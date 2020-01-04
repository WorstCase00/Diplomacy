package com.mst.diplomacy.controller;

import com.google.common.collect.Lists;
import com.mst.diplomacy.ai.ControlledPlayer;
import com.mst.diplomacy.model.definition.Place;
import com.mst.diplomacy.model.definition.Player;
import com.mst.diplomacy.model.game.BuildTurnResult;
import com.mst.diplomacy.model.game.GameState;
import com.mst.diplomacy.model.game.orders.ArmyMoveOrder;
import com.mst.diplomacy.model.game.orders.FleetMoveOrder;
import com.mst.diplomacy.model.game.orders.MoveTurnOrders;
import com.mst.diplomacy.model.game.MoveTurnResult;
import com.mst.diplomacy.model.game.UiGameListener;
import com.mst.diplomacy.rules.Move;
import com.mst.diplomacy.rules.GraphResolver;
import com.mst.diplomacy.rules.Resolver;
import com.mst.diplomacy.rules.Result;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class GameController {

    private final Resolver moveResolver = new GraphResolver();
    
    private List<UiGameListener> listeners = Lists.newArrayList();
    
    public void run(GameState initialGameState, Map<Player, ControlledPlayer> controlledPlayers) throws InterruptedException {

        GameState gameState = initialGameState;
        
        while (!gameState.isGameOver()) {
            Thread.sleep(100);
            System.out.println("next turn");
            
            if (gameState.isBuildStepNext()) {
                for (UiGameListener listener : listeners) {
                    gameState.getCapitalOwners().entrySet().stream().forEach(entry -> listener.playerOccupies(entry.getKey(), entry.getValue()));
                }

                BuildTurnResult.Builder builder = new BuildTurnResult.Builder();
                for (ControlledPlayer player : controlledPlayers.values()) {
                    builder.add(player.getBuilds(gameState));
                }
                BuildTurnResult buildTurnResult = builder.build();
                gameState.buildTurnResult(buildTurnResult);
                this.listeners.stream().forEach(listener -> {
                    buildTurnResult.getTroopAdaptionOrders().stream().forEach(order -> {
                        if (order.isBuild()) {
                            if (order.isFleet()) {
                                listener.addFleet(order.getPlace(), order.getPlayer());
                            } else {
                                listener.addArmy(order.getPlace(), order.getPlayer());
                            }
                        }
                    });
                });
            }

            GameState finalGameState = gameState;
            Map<Player, MoveTurnOrders> playerMoves = controlledPlayers.entrySet().stream().collect(Collectors.toMap(
                    entry -> entry.getKey(),
                    entry -> entry.getValue().getMoves(finalGameState)));

            ValidatedMoves validatedMoves = ValidatedMoves.create(playerMoves);
            Result moveResults = moveResolver.resolve(
                    validatedMoves.getHoldOrders(), 
                    validatedMoves.getMoveOrders(), 
                    validatedMoves.getSupportOrders());
            
            gameState = gameState.moveTurnResolved(transform(moveResults, validatedMoves));
        }
    }

    private MoveTurnResult transform(Result moveResults, ValidatedMoves validatedMoves) {
        Set<String> dislodged = moveResults.getDislodged();
        Set<Move> successfulMoves = moveResults.getSuccessfulMoves();
        Map<Player, Set<ArmyMoveOrder>> armyMoves = validatedMoves.getArmyMoves(successfulMoves);
        
        Map<Player, Set<FleetMoveOrder>> fleetMoves = validatedMoves.getFleetMoves(successfulMoves);
        this.listeners.forEach(listener -> {
            listener.move(validatedMoves.getMoves(successfulMoves));
        });
        Map<Player, Set<Place>> dis = validatedMoves.getDislodgements(dislodged);
        return new MoveTurnResult(
                armyMoves,
                fleetMoves,
                dis
        );
    }

    public void addListener(UiGameListener boardView) {
        this.listeners.add(boardView);
    }
}

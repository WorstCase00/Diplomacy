package com.mst.diplomacy.main;

import com.mst.diplomacy.ai.ControlledPlayer;
import com.mst.diplomacy.ai.HoldingPlayer;
import com.mst.diplomacy.ai.RandomMovePlayer;
import com.mst.diplomacy.controller.GameController;
import com.mst.diplomacy.model.definition.Player;
import com.mst.diplomacy.model.game.GameState;
import com.mst.diplomacy.model.definition.Game;
import com.mst.diplomacy.ui.BoardView;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {
    
    private static final GameFactory GAME_FACTORY = new GameFactory();

    public static void main(String[] args) throws InterruptedException {
        System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
        Game game = GAME_FACTORY.createWestMedGame();
        GameState gameState = GameState.init(game);
        Map<Player, ControlledPlayer> players = game.getPlayers().stream().collect(Collectors.toMap(
                Function.identity(),
                player -> RandomMovePlayer.init(player, game.getWorld())
        ));

        GameController gameController = new GameController();

        BoardView boardView = BoardView.init(game);
        gameController.addListener(boardView);
        
        gameController.run(gameState, players);
    }
}

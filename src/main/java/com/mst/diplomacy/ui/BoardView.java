package com.mst.diplomacy.ui;

import com.google.common.base.Joiner;
import com.google.common.collect.Maps;
import com.mst.diplomacy.model.definition.Game;
import com.mst.diplomacy.model.definition.Place;
import com.mst.diplomacy.model.definition.Player;
import com.mst.diplomacy.model.game.UiGameListener;
import com.mst.diplomacy.model.game.orders.TroopMoveOrder;
import org.graphstream.graph.EdgeRejectedException;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.spriteManager.Sprite;
import org.graphstream.ui.spriteManager.SpriteManager;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;


public class BoardView implements UiGameListener {

    private final SingleGraph graph;
    private final SpriteManager spriteManager;
    
    private Map<Place, Sprite> troopSpriteMap;
    private Map<Place, Sprite> occupationSpriteMap;

    private BoardView() {
        this.graph = new SingleGraph("Board");
        graph.addAttribute("ui.stylesheet", "url(./styles.css)");

        this.graph.display();
        this.spriteManager = new SpriteManager(graph);
        this.troopSpriteMap = Maps.newHashMap();
        this.occupationSpriteMap = Maps.newHashMap();
    }

    @Override
    public void move(Set<TroopMoveOrder> moveOrders) {
        for (TroopMoveOrder moveOrder : moveOrders) {
            removeTroops(moveOrder.getFrom(), moveOrder.getPlayer());
        }

        for (TroopMoveOrder moveOrder : moveOrders) {
            if (moveOrder.isFleet()) {
                addFleet(moveOrder.getTo(), moveOrder.getPlayer());
            } else {
                addArmy(moveOrder.getTo(), moveOrder.getPlayer());
            }
        }
        
        for (TroopMoveOrder moveOrder : moveOrders) {
            if (!moveOrder.getTo().isCapital()) {
                playerOccupies(moveOrder.getTo(), moveOrder.getPlayer());
            }
        }
        
    }

    @Override
    public void addArmy(Place place, Player player) {
        Sprite sprite = spriteManager.addSprite(generateTroopSpriteId(place, player));
        sprite.setAttribute("ui.class", "army"+player.getName());
        sprite.setAttribute("ui.label", "A");
        sprite.attachToNode(place.getName());
        this.troopSpriteMap.put(place, sprite);
    }

    @Override
    public void addFleet(Place place, Player player) {
        Sprite sprite = spriteManager.addSprite(generateTroopSpriteId(place, player));
        sprite.setAttribute("ui.class", "army"+player.getName());
        sprite.setAttribute("ui.label", "F");
        sprite.attachToNode(place.getName());
        this.troopSpriteMap.put(place, sprite);
    }

    @Override
    public void playerOccupies(Place place, Player player) {
        Sprite sprite = occupationSpriteMap.get(place);
        sprite.removeAttribute("ui.hide");
        sprite.setAttribute("ui.class", player.getName());
    }

    @Override
    public void removeTroops(Place place, Player player) {
        Sprite remove = this.troopSpriteMap.remove(place);
        remove.detach();
        remove.clearAttributes();
        remove.setAttribute("ui.hide");
        remove.removeAttribute("ui.class");
        remove.removeAttribute("ui.label");
        this.spriteManager.removeSprite(remove.getId());
    }
    
    public static BoardView init(Game game) {
        BoardView boardView = new BoardView();
        Set<Place> places = game.getWorld().getPlaces();
        places.stream().forEach(place -> {

            if (place.isSea()) {
                boardView.addSea(place);
            } else if (place.isCapital()) {
                boardView.addCapital(place);
            } else {
                boardView.addLand(place);
            }
        });
        places.stream().forEach(place -> {
            game.getWorld().getAdjacentPlaces(place).stream().forEach(place2 -> {
                boardView.addBorder(place.getName(), place2.getName());
            });
        });
        
        game.getPlayers().forEach(player -> boardView.addPlayer(player));
        return boardView;
    }

    private void addPlayer(Player player) {
        player.getHomePlaces().stream().forEach(land -> {
            playerOccupies(land, player);
        });
        player.getArmyPlaces().stream().forEach(place -> addArmy(place, player));
        player.getFleetPlaces().stream().forEach(place -> addFleet(place, player));
    }

    private void addSea(Place place) {
        Node node = graph.addNode(place.getName());
        node.addAttribute("ui.class", "sea");
        node.addAttribute("ui.label", place);
        addSprite(place, node);

    }

    private String generatePlaceSpriteId(String name) {
        return name;
    }

    private void addCapital(Place place) {
        Node node = graph.addNode(place.getName());
        node.addAttribute("ui.class", "capital");
        node.addAttribute("ui.label", place);
        addSprite(place, node);
    }

    private void addSprite(Place place, Node node) {
        Sprite sprite = this.spriteManager.addSprite(generatePlaceSpriteId(place.getName()));
        sprite.addAttribute("ui.hide");
        this.occupationSpriteMap.put(place, sprite);
        sprite.attachToNode(node.getId());
    }

    private void addLand(Place place) {
        Node node = graph.addNode(place.getName());
        node.addAttribute("ui.class", "land");
        node.addAttribute("ui.label", place.getName());
        addSprite(place, node);
    }

    private void addBorder(String name1, String name2) {
        try {
            graph.addEdge(UUID.randomUUID().toString(), name1, name2);
        } catch (EdgeRejectedException e) { }
    }

    private String generateTroopSpriteId(Place land, Player player) {
        return UUID.randomUUID().toString();
    }
    
    private String generateSpriteId(Place land, Player player) {
        return land.getName();
    }

}

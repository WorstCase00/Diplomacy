package com.mst.diplomacy.model.definition;

import com.google.errorprone.annotations.Immutable;

import java.util.Set;
import java.util.stream.Collectors;

@Immutable
public class Player {
    
    private final String name;
    private final String cssColor;
    private final Set<Place> homePlaces;
    private final Set<Place> armyPlaces;
    private final Set<Place> fleetPlaces;

    public Player(String name, String cssColor, Set<Place> homePlaces, Set<Place> armyPlaces, Set<Place> fleetPlaces) {
        this.name = name;
        this.cssColor = cssColor;
        this.homePlaces = homePlaces;
        this.armyPlaces = armyPlaces;
        this.fleetPlaces = fleetPlaces;
    }

    public String getName() {
        return name;
    }

    public String getCssColor() {
        return cssColor;
    }

    public Set<Place> getHomePlaces() {
        return homePlaces;
    }
    
    public Set<Place> getHomeCapitals() {
        return homePlaces.stream().filter(Place::isCapital).collect(Collectors.toSet());
    }

    public Set<Place> getArmyPlaces() {
        return armyPlaces;
    }

    public Set<Place> getFleetPlaces() {
        return fleetPlaces;
    }
}

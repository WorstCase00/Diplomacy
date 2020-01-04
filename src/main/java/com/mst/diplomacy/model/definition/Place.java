package com.mst.diplomacy.model.definition;


import java.util.Objects;

public class Place {
    private final  String name;
    private final PlaceType type;

    private Place(String name, PlaceType type) {
        this.name = name;
        this.type = type;
    }

    public boolean isSea() {
        return type == PlaceType.SEA;
    }

    public boolean isCapital() {
        return type == PlaceType.CAPITAL;
    }

    public static Place createCapital(String name) {
        return new Place(name, PlaceType.CAPITAL);
    }

    public static Place createSea(String name) {
        return new Place(name, PlaceType.SEA);
    }

    public static Place createLand(String name) {
        return new Place(name, PlaceType.LAND);
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Place place = (Place) o;
        return Objects.equals(name, place.name) &&
                type == place.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type);
    }

    @Override
    public String toString() {
        return name;
    }

    
    public boolean isLand() {
        return !isSea();
    }
}

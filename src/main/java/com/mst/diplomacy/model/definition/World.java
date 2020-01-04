package com.mst.diplomacy.model.definition;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import javax.annotation.concurrent.Immutable;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Immutable
public class World {
    
    private final ImmutableBiMap<Place, Set<Place>> places;

    private World(Map<Place, Set<Place>> places) {
        this.places = ImmutableBiMap.copyOf(places);
    }

    public static World create(Map<Place, Set<Place>> adjacentPlaces) {

        Map<Place, Set<Place>> fullAdjacentPlaces = Maps.newHashMap();
        adjacentPlaces.entrySet().stream().forEach(entry -> {
            Set<Place> oldPlaces = Optional.fromNullable(fullAdjacentPlaces.get(entry.getKey())).or(Sets.newHashSet());
            fullAdjacentPlaces.put(entry.getKey(), Sets.newHashSet(Sets.union(oldPlaces, entry.getValue())));

            for (Place place : entry.getValue()) {
                if (!fullAdjacentPlaces.containsKey(place)) {
                    fullAdjacentPlaces.put(place, Sets.newHashSet());
                }
                fullAdjacentPlaces.get(place).add(entry.getKey());
            }
        });
        return new World(fullAdjacentPlaces);
    }

    public Set<Place> getPlaces() {
        return this.places.keySet();
    }

    public Set<Place> getPlaces(Set<String> names) {
        return names.stream().map(id -> places.keySet().stream().filter(place -> place.getName().equals(id)).findFirst().get()).collect(Collectors.toSet());
    }

    public Set<Place> getAdjacentPlaces(Place place) {
        return places.get(place);
    }

    public Set<Place> getAdjacentLandPlaces(Place place) {
        return places.get(place).stream().filter(Place::isLand).collect(Collectors.toSet());
    }

    public Set<Place> getAdjacentSeaPlaces(Place place) {
        return places.get(place).stream().filter(Place::isSea).collect(Collectors.toSet());
    }

    public Set<Place> getAdjacentSeasAndCoasts(Place place) {
        return Sets.union(getAdjacentSeaPlaces(place), getAdjacentCoasts(place));
    }

    private Set<Place> getAdjacentCoasts(Place place) {
        return getAdjacentLandPlaces(place).stream().filter(adjacent -> adjacent.isLand() && !getAdjacentSeaPlaces(adjacent).isEmpty()).collect(Collectors.toSet());
    }

    public boolean isLandLocked(Place place) {
        return place.isLand() && !isCoast(place);
    }

    private boolean isCoast(Place place) {
        return !getAdjacentSeaPlaces(place).isEmpty();
    }
}

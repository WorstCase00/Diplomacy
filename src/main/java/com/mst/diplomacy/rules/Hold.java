package com.mst.diplomacy.rules;

import java.util.Objects;

public class Hold {
    
    private final String location;

    public Hold(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hold hold = (Hold) o;
        return Objects.equals(location, hold.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(location);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("HoldOrder{");
        sb.append("location='").append(location).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

package com.mst.diplomacy.rules;

import java.util.Objects;

public class Support {
    
    private final String from;
    private final String supportFrom;
    private final String supportTo;
    private final Move move;

    public Support(String from, String supportFrom, String supportTo) {
        this.from = from;
        this.supportFrom = supportFrom;
        this.supportTo = supportTo;
        this.move = new Move(supportFrom, supportTo);
    }

    public String getFrom() {
        return from;
    }

    public String getSupportFrom() {
        return supportFrom;
    }

    public String getSupportTo() {
        return supportTo;
    }

    public Move getMove() {
        return move;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Support that = (Support) o;
        return Objects.equals(from, that.from) &&
                Objects.equals(supportFrom, that.supportFrom) &&
                Objects.equals(supportTo, that.supportTo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, supportFrom, supportTo);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("SupportOrder{");
        sb.append("from='").append(from).append('\'');
        sb.append(", supportFrom='").append(supportFrom).append('\'');
        sb.append(", supportTo='").append(supportTo).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

package tech.arauk.ark.arel.nodes;

import tech.arauk.ark.arel.interfaces.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ArelNodeSelectCore extends ArelNode implements ArelFromInterface, ArelGroupsInterface, ArelHavingsInterface, ArelProjectionsInterface, ArelSetQuantifierInterface, ArelSourceInterface, ArelTopInterface, ArelWheresInterface, ArelWindowsInterface {
    public ArelNodeJoinSource source;
    public ArelNodeTop top;
    public ArelNode setQuantifier;
    public List<Object> groups;
    public List<Object> havings;
    public List<Object> projections;
    public List<Object> windows;
    public List<Object> wheres;

    public ArelNodeSelectCore() {
        super();

        this.groups = new ArrayList<>();
        this.havings = new ArrayList<>();
        this.projections = new ArrayList<>();
        this.source = new ArelNodeJoinSource(null);
        this.windows = new ArrayList<>();
        this.wheres = new ArrayList<>();
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof ArelNodeSelectCore) {
            ArelNodeSelectCore selectCore = (ArelNodeSelectCore) other;

            return Objects.equals(this.source(), selectCore.source())
                    && Objects.equals(this.setQuantifier(), selectCore.setQuantifier())
                    && Objects.equals(this.projections(), selectCore.projections())
                    && Objects.equals(this.wheres(), selectCore.wheres())
                    && Objects.equals(this.groups(), selectCore.groups())
                    && Objects.equals(this.havings(), selectCore.havings())
                    && Objects.equals(this.windows(), selectCore.windows());
        }

        return super.equals(other);
    }

    @Override
    public Object from() {
        return source.left();
    }

    @Override
    public ArelNodeSelectCore from(Object value) {
        this.source.left(value);

        return this;
    }

    @Override
    public List<Object> groups() {
        return this.groups;
    }

    @Override
    public ArelNodeSelectCore groups(List<Object> groups) {
        this.groups = groups;
        return this;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(new Object[]{source(), top(), setQuantifier(), projections(), wheres(), groups(), havings(), windows()});
    }

    @Override
    public List<Object> havings() {
        return this.havings;
    }

    @Override
    public ArelNodeSelectCore havings(List<Object> havings) {
        this.havings = havings;
        return this;
    }

    @Override
    public List<Object> projections() {
        return this.projections;
    }

    @Override
    public ArelNodeSelectCore projections(List<Object> projections) {
        this.projections = projections;
        return this;
    }

    @Override
    public ArelNode setQuantifier() {
        return this.setQuantifier;
    }

    @Override
    public ArelNodeSelectCore setQuantifier(ArelNode setQuantifier) {
        this.setQuantifier = setQuantifier;
        return this;
    }

    @Override
    public ArelNodeJoinSource source() {
        return this.source;
    }

    @Override
    public ArelNodeSelectCore source(ArelNodeJoinSource source) {
        this.source = source;
        return this;
    }

    @Override
    public ArelNodeTop top() {
        return this.top;
    }

    @Override
    public ArelNodeSelectCore top(ArelNodeTop top) {
        this.top = top;
        return this;
    }

    @Override
    public List<Object> wheres() {
        return this.wheres;
    }

    @Override
    public ArelNodeSelectCore wheres(Object wheres) {
        this.wheres = objectToList(wheres);
        return this;
    }

    @Override
    public List<Object> windows() {
        return this.windows;
    }

    @Override
    public ArelNodeSelectCore windows(List<Object> windows) {
        this.windows = windows;
        return this;
    }

    public Object froms() {
        return from();
    }

    public ArelNodeSelectCore froms(Object value) {
        return from(value);
    }
}

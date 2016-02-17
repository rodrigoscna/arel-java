package tech.arauk.ark.arel.attributes;

import tech.arauk.ark.arel.*;
import tech.arauk.ark.arel.nodes.*;
import tech.arauk.ark.arel.nodes.binary.ArelNodeAs;
import tech.arauk.ark.arel.nodes.binary.ArelNodeGreaterThan;
import tech.arauk.ark.arel.nodes.binary.ArelNodeIn;
import tech.arauk.ark.arel.nodes.binary.ArelNodeLessThan;

import java.util.Objects;

public class ArelAttribute {
    public ArelRelation relation;
    public String name;

    private ArelOrderPredications mOrderPredications;
    private ArelPredications mPredications;

    public ArelAttribute(ArelRelation relation, String name) {
        this.relation = relation;
        this.name = name;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof ArelAttribute) {
            return Objects.equals(this.relation, ((ArelAttribute) other).relation) && Objects.equals(this.name, ((ArelAttribute) other).name);
        } else {
            return super.equals(other);
        }
    }

    public ArelNode between(Object begin, Object end) {
        return predications().between(begin, end);
    }

    public ArelNodeEquality eq(Object other) {
        return predications().eq(other);
    }

    public ArelNodeGreaterThan gt(Object right) {
        return predications().gt(right);
    }

    public ArelNodeLessThan lt(Object right) {
        return predications().lt(right);
    }

    public ArelNodeIn in(Object other) {
        return predications().in(other);
    }

    public ArelNodeAs as(Object other) {
        return ArelAliasPredications.as(this, other);
    }

    public ArelNodeCount count() {
        return count(false);
    }

    public ArelNodeCount count(Boolean distinct) {
        return ArelExpressions.count(this, distinct);
    }

    public ArelOrderPredications orderPredications() {
        if (this.mOrderPredications == null) {
            this.mOrderPredications = new ArelOrderPredications(this);
        }
        return this.mOrderPredications;
    }

    private ArelPredications predications() {
        if (this.mPredications == null) {
            this.mPredications = new ArelPredications(this);
        }
        return this.mPredications;
    }

    public boolean isAbleToTypeCast() {
        return this.relation.isAbleToTypeCast();
    }

    public Object typeCastForDatabase(Object value) {
        return this.relation.typeCastForDatabase(this.name, value);
    }

    public ArelNodeNamedFunction lower() {
        return this.relation.lower(this);
    }

    public ArelNodeAscending asc() {
        return orderPredications().asc();
    }

    public ArelNodeDescending desc() {
        return orderPredications().desc();
    }

    public interface Type {
        String type();
    }
}

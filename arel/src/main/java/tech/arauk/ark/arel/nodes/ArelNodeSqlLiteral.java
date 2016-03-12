package tech.arauk.ark.arel.nodes;

import tech.arauk.ark.arel.*;
import tech.arauk.ark.arel.nodes.binary.*;

import java.util.Objects;

public class ArelNodeSqlLiteral implements ArelAliasPredicationsInterface, ArelExpressionsInterface, ArelOrderPredicationsInterface, ArelPredicationsInterface {
    private String mValue;

    public ArelNodeSqlLiteral(Object value) {
        this.mValue = String.valueOf(value);
    }

    @Override
    public ArelNodeAs as(Object other) {
        return ArelAliasPredications.as(this, other);
    }

    @Override
    public ArelNodeAscending asc() {
        return ArelOrderPredications.asc(this);
    }

    @Override
    public ArelNode between(Object begin, Object end) {
        return ArelPredications.between(this, begin, end);
    }

    @Override
    public ArelNode between(Object begin, Object end, boolean inclusive) {
        return ArelPredications.between(this, begin, end, inclusive);
    }

    @Override
    public ArelNodeCount count() {
        return ArelExpressions.count(this);
    }

    @Override
    public ArelNodeCount count(Boolean distinct) {
        return ArelExpressions.count(this, distinct);
    }

    @Override
    public ArelNodeDescending desc() {
        return ArelOrderPredications.desc(this);
    }

    @Override
    public ArelNodeEquality eq(Object other) {
        return ArelPredications.eq(this, other);
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof ArelNodeSqlLiteral) {
            return Objects.equals(this.mValue, ((ArelNodeSqlLiteral) other).getValue());
        } else if (other instanceof String) {
            return Objects.equals(this.mValue, String.valueOf(other));
        } else {
            return super.equals(other);
        }
    }

    @Override
    public ArelNodeGreaterThan gt(Object right) {
        return ArelPredications.gt(this, right);
    }

    @Override
    public ArelNodeGreaterThanOrEqual gteq(Object right) {
        return ArelPredications.gteq(this, right);
    }

    @Override
    public ArelNodeIn in(Object other) {
        return ArelPredications.in(this, other);
    }

    @Override
    public ArelNodeLessThan lt(Object right) {
        return ArelPredications.lt(this, right);
    }

    @Override
    public ArelNodeLessThanOrEqual lteq(Object right) {
        return ArelPredications.lteq(this, right);
    }

    @Override
    public ArelNodeNotIn notIn(Object other) {
        return ArelPredications.notIn(this, other);
    }

    @Override
    public String toString() {
        return this.mValue;
    }

    public String getValue() {
        return toString();
    }
}

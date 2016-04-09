package tech.arauk.ark.arel.attributes;

import tech.arauk.ark.arel.*;
import tech.arauk.ark.arel.nodes.*;
import tech.arauk.ark.arel.nodes.ArelNodeAvg;
import tech.arauk.ark.arel.nodes.ArelNodeMax;
import tech.arauk.ark.arel.nodes.ArelNodeMin;
import tech.arauk.ark.arel.nodes.ArelNodeSum;

import java.util.Objects;

public class ArelAttribute implements ArelAliasPredicationsInterface, ArelExpressionsInterface, ArelOrderPredicationsInterface, ArelPredicationsInterface {
    public ArelRelation relation;
    public String name;

    public ArelAttribute(Object relation, String name) {
        if (relation instanceof String) {
            this.relation = new ArelTable((String) relation);
        } else {
            this.relation = (ArelRelation) relation;
        }
        this.name = name;
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
    public ArelNodeAvg average() {
        return ArelExpressions.average(this);
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
    public ArelNodeDoesNotMatch doesNotMatch(Object right) {
        return ArelPredications.doesNotMatch(this, right);
    }

    @Override
    public ArelNodeGrouping doesNotMatchAll(Object... others) {
        return ArelPredications.doesNotMatchAll(this, others);
    }

    @Override
    public ArelNodeGrouping doesNotMatchAny(Object... others) {
        return ArelPredications.doesNotMatchAny(this, others);
    }

    @Override
    public ArelNodeEquality eq(Object other) {
        return ArelPredications.eq(this, other);
    }

    @Override
    public ArelNodeGrouping eqAll(Object... others) {
        return ArelPredications.eqAll(this, others);
    }

    @Override
    public ArelNodeGrouping eqAny(Object... others) {
        return ArelPredications.eqAny(this, others);
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof ArelAttribute) {
            ArelAttribute attribute = (ArelAttribute) other;
            return Objects.deepEquals(this.relation, attribute.relation) && Objects.deepEquals(this.name, attribute.name);
        }

        return super.equals(other);
    }

    @Override
    public ArelNodeGreaterThan gt(Object right) {
        return ArelPredications.gt(this, right);
    }

    @Override
    public ArelNodeGrouping gtAll(Object... others) {
        return ArelPredications.gtAll(this, others);
    }

    @Override
    public ArelNodeGrouping gtAny(Object... others) {
        return ArelPredications.gtAny(this, others);
    }

    @Override
    public ArelNodeGreaterThanOrEqual gteq(Object right) {
        return ArelPredications.gteq(this, right);
    }

    @Override
    public ArelNodeGrouping gteqAll(Object... others) {
        return ArelPredications.gteqAll(this, others);
    }

    @Override
    public ArelNodeGrouping gteqAny(Object... others) {
        return ArelPredications.gteqAny(this, others);
    }

    @Override
    public ArelNodeIn in(Object other) {
        return ArelPredications.in(this, other);
    }

    @Override
    public ArelNodeGrouping inAll(Object... other) {
        return ArelPredications.inAll(this, other);
    }

    @Override
    public ArelNodeGrouping inAny(Object... other) {
        return ArelPredications.inAny(this, other);
    }

    @Override
    public ArelNodeLessThan lt(Object right) {
        return ArelPredications.lt(this, right);
    }

    @Override
    public ArelNodeGrouping ltAll(Object... others) {
        return ArelPredications.ltAll(this, others);
    }

    @Override
    public ArelNodeGrouping ltAny(Object... others) {
        return ArelPredications.ltAny(this, others);
    }

    @Override
    public ArelNodeLessThanOrEqual lteq(Object right) {
        return ArelPredications.lteq(this, right);
    }

    @Override
    public ArelNodeGrouping lteqAll(Object... others) {
        return ArelPredications.lteqAll(this, others);
    }

    @Override
    public ArelNodeGrouping lteqAny(Object... others) {
        return ArelPredications.lteqAny(this, others);
    }

    @Override
    public ArelNodeMatches matches(Object right) {
        return ArelPredications.matches(this, right);
    }

    @Override
    public ArelNodeGrouping matchesAll(Object... others) {
        return ArelPredications.matchesAll(this, others);
    }

    @Override
    public ArelNodeGrouping matchesAny(Object... others) {
        return ArelPredications.matchesAny(this, others);
    }

    @Override
    public ArelNodeMax maximum() {
        return ArelExpressions.maximum(this);
    }

    @Override
    public ArelNodeMin minimum() {
        return ArelExpressions.minimum(this);
    }

    @Override
    public ArelNode notBetween(Object begin, Object end) {
        return ArelPredications.notBetween(this, begin, end);
    }

    @Override
    public ArelNode notBetween(Object begin, Object end, boolean inclusive) {
        return ArelPredications.notBetween(this, begin, end, inclusive);
    }

    @Override
    public ArelNodeNotEqual notEq(Object other) {
        return ArelPredications.notEq(this, other);
    }

    @Override
    public ArelNodeGrouping notEqAll(Object... other) {
        return ArelPredications.notEqAll(this, other);
    }

    @Override
    public ArelNodeGrouping notEqAny(Object... other) {
        return ArelPredications.notEqAny(this, other);
    }

    @Override
    public ArelNodeNotIn notIn(Object other) {
        return ArelPredications.notIn(this, other);
    }

    @Override
    public ArelNodeGrouping notInAll(Object... other) {
        return ArelPredications.notInAll(this, other);
    }

    @Override
    public ArelNodeGrouping notInAny(Object... other) {
        return ArelPredications.notInAny(this, other);
    }

    @Override
    public ArelNodeSum sum() {
        return ArelExpressions.sum(this);
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

    public interface Type {
        String type();
    }
}

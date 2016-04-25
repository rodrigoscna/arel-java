package tech.arauk.ark.arel.nodes;

import tech.arauk.ark.arel.*;

public class ArelNodeInfixOperation extends ArelNodeBinary implements ArelAliasPredicationsInterface, ArelExpressionsInterface, ArelMathInterface, ArelOrderPredicationsInterface, ArelPredicationsInterface {
    private Object operator;

    public ArelNodeInfixOperation(Object operator, Object left, Object right) {
        super(left, right);
        operator(operator);
    }

    @Override
    public ArelNodeGrouping add(Object other) {
        return ArelMath.add(this, other);
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
    public ArelNodeDivision divide(Object other) {
        return ArelMath.divide(this, other);
    }

    @Override
    public ArelNodeDoesNotMatch doesNotMatch(Object right) {
        return ArelPredications.doesNotMatch(this, right);
    }

    @Override
    public ArelNodeDoesNotMatch doesNotMatch(Object right, Object escape) {
        return ArelPredications.doesNotMatch(this, right);
    }

    @Override
    public ArelNodeGrouping doesNotMatchAll(Object others) {
        return ArelPredications.doesNotMatchAll(this, others);
    }

    @Override
    public ArelNodeGrouping doesNotMatchAll(Object others, Object escape) {
        return ArelPredications.doesNotMatchAll(this, others);
    }

    @Override
    public ArelNodeGrouping doesNotMatchAny(Object others) {
        return ArelPredications.doesNotMatchAny(this, others);
    }

    @Override
    public ArelNodeGrouping doesNotMatchAny(Object others, Object escape) {
        return ArelPredications.doesNotMatchAny(this, others);
    }

    @Override
    public ArelNodeEquality eq(Object other) {
        return ArelPredications.eq(this, other);
    }

    @Override
    public ArelNodeGrouping eqAll(Object others) {
        return ArelPredications.eqAll(this, others);
    }

    @Override
    public ArelNodeGrouping eqAny(Object others) {
        return ArelPredications.eqAny(this, others);
    }

    @Override
    public ArelNodeExtract extract(Object field) {
        return ArelExpressions.extract(this, field);
    }

    @Override
    public ArelNodeGreaterThan gt(Object right) {
        return ArelPredications.gt(this, right);
    }

    @Override
    public ArelNodeGrouping gtAll(Object others) {
        return ArelPredications.gtAll(this, others);
    }

    @Override
    public ArelNodeGrouping gtAny(Object others) {
        return ArelPredications.gtAny(this, others);
    }

    @Override
    public ArelNodeGreaterThanOrEqual gteq(Object right) {
        return ArelPredications.gteq(this, right);
    }

    @Override
    public ArelNodeGrouping gteqAll(Object others) {
        return ArelPredications.gteqAll(this, others);
    }

    @Override
    public ArelNodeGrouping gteqAny(Object others) {
        return ArelPredications.gteqAny(this, others);
    }

    @Override
    public ArelNodeIn in(Object other) {
        return ArelPredications.in(this, other);
    }

    @Override
    public ArelNodeGrouping inAll(Object others) {
        return ArelPredications.inAll(this, others);
    }

    @Override
    public ArelNodeGrouping inAny(Object others) {
        return ArelPredications.inAny(this, others);
    }

    @Override
    public ArelNodeLessThan lt(Object right) {
        return ArelPredications.lt(this, right);
    }

    @Override
    public ArelNodeGrouping ltAll(Object others) {
        return ArelPredications.ltAll(this, others);
    }

    @Override
    public ArelNodeGrouping ltAny(Object others) {
        return ArelPredications.ltAny(this, others);
    }

    @Override
    public ArelNodeLessThanOrEqual lteq(Object right) {
        return ArelPredications.lteq(this, right);
    }

    @Override
    public ArelNodeGrouping lteqAll(Object others) {
        return ArelPredications.lteqAll(this, others);
    }

    @Override
    public ArelNodeGrouping lteqAny(Object others) {
        return ArelPredications.lteqAny(this, others);
    }

    @Override
    public ArelNodeMatches matches(Object right) {
        return ArelPredications.matches(this, right);
    }

    @Override
    public ArelNodeMatches matches(Object right, Object escape) {
        return ArelPredications.matches(this, right);
    }

    @Override
    public ArelNodeGrouping matchesAll(Object others) {
        return ArelPredications.matchesAll(this, others);
    }

    @Override
    public ArelNodeGrouping matchesAll(Object others, Object escape) {
        return ArelPredications.matchesAll(this, others);
    }

    @Override
    public ArelNodeGrouping matchesAny(Object others) {
        return ArelPredications.matchesAny(this, others);
    }

    @Override
    public ArelNodeGrouping matchesAny(Object others, Object escape) {
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
    public ArelNodeMultiplication multiply(Object other) {
        return ArelMath.multiply(this, other);
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
    public ArelNodeGrouping notEqAll(Object others) {
        return ArelPredications.notEqAll(this, others);
    }

    @Override
    public ArelNodeGrouping notEqAny(Object others) {
        return ArelPredications.notEqAny(this, others);
    }

    @Override
    public ArelNodeNotIn notIn(Object other) {
        return ArelPredications.notIn(this, other);
    }

    @Override
    public ArelNodeGrouping notInAll(Object others) {
        return ArelPredications.notInAll(this, others);
    }

    @Override
    public ArelNodeGrouping notInAny(Object others) {
        return ArelPredications.notInAny(this, others);
    }

    @Override
    public ArelNodeGrouping subtract(Object other) {
        return ArelMath.subtract(this, other);
    }

    @Override
    public ArelNodeSum sum() {
        return ArelExpressions.sum(this);
    }

    public Object operator() {
        return this.operator;
    }

    public ArelNodeInfixOperation operator(Object operator) {
        this.operator = operator;
        return this;
    }
}

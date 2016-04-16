package tech.arauk.ark.arel.nodes;

import tech.arauk.ark.arel.ArelPredications;
import tech.arauk.ark.arel.ArelPredicationsInterface;
import tech.arauk.ark.arel.ArelWindowPredications;
import tech.arauk.ark.arel.ArelWindowPredicationsInterface;

import java.util.Arrays;
import java.util.Objects;

public class ArelNodeFunction extends ArelNode implements ArelPredicationsInterface, ArelWindowPredicationsInterface {
    public Object alias;
    public Object expressions;
    public Boolean distinct;

    public ArelNodeFunction(Object expr) {
        this(expr, null);
    }

    public ArelNodeFunction(Object expr, String alias) {
        this.expressions = expr;
        if (alias != null) {
            this.alias = new ArelNodeSqlLiteral(alias);
        }
        this.distinct = false;
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
        if (other instanceof ArelNodeFunction) {
            ArelNodeFunction function = (ArelNodeFunction) other;

            return Objects.deepEquals(this.getClass(), function.getClass()) && Objects.deepEquals(this.expressions(), function.expressions()) && Objects.deepEquals(this.alias(), function.alias()) && Objects.deepEquals(this.distinct(), function.distinct());
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
    public int hashCode() {
        return Arrays.hashCode(new Object[]{expressions(), alias(), distinct()});
    }

    @Override
    public ArelNodeIn in(Object other) {
        return ArelPredications.in(this, other);
    }

    @Override
    public ArelNodeGrouping inAll(Object... others) {
        return ArelPredications.inAll(this, others);
    }

    @Override
    public ArelNodeGrouping inAny(Object... others) {
        return ArelPredications.inAny(this, others);
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
    public ArelNodeGrouping notEqAll(Object... others) {
        return ArelPredications.notEqAll(this, others);
    }

    @Override
    public ArelNodeGrouping notEqAny(Object... others) {
        return ArelPredications.notEqAny(this, others);
    }

    @Override
    public ArelNodeNotIn notIn(Object other) {
        return ArelPredications.notIn(this, other);
    }

    @Override
    public ArelNodeGrouping notInAll(Object... others) {
        return ArelPredications.notInAll(this, others);
    }

    @Override
    public ArelNodeGrouping notInAny(Object... others) {
        return ArelPredications.notInAny(this, others);
    }

    @Override
    public ArelNodeOver over() {
        return ArelWindowPredications.over(this);
    }

    @Override
    public ArelNodeOver over(Object expr) {
        return ArelWindowPredications.over(this, expr);
    }

    public ArelNodeFunction as(Object alias) {
        this.alias = new ArelNodeSqlLiteral(alias);
        return this;
    }

    public Object alias() {
        return this.alias;
    }

    public ArelNodeFunction alias(Object alias) {
        this.alias = alias;
        return this;
    }

    public Boolean distinct() {
        return this.distinct;
    }

    public ArelNodeFunction distinct(Boolean distinct) {
        this.distinct = distinct;
        return this;
    }

    public Object expressions() {
        return this.expressions;
    }

    public ArelNodeFunction expressions(Object expressions) {
        this.expressions = expressions;
        return this;
    }
}

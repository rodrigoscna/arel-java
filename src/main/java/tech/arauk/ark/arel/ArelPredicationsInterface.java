package tech.arauk.ark.arel;

import tech.arauk.ark.arel.nodes.*;

public interface ArelPredicationsInterface {
    ArelNode between(Object begin, Object end);

    ArelNode between(Object begin, Object end, boolean inclusive);

    ArelNodeDoesNotMatch doesNotMatch(Object right);

    ArelNodeDoesNotMatch doesNotMatch(Object right, Object escape);

    ArelNodeGrouping doesNotMatchAll(Object others);

    ArelNodeGrouping doesNotMatchAll(Object others, Object escape);

    ArelNodeGrouping doesNotMatchAny(Object others);

    ArelNodeGrouping doesNotMatchAny(Object others, Object escape);

    ArelNodeEquality eq(Object other);

    ArelNodeGrouping eqAll(Object others);

    ArelNodeGrouping eqAny(Object others);

    ArelNodeGreaterThan gt(Object right);

    ArelNodeGrouping gtAll(Object others);

    ArelNodeGrouping gtAny(Object others);

    ArelNodeGreaterThanOrEqual gteq(Object right);

    ArelNodeGrouping gteqAll(Object others);

    ArelNodeGrouping gteqAny(Object others);

    ArelNodeIn in(Object other);

    ArelNodeGrouping inAll(Object others);

    ArelNodeGrouping inAny(Object others);

    ArelNodeLessThan lt(Object right);

    ArelNodeGrouping ltAll(Object others);

    ArelNodeGrouping ltAny(Object others);

    ArelNodeLessThanOrEqual lteq(Object right);

    ArelNodeGrouping lteqAll(Object others);

    ArelNodeGrouping lteqAny(Object others);

    ArelNodeMatches matches(Object right);

    ArelNodeMatches matches(Object right, Object escape);

    ArelNodeGrouping matchesAll(Object others);

    ArelNodeGrouping matchesAll(Object others, Object escape);

    ArelNodeGrouping matchesAny(Object others);

    ArelNodeGrouping matchesAny(Object others, Object escape);

    ArelNode notBetween(Object begin, Object end);

    ArelNode notBetween(Object begin, Object end, boolean inclusive);

    ArelNodeNotEqual notEq(Object other);

    ArelNodeGrouping notEqAll(Object others);

    ArelNodeGrouping notEqAny(Object others);

    ArelNodeNotIn notIn(Object other);

    ArelNodeGrouping notInAll(Object others);

    ArelNodeGrouping notInAny(Object others);
}

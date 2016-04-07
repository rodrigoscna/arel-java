package tech.arauk.ark.arel;

import tech.arauk.ark.arel.nodes.ArelNode;
import tech.arauk.ark.arel.nodes.ArelNodeEquality;
import tech.arauk.ark.arel.nodes.ArelNodeGrouping;
import tech.arauk.ark.arel.nodes.binary.*;

public interface ArelPredicationsInterface {
    ArelNode between(Object begin, Object end);

    ArelNode between(Object begin, Object end, boolean inclusive);

    ArelNodeDoesNotMatch doesNotMatch(Object right);

    ArelNodeGrouping doesNotMatchAll(Object... others);

    ArelNodeGrouping doesNotMatchAny(Object... others);

    ArelNodeEquality eq(Object other);

    ArelNodeGrouping eqAll(Object... others);

    ArelNodeGrouping eqAny(Object... others);

    ArelNodeGreaterThan gt(Object right);

    ArelNodeGrouping gtAll(Object... others);

    ArelNodeGrouping gtAny(Object... others);

    ArelNodeGreaterThanOrEqual gteq(Object right);

    ArelNodeGrouping gteqAll(Object... others);

    ArelNodeGrouping gteqAny(Object... others);

    ArelNodeIn in(Object other);

    ArelNodeGrouping inAll(Object... other);

    ArelNodeGrouping inAny(Object... other);

    ArelNodeLessThan lt(Object right);

    ArelNodeGrouping ltAll(Object... others);

    ArelNodeGrouping ltAny(Object... others);

    ArelNodeLessThanOrEqual lteq(Object right);

    ArelNodeGrouping lteqAll(Object... others);

    ArelNodeGrouping lteqAny(Object... others);

    ArelNodeMatches matches(Object right);

    ArelNodeGrouping matchesAll(Object... others);

    ArelNodeGrouping matchesAny(Object... others);

    ArelNode notBetween(Object begin, Object end);

    ArelNode notBetween(Object begin, Object end, boolean inclusive);

    ArelNodeNotEqual notEq(Object other);

    ArelNodeGrouping notEqAll(Object... others);

    ArelNodeGrouping notEqAny(Object... others);

    ArelNodeNotIn notIn(Object other);

    ArelNodeGrouping notInAll(Object... other);

    ArelNodeGrouping notInAny(Object... other);
}

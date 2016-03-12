package tech.arauk.ark.arel;

import tech.arauk.ark.arel.nodes.ArelNode;
import tech.arauk.ark.arel.nodes.ArelNodeEquality;
import tech.arauk.ark.arel.nodes.binary.*;

public interface ArelPredicationsInterface {
    ArelNode between(Object begin, Object end);

    ArelNode between(Object begin, Object end, boolean inclusive);

    ArelNodeEquality eq(Object other);

    ArelNodeGreaterThan gt(Object right);

    ArelNodeGreaterThanOrEqual gteq(Object right);

    ArelNodeIn in(Object other);

    ArelNodeLessThan lt(Object right);

    ArelNodeLessThanOrEqual lteq(Object right);

    ArelNodeNotIn notIn(Object other);
}

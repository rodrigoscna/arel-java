package tech.arauk.ark.arel;

import tech.arauk.ark.arel.nodes.ArelNode;
import tech.arauk.ark.arel.nodes.ArelNodeEquality;
import tech.arauk.ark.arel.nodes.ArelNodeQuoted;
import tech.arauk.ark.arel.nodes.binary.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class ArelPredications {
    public static ArelNode between(Object holder, Object begin, Object end) {
        return between(holder, begin, end, true);
    }

    public static ArelNode between(Object holder, Object begin, Object end, boolean inclusive) {
        if (equalsQuoted(begin, Float.NEGATIVE_INFINITY)) {
            if (equalsQuoted(end, Float.POSITIVE_INFINITY)) {
                return notIn(holder, new ArrayList<>());
            } else if (inclusive) {
                return lteq(holder, end);
            } else {
                return lt(holder, end);
            }
        } else if (equalsQuoted(end, Float.POSITIVE_INFINITY)) {
            return gteq(holder, end);
        } else if (inclusive) {
            Object left = quotedNode(holder, begin);
            Object right = quotedNode(holder, end);
            return new ArelNodeBetween(holder, ((ArelNode) left).and(right));
        } else {
            return gt(holder, begin).and(lt(holder, end));
        }
    }

    public static ArelNodeEquality eq(Object holder, Object other) {
        return new ArelNodeEquality(holder, quotedNode(holder, other));
    }

    public static ArelNodeGreaterThan gt(Object holder, Object right) {
        return new ArelNodeGreaterThan(holder, quotedNode(holder, right));
    }

    public static ArelNodeGreaterThanOrEqual gteq(Object holder, Object right) {
        return new ArelNodeGreaterThanOrEqual(holder, quotedNode(holder, right));
    }

    public static ArelNodeLessThan lt(Object holder, Object right) {
        return new ArelNodeLessThan(holder, quotedNode(holder, right));
    }

    public static ArelNodeLessThanOrEqual lteq(Object holder, Object right) {
        return new ArelNodeLessThanOrEqual(holder, quotedNode(holder, right));
    }

    public static ArelNodeIn in(Object holder, Object other) {
        if (other instanceof ArelSelectManager) {
            return new ArelNodeIn(holder, ((ArelSelectManager) other).ast);
        } else if (other instanceof List) {
            return new ArelNodeIn(holder, quotedArray(holder, (List<Object>) other));
        } else {
            return new ArelNodeIn(holder, quotedNode(holder, other));
        }
    }

    public static ArelNodeNotIn notIn(Object holder, Object other) {
        if (other instanceof ArelSelectManager) {
            return new ArelNodeNotIn(holder, ((ArelSelectManager) other).ast);
        } else if (other instanceof List) {
            return new ArelNodeNotIn(holder, quotedArray(holder, (List<Object>) other));
        } else {
            return new ArelNodeNotIn(holder, quotedNode(holder, other));
        }
    }

    private static boolean equalsQuoted(Object maybeQuoted, Object value) {
        if (maybeQuoted instanceof ArelNodeQuoted) {
            return Objects.equals(((ArelNodeQuoted) maybeQuoted).expr, value);
        } else {
            return Objects.equals(maybeQuoted, value);
        }
    }

    private static List<Object> quotedArray(Object holder, List<Object> others) {
        for (int i = 0; i < others.size(); i++) {
            others.set(i, quotedNode(holder, others.get(i)));
        }

        return others;
    }

    private static Object quotedNode(Object holder, Object other) {
        return ArelNodes.buildQuoted(other, holder);
    }
}

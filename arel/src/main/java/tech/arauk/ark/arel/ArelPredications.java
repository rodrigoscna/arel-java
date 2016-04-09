package tech.arauk.ark.arel;

import tech.arauk.ark.arel.nodes.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
            return gteq(holder, begin);
        } else if (inclusive) {
            Object left = quotedNode(holder, begin);
            Object right = quotedNode(holder, end);
            return new ArelNodeBetween(holder, ((ArelNode) left).and(right));
        } else {
            return gt(holder, begin).and(lt(holder, end));
        }
    }

    public static ArelNodeDoesNotMatch doesNotMatch(Object holder, Object right) {
        return new ArelNodeDoesNotMatch(holder, quotedNode(holder, right));
    }

    public static ArelNodeGrouping doesNotMatchAll(Object holder, Object[] others) {
        return groupingAll(holder, "doesNotMatch", others);
    }

    public static ArelNodeGrouping doesNotMatchAny(Object holder, Object[] others) {
        return groupingAny(holder, "doesNotMatch", others);
    }

    public static ArelNodeEquality eq(Object holder, Object other) {
        return new ArelNodeEquality(holder, quotedNode(holder, other));
    }

    public static ArelNodeGrouping eqAll(Object holder, Object[] others) {
        return groupingAll(holder, "eq", others);
    }

    public static ArelNodeGrouping eqAny(Object holder, Object[] others) {
        return groupingAny(holder, "eq", others);
    }

    public static ArelNodeGreaterThan gt(Object holder, Object right) {
        return new ArelNodeGreaterThan(holder, quotedNode(holder, right));
    }

    public static ArelNodeGrouping gtAll(Object holder, Object[] others) {
        return groupingAll(holder, "gt", others);
    }

    public static ArelNodeGrouping gtAny(Object holder, Object[] others) {
        return groupingAny(holder, "gt", others);
    }

    public static ArelNodeGreaterThanOrEqual gteq(Object holder, Object right) {
        return new ArelNodeGreaterThanOrEqual(holder, quotedNode(holder, right));
    }

    public static ArelNodeGrouping gteqAll(Object holder, Object[] others) {
        return groupingAll(holder, "gteq", others);
    }

    public static ArelNodeGrouping gteqAny(Object holder, Object[] others) {
        return groupingAny(holder, "gteq", others);
    }

    public static ArelNodeIn in(Object holder, Object other) {
        if (other instanceof ArelSelectManager) {
            return new ArelNodeIn(holder, ((ArelSelectManager) other).ast());
        } else if (other instanceof List) {
            return new ArelNodeIn(holder, quotedArray(holder, (List<Object>) other));
        } else if (other instanceof Object[]) {
            return new ArelNodeIn(holder, quotedArray(holder, (Object[]) other));
        } else {
            return new ArelNodeIn(holder, quotedNode(holder, other));
        }
    }

    public static ArelNodeGrouping inAll(Object holder, Object[] others) {
        return groupingAll(holder, "in", others);
    }

    public static ArelNodeGrouping inAny(Object holder, Object[] others) {
        return groupingAny(holder, "in", others);
    }

    public static ArelNodeLessThan lt(Object holder, Object right) {
        return new ArelNodeLessThan(holder, quotedNode(holder, right));
    }

    public static ArelNodeGrouping ltAll(Object holder, Object[] others) {
        return groupingAll(holder, "lt", others);
    }

    public static ArelNodeGrouping ltAny(Object holder, Object[] others) {
        return groupingAny(holder, "lt", others);
    }

    public static ArelNodeLessThanOrEqual lteq(Object holder, Object right) {
        return new ArelNodeLessThanOrEqual(holder, quotedNode(holder, right));
    }

    public static ArelNodeGrouping lteqAll(Object holder, Object[] others) {
        return groupingAll(holder, "lteq", others);
    }

    public static ArelNodeGrouping lteqAny(Object holder, Object[] others) {
        return groupingAny(holder, "lteq", others);
    }

    public static ArelNodeMatches matches(Object holder, Object right) {
        return new ArelNodeMatches(holder, quotedNode(holder, right));
    }

    public static ArelNodeGrouping matchesAll(Object holder, Object[] others) {
        return groupingAll(holder, "matches", others);
    }

    public static ArelNodeGrouping matchesAny(Object holder, Object[] others) {
        return groupingAny(holder, "matches", others);
    }

    public static ArelNode notBetween(Object holder, Object begin, Object end) {
        return notBetween(holder, begin, end, true);
    }

    public static ArelNode notBetween(Object holder, Object begin, Object end, boolean inclusive) {
        if (equalsQuoted(begin, Float.NEGATIVE_INFINITY)) {
            if (equalsQuoted(end, Float.POSITIVE_INFINITY)) {
                return in(holder, new ArrayList<>());
            } else if (inclusive) {
                return gt(holder, end);
            } else {
                return gteq(holder, end);
            }
        } else if (equalsQuoted(end, Float.POSITIVE_INFINITY)) {
            return lt(holder, begin);
        } else {
            Object left = lt(holder, begin);
            if (inclusive) {
                Object right = gt(holder, end);
                return ((ArelNode) left).or(right);
            } else {
                Object right = gteq(holder, end);
                return ((ArelNode) left).or(right);
            }
        }
    }

    public static ArelNodeNotEqual notEq(Object holder, Object other) {
        return new ArelNodeNotEqual(holder, quotedNode(holder, other));
    }

    public static ArelNodeGrouping notEqAll(Object holder, Object[] others) {
        return groupingAll(holder, "notEq", others);
    }

    public static ArelNodeGrouping notEqAny(Object holder, Object[] others) {
        return groupingAny(holder, "notEq", others);
    }

    public static ArelNodeNotIn notIn(Object holder, Object other) {
        if (other instanceof ArelSelectManager) {
            return new ArelNodeNotIn(holder, ((ArelSelectManager) other).ast());
        } else if (other instanceof List) {
            return new ArelNodeNotIn(holder, quotedArray(holder, (List<Object>) other));
        } else if (other instanceof Object[]) {
            return new ArelNodeNotIn(holder, quotedArray(holder, (Object[]) other));
        } else {
            return new ArelNodeNotIn(holder, quotedNode(holder, other));
        }
    }

    public static ArelNodeGrouping notInAll(Object holder, Object[] others) {
        return groupingAll(holder, "notIn", others);
    }

    public static ArelNodeGrouping notInAny(Object holder, Object[] others) {
        return groupingAny(holder, "notIn", others);
    }

    private static boolean equalsQuoted(Object maybeQuoted, Object value) {
        if (maybeQuoted instanceof ArelNodeQuoted) {
            return Objects.equals(((ArelNodeQuoted) maybeQuoted).expr(), value);
        } else {
            return Objects.equals(maybeQuoted, value);
        }
    }

    private static List<Object> getNodesByMethodId(Object holder, String methodId, Object[] others, Object[] extras) {
        List<Object> nodes = new ArrayList<>();
        for (Object expr : others) {
            try {
                Method method = ArelPredications.class.getMethod(methodId, Object.class, Object.class, Object[].class);
                nodes.add(method.invoke(null, holder, expr, extras));
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException ignored) {
                try {
                    Method method = ArelPredications.class.getMethod(methodId, Object.class, Object.class);
                    nodes.add(method.invoke(null, holder, expr));
                } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException exception) {
                    throw new RuntimeException("Cannot call " + methodId);
                }
            }
        }

        return nodes;
    }

    private static ArelNodeGrouping groupingAll(Object holder, String methodId, Object[] others, Object... extras) {
        List<Object> nodes = getNodesByMethodId(holder, methodId, others, extras);

        return new ArelNodeGrouping(new ArelNodeAnd(nodes));
    }

    private static ArelNodeGrouping groupingAny(Object holder, String methodId, Object[] others, Object... extras) {
        List<Object> nodes = getNodesByMethodId(holder, methodId, others, extras);

        Object node = nodes.get(0);
        for (int i = 1; i < nodes.size(); i++) {
            node = new ArelNodeOr(node, nodes.get(i));
        }

        return new ArelNodeGrouping(node);
    }

    private static List<Object> quotedArray(Object holder, List<Object> others) {
        for (int i = 0; i < others.size(); i++) {
            others.set(i, quotedNode(holder, others.get(i)));
        }

        return others;
    }

    private static Object[] quotedArray(Object holder, Object[] others) {
        for (int i = 0; i < others.length; i++) {
            others[i] = quotedNode(holder, others[i]);
        }

        return others;
    }

    private static Object quotedNode(Object holder, Object other) {
        return ArelNodes.buildQuoted(other, holder);
    }
}

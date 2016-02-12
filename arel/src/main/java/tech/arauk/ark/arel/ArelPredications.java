package tech.arauk.ark.arel;

import tech.arauk.ark.arel.nodes.ArelNode;
import tech.arauk.ark.arel.nodes.ArelNodeEquality;
import tech.arauk.ark.arel.nodes.ArelNodeQuoted;
import tech.arauk.ark.arel.nodes.binary.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ArelPredications {
    private Object mHolder;

    public ArelPredications(Object holder) {
        this.mHolder = holder;
    }

    public ArelNode between(Object begin, Object end) {
        return between(begin, end, true);
    }

    public ArelNode between(Object begin, Object end, boolean inclusive) {
        if (equalsQuoted(begin, Float.NEGATIVE_INFINITY)) {
            if (equalsQuoted(end, Float.POSITIVE_INFINITY)) {
                return notIn(new ArrayList<>());
            } else if (inclusive) {
                return lteq(end);
            } else {
                return lt(end);
            }
        } else if (equalsQuoted(end, Float.POSITIVE_INFINITY)) {
            return gteq(end);
        } else if (inclusive) {
            Object left = quotedNode(begin);
            Object right = quotedNode(end);
            return new ArelNodeBetween(this.mHolder, ((ArelNode) left).and(right));
        } else {
            return gt(begin).and(lt(end));
        }
    }

    public ArelNodeEquality eq(Object other) {
        return new ArelNodeEquality(this.mHolder, quotedNode(other));
    }

    public ArelNodeGreaterThan gt(Object right) {
        return new ArelNodeGreaterThan(this.mHolder, quotedNode(right));
    }

    public ArelNodeGreaterThanOrEqual gteq(Object right) {
        return new ArelNodeGreaterThanOrEqual(this.mHolder, quotedNode(right));
    }

    public ArelNodeLessThan lt(Object right) {
        return new ArelNodeLessThan(this.mHolder, quotedNode(right));
    }

    public ArelNodeLessThanOrEqual lteq(Object right) {
        return new ArelNodeLessThanOrEqual(this.mHolder, quotedNode(right));
    }

    public ArelNodeIn in(Object other) {
        if (other instanceof ArelSelectManager) {
            return new ArelNodeIn(this.mHolder, ((ArelSelectManager) other).ast);
        } else if (other instanceof List) {
            return new ArelNodeIn(this, quotedArray((List<Object>) other));
        } else {
            return new ArelNodeIn(this, quotedNode(other));
        }
    }

    public ArelNodeNotIn notIn(Object other) {
        if (other instanceof ArelSelectManager) {
            return new ArelNodeNotIn(this, ((ArelSelectManager) other).ast);
        } else if (other instanceof List) {
            return new ArelNodeNotIn(this, quotedArray((List<Object>) other));
        } else {
            return new ArelNodeNotIn(this, quotedNode(other));
        }
    }

    private boolean equalsQuoted(Object maybeQuoted, Object value) {
        if (maybeQuoted instanceof ArelNodeQuoted) {
            return Objects.equals(((ArelNodeQuoted) maybeQuoted).expr, value);
        } else {
            return Objects.equals(maybeQuoted, value);
        }
    }

    private List<Object> quotedArray(List<Object> others) {
        for (int i = 0; i < others.size(); i++) {
            others.set(i, quotedNode(others.get(i)));
        }

        return others;
    }

    private Object quotedNode(Object other) {
        return ArelNodes.buildQuoted(other, mHolder);
    }
}

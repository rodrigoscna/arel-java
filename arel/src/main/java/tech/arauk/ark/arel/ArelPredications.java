package tech.arauk.ark.arel;

import tech.arauk.ark.arel.nodes.ArelNodeEquality;
import tech.arauk.ark.arel.nodes.binary.ArelNodeGreaterThan;
import tech.arauk.ark.arel.nodes.binary.ArelNodeLessThan;

public class ArelPredications {
    private Object mHolder;

    public ArelPredications(Object holder) {
        this.mHolder = holder;
    }

    public ArelNodeEquality eq(Object other) {
        return new ArelNodeEquality(this.mHolder, quotedNode(other));
    }

    public ArelNodeGreaterThan gt(Object right) {
        return new ArelNodeGreaterThan(this.mHolder, quotedNode(right));
    }

    public ArelNodeLessThan lt(Object right) {
        return new ArelNodeLessThan(this.mHolder, quotedNode(right));
    }

    private Object quotedNode(Object other) {
        return ArelNodes.buildQuoted(other, mHolder);
    }
}

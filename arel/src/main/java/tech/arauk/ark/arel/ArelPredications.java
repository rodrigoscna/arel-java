package tech.arauk.ark.arel;

import tech.arauk.ark.arel.nodes.ArelNodeEquality;

public class ArelPredications {
    private Object mHolder;

    public ArelPredications(Object holder) {
        this.mHolder = holder;
    }

    public Object eq(Object other) {
        return new ArelNodeEquality(this.mHolder, quotedNode(other));
    }

    private Object quotedNode(Object other) {
        return ArelNodes.buildQuoted(other, mHolder);
    }
}

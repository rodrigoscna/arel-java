package tech.arauk.ark.arel;

import tech.arauk.ark.arel.nodes.ArelNodeOver;

public abstract class ArelWindowPredications {
    public static ArelNodeOver over(Object holder) {
        return over(holder, null);
    }

    public static ArelNodeOver over(Object holder, Object expr) {
        return new ArelNodeOver(holder, expr);
    }
}

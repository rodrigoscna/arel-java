package tech.arauk.ark.arel.nodes.unary;

import tech.arauk.ark.arel.nodes.ArelNodeUnary;

public class ArelNodePreceding extends ArelNodeUnary {
    public ArelNodePreceding() {
        this(null);
    }

    public ArelNodePreceding(Object expr) {
        super(expr);
    }
}

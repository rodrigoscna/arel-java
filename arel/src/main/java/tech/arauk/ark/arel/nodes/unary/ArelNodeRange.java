package tech.arauk.ark.arel.nodes.unary;

import tech.arauk.ark.arel.nodes.ArelNodeUnary;

public class ArelNodeRange extends ArelNodeUnary {
    public ArelNodeRange() {
        this(null);
    }

    public ArelNodeRange(Object expr) {
        super(expr);
    }
}

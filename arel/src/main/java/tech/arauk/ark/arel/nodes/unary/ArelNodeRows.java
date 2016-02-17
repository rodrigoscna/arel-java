package tech.arauk.ark.arel.nodes.unary;

import tech.arauk.ark.arel.nodes.ArelNodeUnary;

public class ArelNodeRows extends ArelNodeUnary {
    public ArelNodeRows() {
        this(null);
    }

    public ArelNodeRows(Object expr) {
        super(expr);
    }
}

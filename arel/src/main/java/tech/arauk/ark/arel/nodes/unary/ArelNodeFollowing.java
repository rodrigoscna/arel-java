package tech.arauk.ark.arel.nodes.unary;

import tech.arauk.ark.arel.nodes.ArelNodeUnary;

public class ArelNodeFollowing extends ArelNodeUnary {
    public ArelNodeFollowing() {
        this(null);
    }

    public ArelNodeFollowing(Object expr) {
        super(expr);
    }
}

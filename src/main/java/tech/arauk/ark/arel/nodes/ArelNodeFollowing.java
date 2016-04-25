package tech.arauk.ark.arel.nodes;

public class ArelNodeFollowing extends ArelNodeUnary {
    public ArelNodeFollowing() {
        this(null);
    }

    public ArelNodeFollowing(Object expr) {
        super(expr);
    }
}

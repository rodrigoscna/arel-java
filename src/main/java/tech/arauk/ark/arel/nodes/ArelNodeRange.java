package tech.arauk.ark.arel.nodes;

public class ArelNodeRange extends ArelNodeUnary {
    public ArelNodeRange() {
        this(null);
    }

    public ArelNodeRange(Object expr) {
        super(expr);
    }
}

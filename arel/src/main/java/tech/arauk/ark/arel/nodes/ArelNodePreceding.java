package tech.arauk.ark.arel.nodes;

public class ArelNodePreceding extends ArelNodeUnary {
    public ArelNodePreceding() {
        this(null);
    }

    public ArelNodePreceding(Object expr) {
        super(expr);
    }
}

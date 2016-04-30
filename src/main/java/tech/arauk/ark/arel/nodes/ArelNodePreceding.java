package tech.arauk.ark.arel.nodes;

import tech.arauk.ark.arel.annotations.Beta;

@Beta
public class ArelNodePreceding extends ArelNodeUnary {
    public ArelNodePreceding() {
        this(null);
    }

    public ArelNodePreceding(Object expr) {
        super(expr);
    }
}

package tech.arauk.ark.arel.nodes;

import tech.arauk.ark.arel.annotations.Beta;

@Beta
public class ArelNodeRange extends ArelNodeUnary {
    public ArelNodeRange() {
        this(null);
    }

    public ArelNodeRange(Object expr) {
        super(expr);
    }
}

package tech.arauk.ark.arel.nodes;

import tech.arauk.ark.arel.annotations.Beta;

@Beta
public class ArelNodeRows extends ArelNodeUnary {
    public ArelNodeRows() {
        this(null);
    }

    public ArelNodeRows(Object expr) {
        super(expr);
    }
}

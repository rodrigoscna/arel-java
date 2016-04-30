package tech.arauk.ark.arel.nodes;

import tech.arauk.ark.arel.annotations.Beta;

@Beta
public class ArelNodeQuoted extends ArelNodeUnary {
    public ArelNodeQuoted(Object expr) {
        super(expr);
    }
}

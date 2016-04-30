package tech.arauk.ark.arel.nodes;

import tech.arauk.ark.arel.annotations.Beta;

@Beta
public class ArelNodeDistinctOn extends ArelNodeUnary {
    public ArelNodeDistinctOn(Object expr) {
        super(expr);
    }
}

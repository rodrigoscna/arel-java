package tech.arauk.ark.arel.nodes;

import tech.arauk.ark.arel.annotations.Beta;

@Beta
public class ArelNodeLimit extends ArelNodeUnary {
    public ArelNodeLimit(Object expr) {
        super(expr);
    }
}

package tech.arauk.ark.arel.nodes;

import tech.arauk.ark.arel.annotations.Beta;

@Beta
public class ArelNodeLock extends ArelNodeUnary {
    public ArelNodeLock(Object expr) {
        super(expr);
    }
}

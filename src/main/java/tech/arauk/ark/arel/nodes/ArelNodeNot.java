package tech.arauk.ark.arel.nodes;

import tech.arauk.ark.arel.annotations.Beta;

@Beta
public class ArelNodeNot extends ArelNodeUnary {
    public ArelNodeNot(Object expr) {
        super(expr);
    }
}

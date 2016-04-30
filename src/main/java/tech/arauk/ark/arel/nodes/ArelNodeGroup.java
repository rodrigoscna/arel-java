package tech.arauk.ark.arel.nodes;

import tech.arauk.ark.arel.annotations.Beta;

@Beta
public class ArelNodeGroup extends ArelNodeUnary {
    public ArelNodeGroup(Object expr) {
        super(expr);
    }
}

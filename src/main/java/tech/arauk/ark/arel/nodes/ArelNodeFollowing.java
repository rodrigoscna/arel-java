package tech.arauk.ark.arel.nodes;

import tech.arauk.ark.arel.annotations.Beta;

@Beta
public class ArelNodeFollowing extends ArelNodeUnary {
    public ArelNodeFollowing() {
        this(null);
    }

    public ArelNodeFollowing(Object expr) {
        super(expr);
    }
}

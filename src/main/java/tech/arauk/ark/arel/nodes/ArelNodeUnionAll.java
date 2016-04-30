package tech.arauk.ark.arel.nodes;

import tech.arauk.ark.arel.annotations.Beta;

@Beta
public class ArelNodeUnionAll extends ArelNodeBinary {
    public ArelNodeUnionAll(Object left, Object right) {
        super(left, right);
    }
}

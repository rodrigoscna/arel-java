package tech.arauk.ark.arel.nodes;

import tech.arauk.ark.arel.annotations.Beta;

@Beta
public class ArelNodeUnion extends ArelNodeBinary {
    public ArelNodeUnion(Object left, Object right) {
        super(left, right);
    }
}

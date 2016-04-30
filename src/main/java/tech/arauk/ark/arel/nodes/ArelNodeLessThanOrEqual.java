package tech.arauk.ark.arel.nodes;

import tech.arauk.ark.arel.annotations.Beta;

@Beta
public class ArelNodeLessThanOrEqual extends ArelNodeBinary {
    public ArelNodeLessThanOrEqual(Object left, Object right) {
        super(left, right);
    }
}

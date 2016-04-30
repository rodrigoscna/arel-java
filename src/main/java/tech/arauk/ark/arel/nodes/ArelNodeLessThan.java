package tech.arauk.ark.arel.nodes;

import tech.arauk.ark.arel.annotations.Beta;

@Beta
public class ArelNodeLessThan extends ArelNodeBinary {
    public ArelNodeLessThan(Object left, Object right) {
        super(left, right);
    }
}

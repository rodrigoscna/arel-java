package tech.arauk.ark.arel.nodes;

import tech.arauk.ark.arel.annotations.Beta;

@Beta
public class ArelNodeOr extends ArelNodeBinary {
    public ArelNodeOr(Object left, Object right) {
        super(left, right);
    }
}

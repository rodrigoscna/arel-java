package tech.arauk.ark.arel.nodes;

import tech.arauk.ark.arel.annotations.Beta;

@Beta
public class ArelNodeExcept extends ArelNodeBinary {
    public ArelNodeExcept(Object left, Object right) {
        super(left, right);
    }
}

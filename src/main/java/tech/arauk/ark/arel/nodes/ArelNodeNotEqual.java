package tech.arauk.ark.arel.nodes;

import tech.arauk.ark.arel.annotations.Beta;

@Beta
public class ArelNodeNotEqual extends ArelNodeBinary {
    public ArelNodeNotEqual(Object left, Object right) {
        super(left, right);
    }
}

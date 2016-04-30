package tech.arauk.ark.arel.nodes;

import tech.arauk.ark.arel.annotations.Beta;

@Beta
public class ArelNodeNotIn extends ArelNodeBinary {
    public ArelNodeNotIn(Object left, Object right) {
        super(left, right);
    }
}

package tech.arauk.ark.arel.nodes;

import tech.arauk.ark.arel.annotations.Beta;

@Beta
public class ArelNodeIn extends ArelNodeBinary {
    public ArelNodeIn(Object left, Object right) {
        super(left, right);
    }
}

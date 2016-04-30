package tech.arauk.ark.arel.nodes;

import tech.arauk.ark.arel.annotations.Beta;

@Beta
public class ArelNodeBetween extends ArelNodeBinary {
    public ArelNodeBetween(Object left, Object right) {
        super(left, right);
    }
}

package tech.arauk.ark.arel.nodes;

import tech.arauk.ark.arel.annotations.Beta;

@Beta
public class ArelNodeGreaterThan extends ArelNodeBinary {
    public ArelNodeGreaterThan(Object left, Object right) {
        super(left, right);
    }
}

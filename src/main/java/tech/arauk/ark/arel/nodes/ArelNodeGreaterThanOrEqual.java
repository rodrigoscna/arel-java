package tech.arauk.ark.arel.nodes;

import tech.arauk.ark.arel.annotations.Beta;

@Beta
public class ArelNodeGreaterThanOrEqual extends ArelNodeBinary {
    public ArelNodeGreaterThanOrEqual(Object left, Object right) {
        super(left, right);
    }
}

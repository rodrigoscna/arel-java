package tech.arauk.ark.arel.nodes;

import tech.arauk.ark.arel.annotations.Beta;

@Beta
public class ArelNodeJoin extends ArelNodeBinary {
    public ArelNodeJoin(Object left, Object right) {
        super(left, right);
    }
}

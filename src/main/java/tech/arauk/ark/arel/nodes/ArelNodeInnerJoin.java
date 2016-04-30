package tech.arauk.ark.arel.nodes;

import tech.arauk.ark.arel.annotations.Beta;

@Beta
public class ArelNodeInnerJoin extends ArelNodeJoin {
    public ArelNodeInnerJoin(Object left, Object right) {
        super(left, right);
    }
}

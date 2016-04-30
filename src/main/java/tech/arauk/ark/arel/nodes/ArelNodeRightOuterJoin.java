package tech.arauk.ark.arel.nodes;

import tech.arauk.ark.arel.annotations.Beta;

@Beta
public class ArelNodeRightOuterJoin extends ArelNodeJoin {
    public ArelNodeRightOuterJoin(Object left, Object right) {
        super(left, right);
    }
}

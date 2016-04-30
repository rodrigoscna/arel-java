package tech.arauk.ark.arel.nodes;

import tech.arauk.ark.arel.annotations.Beta;

@Beta
public class ArelNodeOuterJoin extends ArelNodeJoin {
    public ArelNodeOuterJoin(Object left, Object right) {
        super(left, right);
    }
}

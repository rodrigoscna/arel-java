package tech.arauk.ark.arel.nodes;

import tech.arauk.ark.arel.annotations.Beta;

@Beta
public class ArelNodeFullOuterJoin extends ArelNodeJoin {
    public ArelNodeFullOuterJoin(Object left, Object right) {
        super(left, right);
    }
}

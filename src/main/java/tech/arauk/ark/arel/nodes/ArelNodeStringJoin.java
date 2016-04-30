package tech.arauk.ark.arel.nodes;

import tech.arauk.ark.arel.annotations.Beta;

@Beta
public class ArelNodeStringJoin extends ArelNodeJoin {
    public ArelNodeStringJoin(Object left) {
        super(left, null);
    }

    public ArelNodeStringJoin(Object left, Object right) {
        super(left, right);
    }
}

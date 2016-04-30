package tech.arauk.ark.arel.nodes;

import tech.arauk.ark.arel.annotations.Beta;

@Beta
public class ArelNodeIntersect extends ArelNodeBinary {
    public ArelNodeIntersect(Object left, Object right) {
        super(left, right);
    }
}

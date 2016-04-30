package tech.arauk.ark.arel.nodes;

import tech.arauk.ark.arel.annotations.Beta;

@Beta
public class ArelNodeAssignment extends ArelNodeBinary {
    public ArelNodeAssignment(Object left, Object right) {
        super(left, right);
    }
}

package tech.arauk.ark.arel.nodes;

import tech.arauk.ark.arel.annotations.Beta;

@Beta
public class ArelNodeAddition extends ArelNodeInfixOperation {
    public ArelNodeAddition(Object left, Object right) {
        super("+", left, right);
    }
}

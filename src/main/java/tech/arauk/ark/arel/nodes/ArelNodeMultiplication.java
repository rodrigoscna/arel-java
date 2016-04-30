package tech.arauk.ark.arel.nodes;

import tech.arauk.ark.arel.annotations.Beta;

@Beta
public class ArelNodeMultiplication extends ArelNodeInfixOperation {
    public ArelNodeMultiplication(Object left, Object right) {
        super("*", left, right);
    }
}

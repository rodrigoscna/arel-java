package tech.arauk.ark.arel.nodes;

import tech.arauk.ark.arel.annotations.Beta;

@Beta
public class ArelNodeSubtraction extends ArelNodeInfixOperation {
    public ArelNodeSubtraction(Object left, Object right) {
        super("-", left, right);
    }
}

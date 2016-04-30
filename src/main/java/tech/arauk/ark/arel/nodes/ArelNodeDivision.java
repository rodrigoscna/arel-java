package tech.arauk.ark.arel.nodes;

import tech.arauk.ark.arel.annotations.Beta;

@Beta
public class ArelNodeDivision extends ArelNodeInfixOperation {
    public ArelNodeDivision(Object left, Object right) {
        super("/", left, right);
    }
}

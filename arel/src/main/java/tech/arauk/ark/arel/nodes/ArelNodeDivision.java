package tech.arauk.ark.arel.nodes;

public class ArelNodeDivision extends ArelNodeInfixOperation {
    public ArelNodeDivision(Object left, Object right) {
        super("/", left, right);
    }
}

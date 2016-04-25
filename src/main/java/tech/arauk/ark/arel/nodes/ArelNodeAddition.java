package tech.arauk.ark.arel.nodes;

public class ArelNodeAddition extends ArelNodeInfixOperation {
    public ArelNodeAddition(Object left, Object right) {
        super("+", left, right);
    }
}

package tech.arauk.ark.arel.nodes;

public class ArelNodeMultiplication extends ArelNodeInfixOperation {
    public ArelNodeMultiplication(Object left, Object right) {
        super("*", left, right);
    }
}

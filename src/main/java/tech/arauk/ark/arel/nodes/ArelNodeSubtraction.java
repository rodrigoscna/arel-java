package tech.arauk.ark.arel.nodes;

public class ArelNodeSubtraction extends ArelNodeInfixOperation {
    public ArelNodeSubtraction(Object left, Object right) {
        super("-", left, right);
    }
}

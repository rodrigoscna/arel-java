package tech.arauk.ark.arel.nodes;

public class ArelNodeSum extends ArelNodeFunction {
    public ArelNodeSum(Object expr) {
        super(expr);
    }

    public ArelNodeSum(Object expr, String alias) {
        super(expr, alias);
    }
}

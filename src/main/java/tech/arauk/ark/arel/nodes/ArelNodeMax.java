package tech.arauk.ark.arel.nodes;

public class ArelNodeMax extends ArelNodeFunction {
    public ArelNodeMax(Object expr) {
        super(expr);
    }

    public ArelNodeMax(Object expr, String alias) {
        super(expr, alias);
    }
}

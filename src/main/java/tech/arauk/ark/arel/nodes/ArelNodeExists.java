package tech.arauk.ark.arel.nodes;

public class ArelNodeExists extends ArelNodeFunction {
    public ArelNodeExists(Object expr) {
        super(expr);
    }

    public ArelNodeExists(Object expr, String alias) {
        super(expr, alias);
    }
}

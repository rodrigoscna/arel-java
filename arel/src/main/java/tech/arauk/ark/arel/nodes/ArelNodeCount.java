package tech.arauk.ark.arel.nodes;

public class ArelNodeCount extends ArelNodeFunction {
    public ArelNodeCount(Object expr) {
        super(expr);
    }

    public ArelNodeCount(Object expr, Boolean distinct) {
        super(expr);

        distinct(distinct);
    }

    public ArelNodeCount(Object expr, String alias) {
        super(expr, alias);
    }

    public ArelNodeCount(Object expr, Boolean distinct, String alias) {
        super(expr, alias);

        distinct(distinct);
    }
}

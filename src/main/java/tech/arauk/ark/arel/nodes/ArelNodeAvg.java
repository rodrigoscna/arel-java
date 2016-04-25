package tech.arauk.ark.arel.nodes;

public class ArelNodeAvg extends ArelNodeFunction {
    public ArelNodeAvg(Object expr) {
        super(expr);
    }

    public ArelNodeAvg(Object expr, String alias) {
        super(expr, alias);
    }
}

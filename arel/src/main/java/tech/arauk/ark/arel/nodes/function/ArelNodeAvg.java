package tech.arauk.ark.arel.nodes.function;

import tech.arauk.ark.arel.nodes.ArelNodeFunction;

public class ArelNodeAvg extends ArelNodeFunction {
    public ArelNodeAvg(Object expr) {
        super(expr);
    }

    public ArelNodeAvg(Object expr, String alias) {
        super(expr, alias);
    }
}

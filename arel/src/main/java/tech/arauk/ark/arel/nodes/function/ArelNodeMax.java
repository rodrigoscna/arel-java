package tech.arauk.ark.arel.nodes.function;

import tech.arauk.ark.arel.nodes.ArelNodeFunction;

public class ArelNodeMax extends ArelNodeFunction {
    public ArelNodeMax(Object expr) {
        super(expr);
    }

    public ArelNodeMax(Object expr, String alias) {
        super(expr, alias);
    }
}

package tech.arauk.ark.arel.nodes.function;

import tech.arauk.ark.arel.nodes.ArelNodeFunction;

public class ArelNodeMin extends ArelNodeFunction {
    public ArelNodeMin(Object expr) {
        super(expr);
    }

    public ArelNodeMin(Object expr, String alias) {
        super(expr, alias);
    }
}

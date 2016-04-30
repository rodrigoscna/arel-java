package tech.arauk.ark.arel.nodes;

import tech.arauk.ark.arel.annotations.Beta;

@Beta
public class ArelNodeMin extends ArelNodeFunction {
    public ArelNodeMin(Object expr) {
        super(expr);
    }

    public ArelNodeMin(Object expr, String alias) {
        super(expr, alias);
    }
}

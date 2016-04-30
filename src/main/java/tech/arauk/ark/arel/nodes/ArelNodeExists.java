package tech.arauk.ark.arel.nodes;

import tech.arauk.ark.arel.annotations.Beta;

@Beta
public class ArelNodeExists extends ArelNodeFunction {
    public ArelNodeExists(Object expr) {
        super(expr);
    }

    public ArelNodeExists(Object expr, String alias) {
        super(expr, alias);
    }
}

package tech.arauk.ark.arel.nodes;

import tech.arauk.ark.arel.annotations.Beta;

@Beta
public class ArelNodeSum extends ArelNodeFunction {
    public ArelNodeSum(Object expr) {
        super(expr);
    }

    public ArelNodeSum(Object expr, String alias) {
        super(expr, alias);
    }
}

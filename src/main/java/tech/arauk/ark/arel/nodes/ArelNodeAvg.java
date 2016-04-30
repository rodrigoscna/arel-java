package tech.arauk.ark.arel.nodes;

import tech.arauk.ark.arel.annotations.Beta;

@Beta
public class ArelNodeAvg extends ArelNodeFunction {
    public ArelNodeAvg(Object expr) {
        super(expr);
    }

    public ArelNodeAvg(Object expr, String alias) {
        super(expr, alias);
    }
}

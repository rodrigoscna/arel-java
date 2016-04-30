package tech.arauk.ark.arel.nodes;

import tech.arauk.ark.arel.annotations.Beta;

@Beta
public class ArelNodeBin extends ArelNodeUnary {
    public ArelNodeBin(Object expr) {
        super(expr);
    }
}

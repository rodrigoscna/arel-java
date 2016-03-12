package tech.arauk.ark.arel.nodes;

import tech.arauk.ark.arel.ArelOrderPredications;
import tech.arauk.ark.arel.ArelOrderPredicationsInterface;

public class ArelNodeGrouping extends ArelNodeUnary implements ArelOrderPredicationsInterface {
    public ArelNodeGrouping(Object expr) {
        super(expr);
    }

    @Override
    public ArelNodeAscending asc() {
        return ArelOrderPredications.asc(this);
    }

    @Override
    public ArelNodeDescending desc() {
        return ArelOrderPredications.desc(this);
    }
}

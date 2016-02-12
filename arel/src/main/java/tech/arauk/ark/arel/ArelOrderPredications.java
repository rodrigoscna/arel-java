package tech.arauk.ark.arel;

import tech.arauk.ark.arel.nodes.ArelNodeAscending;
import tech.arauk.ark.arel.nodes.ArelNodeDescending;

public class ArelOrderPredications {
    private Object mHolder;

    public ArelOrderPredications(Object holder) {
        this.mHolder = holder;
    }

    public ArelNodeAscending asc() {
        return new ArelNodeAscending(this.mHolder);
    }

    public ArelNodeDescending desc() {
        return new ArelNodeDescending(this.mHolder);
    }
}

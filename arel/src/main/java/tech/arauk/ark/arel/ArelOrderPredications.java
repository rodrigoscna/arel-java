package tech.arauk.ark.arel;

import tech.arauk.ark.arel.nodes.ArelNodeAscending;
import tech.arauk.ark.arel.nodes.ArelNodeDescending;

public abstract class ArelOrderPredications {
    public static ArelNodeAscending asc(Object holder) {
        return new ArelNodeAscending(holder);
    }

    public static ArelNodeDescending desc(Object holder) {
        return new ArelNodeDescending(holder);
    }
}

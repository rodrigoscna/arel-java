package tech.arauk.ark.arel;

import tech.arauk.ark.arel.annotations.Beta;
import tech.arauk.ark.arel.nodes.ArelNodeAscending;
import tech.arauk.ark.arel.nodes.ArelNodeDescending;

@Beta
public abstract class ArelOrderPredications {
    public static ArelNodeAscending asc(Object holder) {
        return new ArelNodeAscending(holder);
    }

    public static ArelNodeDescending desc(Object holder) {
        return new ArelNodeDescending(holder);
    }
}

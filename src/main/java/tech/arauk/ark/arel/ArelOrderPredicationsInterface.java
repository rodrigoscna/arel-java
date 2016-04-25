package tech.arauk.ark.arel;

import tech.arauk.ark.arel.nodes.ArelNodeAscending;
import tech.arauk.ark.arel.nodes.ArelNodeDescending;

public interface ArelOrderPredicationsInterface {
    ArelNodeAscending asc();

    ArelNodeDescending desc();
}

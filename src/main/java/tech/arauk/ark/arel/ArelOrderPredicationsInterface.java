package tech.arauk.ark.arel;

import tech.arauk.ark.arel.annotations.Beta;
import tech.arauk.ark.arel.nodes.ArelNodeAscending;
import tech.arauk.ark.arel.nodes.ArelNodeDescending;

@Beta
public interface ArelOrderPredicationsInterface {
    ArelNodeAscending asc();

    ArelNodeDescending desc();
}

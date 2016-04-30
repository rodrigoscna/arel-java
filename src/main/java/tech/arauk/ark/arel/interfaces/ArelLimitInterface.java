package tech.arauk.ark.arel.interfaces;

import tech.arauk.ark.arel.annotations.Beta;
import tech.arauk.ark.arel.nodes.ArelNodeLimit;

@Beta
public interface ArelLimitInterface {
    ArelNodeLimit limit();

    ArelLimitInterface limit(Object limit);
}

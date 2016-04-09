package tech.arauk.ark.arel.interfaces;

import tech.arauk.ark.arel.nodes.ArelNodeLimit;

public interface ArelLimitInterface {
    ArelNodeLimit limit();

    ArelLimitInterface limit(ArelNodeLimit limit);
}

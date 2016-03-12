package tech.arauk.ark.arel;

import tech.arauk.ark.arel.nodes.ArelNodeCount;

public interface ArelExpressionsInterface {
    ArelNodeCount count();

    ArelNodeCount count(Boolean distinct);
}

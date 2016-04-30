package tech.arauk.ark.arel.interfaces;

import tech.arauk.ark.arel.annotations.Beta;
import tech.arauk.ark.arel.nodes.ArelNodeTop;

@Beta
public interface ArelTopInterface {
    ArelNodeTop top();

    ArelTopInterface top(ArelNodeTop top);
}

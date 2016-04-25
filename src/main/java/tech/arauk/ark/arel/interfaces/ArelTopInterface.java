package tech.arauk.ark.arel.interfaces;

import tech.arauk.ark.arel.nodes.ArelNodeTop;

public interface ArelTopInterface {
    ArelNodeTop top();

    ArelTopInterface top(ArelNodeTop top);
}

package tech.arauk.ark.arel.interfaces;

import tech.arauk.ark.arel.nodes.unary.ArelNodeLock;

public interface ArelLockInterface {
    ArelNodeLock lock();

    ArelLockInterface lock(ArelNodeLock lock);
}

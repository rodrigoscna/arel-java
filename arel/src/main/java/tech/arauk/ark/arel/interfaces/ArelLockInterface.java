package tech.arauk.ark.arel.interfaces;

import tech.arauk.ark.arel.nodes.ArelNodeLock;

public interface ArelLockInterface {
    ArelNodeLock lock();

    ArelLockInterface lock(Object lock);
}

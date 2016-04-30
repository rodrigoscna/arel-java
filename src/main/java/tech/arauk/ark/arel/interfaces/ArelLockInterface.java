package tech.arauk.ark.arel.interfaces;

import tech.arauk.ark.arel.annotations.Beta;
import tech.arauk.ark.arel.nodes.ArelNodeLock;

@Beta
public interface ArelLockInterface {
    ArelNodeLock lock();

    ArelLockInterface lock(Object lock);
}

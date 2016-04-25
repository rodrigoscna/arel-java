package tech.arauk.ark.arel.interfaces;

import tech.arauk.ark.arel.nodes.ArelNodeSelectCore;

public interface ArelCoresInterface {
    ArelNodeSelectCore[] cores();

    ArelCoresInterface cores(ArelNodeSelectCore[] cores);
}

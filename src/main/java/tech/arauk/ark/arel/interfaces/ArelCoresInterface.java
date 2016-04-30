package tech.arauk.ark.arel.interfaces;

import tech.arauk.ark.arel.annotations.Beta;
import tech.arauk.ark.arel.nodes.ArelNodeSelectCore;

@Beta
public interface ArelCoresInterface {
    ArelNodeSelectCore[] cores();

    ArelCoresInterface cores(ArelNodeSelectCore[] cores);
}

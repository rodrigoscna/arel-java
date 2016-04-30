package tech.arauk.ark.arel.interfaces;

import tech.arauk.ark.arel.annotations.Beta;

@Beta
public interface ArelKeyInterface {
    Object key();

    ArelKeyInterface key(Object key);
}

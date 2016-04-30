package tech.arauk.ark.arel.interfaces;

import tech.arauk.ark.arel.annotations.Beta;

@Beta
public interface ArelNameInterface {
    String name();

    ArelNameInterface name(String name);
}

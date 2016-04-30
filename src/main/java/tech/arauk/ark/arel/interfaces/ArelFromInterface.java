package tech.arauk.ark.arel.interfaces;

import tech.arauk.ark.arel.annotations.Beta;

@Beta
public interface ArelFromInterface {
    Object from();

    ArelFromInterface from(Object from);
}

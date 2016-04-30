package tech.arauk.ark.arel.interfaces;

import tech.arauk.ark.arel.annotations.Beta;

@Beta
public interface ArelRelationInterface {
    Object relation();

    ArelRelationInterface relation(Object relation);
}

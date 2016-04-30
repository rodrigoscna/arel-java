package tech.arauk.ark.arel.interfaces;

import tech.arauk.ark.arel.annotations.Beta;

import java.util.List;

@Beta
public interface ArelWheresInterface {
    List<Object> wheres();

    ArelWheresInterface wheres(Object wheres);
}

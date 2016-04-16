package tech.arauk.ark.arel.interfaces;

import java.util.List;

public interface ArelWheresInterface {
    List<Object> wheres();

    ArelWheresInterface wheres(Object wheres);
}

package tech.arauk.ark.arel.interfaces;

import java.util.List;

public interface ArelValuesInterface {
    List<Object> values();

    ArelValuesInterface values(Object values);
}

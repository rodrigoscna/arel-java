package tech.arauk.ark.arel.connection;

import java.util.Map;

public interface SchemaCache {
    boolean tableExists(Object name);

    Map<Object, Object> columnsHash(Object table);
}

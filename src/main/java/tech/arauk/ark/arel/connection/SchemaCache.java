package tech.arauk.ark.arel.connection;

import tech.arauk.ark.arel.annotations.Beta;

import java.util.Map;

@Beta
public interface SchemaCache {
    boolean tableExists(Object name);

    Map<Object, Object> columnsHash(Object table);
}

package tech.arauk.ark.arel.connection;

import tech.arauk.ark.arel.annotations.Beta;

@Beta
public interface ArelTypeCaster {
    Object typeCastForDatabase(Object attributeName, Object value);
}

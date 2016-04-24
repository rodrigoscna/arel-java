package tech.arauk.ark.arel.connection;

public interface ArelTypeCaster {
    Object typeCastForDatabase(Object attributeName, Object value);
}

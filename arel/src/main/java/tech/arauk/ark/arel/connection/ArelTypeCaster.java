package tech.arauk.ark.arel.connection;

public interface ArelTypeCaster {
    Object typeCastForDatabase(String attributeName, Object value);
}

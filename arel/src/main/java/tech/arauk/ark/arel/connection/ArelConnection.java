package tech.arauk.ark.arel.connection;

import tech.arauk.ark.arel.visitors.ArelVisitor;

public interface ArelConnection {
    ArelVisitor getVisitor();

    Object quote(Object value);

    String quoteColumnName(String columnName);

    String quoteTableName(String tableName);
}

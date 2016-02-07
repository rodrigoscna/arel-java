package tech.arauk.ark.arel.support;

import tech.arauk.ark.arel.connection.ArelConnection;
import tech.arauk.ark.arel.connection.ArelTypeCaster;
import tech.arauk.ark.arel.visitors.ArelVisitor;
import tech.arauk.ark.arel.visitors.ArelVisitorToSql;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FakeRecord {
    public static class Column {
    }

    public static class Connection implements ArelConnection {
        private ArelVisitorToSql arelVisitor;

        @Override
        public ArelVisitor getVisitor() {
            return arelVisitor;
        }

        @Override
        public String quoteColumnName(String columnName) {
            return String.format("\"%s\"", columnName);
        }

        @Override
        public String quoteTableName(String tableName) {
            return String.format("\"%s\"", tableName);
        }

        @Override
        public Object quote(Object value) {
            if (value instanceof Date) {
                return String.format("'%s'", new SimpleDateFormat("yyyy-MM-dd H:m:s").format(((Date) value)));
            } else if (value instanceof Boolean) {
                if ((Boolean) value) {
                    return "'t'";
                } else {
                    return "'f'";
                }
            } else if (value == null) {
                return "NULL";
            } else if (value instanceof Number) {
                return value;
            } else {
                return String.format("'%s'", String.valueOf(value).replaceAll("'", "\\\\'"));
            }
        }
    }

    public static class ConnectionPool {
        private Connection connection;

        public ConnectionPool() {
            this.connection = new Connection();
            this.connection.arelVisitor = new ArelVisitorToSql(this.connection);
        }
    }

    public static class TypeCaster implements ArelTypeCaster {
        @Override
        public Object typeCastForDatabase(String attributeName, Object value) {
            if ("id".equals(attributeName)) {
                return Integer.valueOf(String.valueOf(value));
            } else {
                return value;
            }
        }
    }

    public static class Base extends ArelVisitor {
        private ConnectionPool connectionPool;

        public Base() {
            super(new ConnectionPool().connection);
        }
    }
}

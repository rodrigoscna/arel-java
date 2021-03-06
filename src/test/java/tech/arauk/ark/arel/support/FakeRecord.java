package tech.arauk.ark.arel.support;

import tech.arauk.ark.arel.connection.ArelConnection;
import tech.arauk.ark.arel.connection.ArelTypeCaster;
import tech.arauk.ark.arel.connection.SchemaCache;
import tech.arauk.ark.arel.visitors.ArelVisitor;
import tech.arauk.ark.arel.visitors.ArelVisitorToSql;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FakeRecord {
    public static class Column {
        public String name;
        public String type;

        public Column(String name, String type) {
            this.name = name;
            this.type = type;
        }
    }

    public static class Connection implements ArelConnection, SchemaCache {
        public ArelVisitor visitor;
        private List<String> tables;
        private Map<String, Column[]> columns;
        private Map<String, Map<Object, Object>> columnsHash;
        private Map<String, String> primaryKeys;

        public Connection() {
            this(null);
        }

        public Connection(ArelVisitor visitor) {
            this.tables = new ArrayList<>();
            this.tables.add("developers");
            this.tables.add("photos");
            this.tables.add("products");
            this.tables.add("users");

            this.columns = new HashMap<>();
            this.columns.put("users", new Column[]{
                    new Column("id", "integer"),
                    new Column("name", "string"),
                    new Column("bool", "boolean"),
                    new Column("created_at", "datetime"),
                    new Column("created_at_date", "date"),
                    new Column("created_at_time", "time"),
            });
            this.columns.put("products", new Column[]{
                    new Column("id", "integer"),
                    new Column("price", "decimal"),
            });

            this.columnsHash = new HashMap<>();
            this.columnsHash.put("users", Connection.hashMapFor(this.columns.get("users")));
            this.columnsHash.put("products", Connection.hashMapFor(this.columns.get("products")));

            this.primaryKeys = new HashMap<>();
            this.primaryKeys.put("users", "id");
            this.primaryKeys.put("products", "id");

            this.visitor = visitor;
        }

        private static Map<Object, Object> hashMapFor(Column[] columns) {
            Map<Object, Object> hashMap = new HashMap<>();
            for (Column column : columns) {
                hashMap.put(column.name, column);
            }
            return hashMap;
        }

        @Override
        public Map<Object, Object> columnsHash(Object table) {
            return this.columnsHash.get(table);
        }

        @Override
        public ArelVisitor getVisitor() {
            return visitor;
        }

        @Override
        public Object quote(Object thing) {
            if (thing instanceof Date) {
                return String.format("'%s'", new SimpleDateFormat("yyyy-MM-dd H:m:s").format(((Date) thing)));
            } else if (thing instanceof Boolean) {
                if ((Boolean) thing) {
                    return "'t'";
                } else {
                    return "'f'";
                }
            } else if (thing == null) {
                return "NULL";
            } else if (thing instanceof Number) {
                return thing;
            } else {
                return String.format("'%s'", String.valueOf(thing).replaceAll("'", "\\\\'"));
            }
        }

        @Override
        public Object quote(Object thing, Object column) {
            Object convertedThing = thing;

            if (column != null && thing != null) {
                String type = ((Column) column).type;

                switch (type) {
                    case "date":
                        convertedThing = new SimpleDateFormat("yyyy-MM-dd").format(((Date) thing));
                        break;
                    case "datetime":
                        convertedThing = new SimpleDateFormat("yyyy-MM-dd H:m:s").format(((Date) thing));
                        break;
                    case "integer":
                        StringBuilder integerThing = new StringBuilder();

                        Pattern pattern = Pattern.compile("^(\\-)?(\\d+)");
                        Matcher matcher = pattern.matcher(String.valueOf(thing));

                        while (matcher.find()) {
                            integerThing.append(matcher.group());
                        }

                        convertedThing = Integer.valueOf(integerThing.toString());
                        break;
                    case "string":
                        convertedThing = String.valueOf(thing);
                        break;
                    case "time":
                        convertedThing = new SimpleDateFormat("H:m:s").format(((Date) thing));
                        break;
                }
            }

            return quote(convertedThing);
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
        public SchemaCache schemaCache() {
            return this;
        }

        @Override
        public boolean tableExists(Object name) {
            return this.tables.contains(name);
        }
    }

    public static class ConnectionPool {
        private Connection connection;

        public ConnectionPool() {
            this.connection = new Connection();
            this.connection.visitor = new ArelVisitorToSql(this.connection);
        }
    }

    public static class Base extends ArelVisitor {
        private ConnectionPool connectionPool;

        public Base() {
            super(new ConnectionPool().connection);
        }
    }
}

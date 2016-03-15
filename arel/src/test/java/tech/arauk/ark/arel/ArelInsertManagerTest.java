package tech.arauk.ark.arel;

import junit.framework.TestCase;
import tech.arauk.ark.arel.nodes.binary.ArelNodeValues;
import tech.arauk.ark.arel.support.FakeRecord;

import java.util.*;

public class ArelInsertManagerTest {
    public static abstract class Base extends TestCase {
        static {
            ArelTable.engine = new FakeRecord.Base();
        }

        @Override
        protected void setUp() throws Exception {
            super.setUp();
        }

        @Override
        protected void tearDown() throws Exception {
            super.tearDown();
        }
    }

    public static class Columns extends Base {
        public void testColumns() {
            ArelTable table = new ArelTable("users");

            ArelInsertManager insertManager = new ArelInsertManager();
            insertManager.into(table);
            insertManager.columns().add(table.get("id"));

            assertEquals("INSERT INTO \"users\" (\"id\")", insertManager.toSQL());
        }
    }

    public static class Combo extends Base {
        public void testColumnsAndValues() {
            ArelTable table = new ArelTable("users");

            List<Object> values = new ArrayList<>();
            values.add(1);
            values.add("aaron");

            ArelInsertManager insertManager = new ArelInsertManager();
            insertManager.into(table);
            insertManager.values(new ArelNodeValues(values));
            insertManager.columns().add(table.get("id"));
            insertManager.columns().add(table.get("name"));

            assertEquals("INSERT INTO \"users\" (\"id\", \"name\") VALUES (1, 'aaron')", insertManager.toSQL());
        }
    }

    public static class Insert extends Base {
        public void testInsertCreateValues() {
            List<Object> values = new ArrayList<>();
            values.add("a");
            values.add("b");
            List<Object> columns = new ArrayList<>();
            columns.add("c");
            columns.add("d");

            ArelInsertManager insertManager = new ArelInsertManager();
            Object insertManagerValues = insertManager.createValues(values, columns);

            assertSame(ArelNodeValues.class, insertManagerValues.getClass());
            assertEquals(values, ((ArelNodeValues) insertManagerValues).left());
            assertEquals(columns, ((ArelNodeValues) insertManagerValues).right());
        }

        public void testInsertDate() {
            ArelTable table = new ArelTable("users");

            Date date = new Date();

            Map<Object, Object> values = new LinkedHashMap<>();
            values.put(table.get("created_at"), date);

            ArelInsertManager insertManager = new ArelInsertManager();
            insertManager.insert(values);

            assertEquals(String.format("INSERT INTO \"users\" (\"created_at\") VALUES (%s)", ArelTable.engine.connection.quote(date)), insertManager.toSQL());
        }

        public void testInsertDefaultTable() {
            ArelTable table = new ArelTable("users");

            Map<Object, Object> values = new LinkedHashMap<>();
            values.put(table.get("id"), 1);
            values.put(table.get("name"), "aaron");

            ArelInsertManager insertManager = new ArelInsertManager();
            insertManager.insert(values);

            assertEquals("INSERT INTO \"users\" (\"id\", \"name\") VALUES (1, 'aaron')", insertManager.toSQL());
        }

        public void testInsertEmptyOverwrite() {
            ArelTable table = new ArelTable("users");

            Map<Object, Object> values = new LinkedHashMap<>();
            values.put(table.get("id"), 1);

            ArelInsertManager insertManager = new ArelInsertManager();
            insertManager.insert(values);
            insertManager.insert(new LinkedHashMap<>());

            assertEquals("INSERT INTO \"users\" (\"id\") VALUES (1)", insertManager.toSQL());
        }

        public void testInsertFalse() {
            ArelTable table = new ArelTable("users");

            ArelInsertManager insertManager = new ArelInsertManager();

            Map<Object, Object> values = new LinkedHashMap<>();
            values.put(table.get("bool"), false);
            insertManager.insert(values);

            assertEquals("INSERT INTO \"users\" (\"bool\") VALUES ('f')", insertManager.toSQL());
        }

        public void testInsertList() {
            ArelTable table = new ArelTable("users");

            Map<Object, Object> values = new LinkedHashMap<>();
            values.put(table.get("id"), 1);
            values.put(table.get("name"), "aaron");

            ArelInsertManager insertManager = new ArelInsertManager();
            insertManager.into(table);
            insertManager.insert(values);

            assertEquals("INSERT INTO \"users\" (\"id\", \"name\") VALUES (1, 'aaron')", insertManager.toSQL());
        }

        public void testInsertNull() {
            ArelTable table = new ArelTable("users");

            ArelInsertManager insertManager = new ArelInsertManager();

            Map<Object, Object> values = new LinkedHashMap<>();
            values.put(table.get("id"), null);
            insertManager.insert(values);

            assertEquals("INSERT INTO \"users\" (\"id\") VALUES (NULL)", insertManager.toSQL());
        }

        public void testInsertSQLLiterals() {
            List<Object> values = new ArrayList<>();
            values.add(Arel.sql("*"));
            List<Object> columns = new ArrayList<>();
            columns.add("a");

            ArelInsertManager insertManager = new ArelInsertManager();
            insertManager.into(new ArelTable("users"));
            insertManager.values(insertManager.createValues(values, columns));

            assertEquals("INSERT INTO \"users\" VALUES (*)", insertManager.toSQL());
        }
    }

    public static class Into extends Base {
        public void testIntoMethodChain() {
            ArelInsertManager insertManager = new ArelInsertManager();

            assertEquals(insertManager, insertManager.into(new ArelTable("users")));
        }

        public void testIntoToSQL() {
            ArelTable table = new ArelTable("users");

            ArelInsertManager insertManager = new ArelInsertManager();
            insertManager.into(table);

            assertEquals("INSERT INTO \"users\"", insertManager.toSQL());
        }
    }

    public static class New extends Base {
        public void testNew() {
            new ArelInsertManager();
        }
    }

    public static class Select extends Base {
        public void testSelectValues() {
            ArelTable table = new ArelTable("users");

            ArelInsertManager insertManager = new ArelInsertManager();
            insertManager.into(table);

            ArelSelectManager selectManager = new ArelSelectManager();
            selectManager.project(Arel.sql("1"));
            selectManager.project(Arel.sql("\"aaron\""));

            insertManager.select(selectManager);
            insertManager.columns().add(table.get("id"));
            insertManager.columns().add(table.get("name"));

            assertEquals("INSERT INTO \"users\" (\"id\", \"name\") (SELECT 1, \"aaron\")", insertManager.toSQL());
        }
    }

    public static class Values extends Base {
        public void testValues() {
            ArelTable table = new ArelTable("users");

            List<Object> values = new ArrayList<>();
            values.add(1);

            ArelInsertManager insertManager = new ArelInsertManager();
            insertManager.into(table);
            insertManager.values(new ArelNodeValues(values));

            assertEquals("INSERT INTO \"users\" VALUES (1)", insertManager.toSQL());
        }
    }
}

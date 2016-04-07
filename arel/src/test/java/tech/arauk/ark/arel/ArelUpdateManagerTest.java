package tech.arauk.ark.arel;

import junit.framework.TestCase;
import tech.arauk.ark.arel.interfaces.ArelKeyInterface;
import tech.arauk.ark.arel.nodes.ArelNodeBindParam;
import tech.arauk.ark.arel.nodes.ArelNodeSqlLiteral;
import tech.arauk.ark.arel.nodes.binary.ArelNodeJoinSource;
import tech.arauk.ark.arel.support.FakeRecord;

import java.util.ArrayList;
import java.util.List;

public class ArelUpdateManagerTest {
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

    public static class UpdateManager extends Base {
        public void testUpdateManagerLimit() {
            ArelTable table = new ArelTable("users");

            ArelUpdateManager updateManager = new ArelUpdateManager();
            updateManager.key("id");
            updateManager.take(10);
            updateManager.table(table);
            updateManager.set(new Object[][]{{table.get("name"), null}});

            assertEquals("UPDATE \"users\" SET \"name\" = NULL WHERE 'id' IN (SELECT 'id' FROM \"users\" LIMIT 10)", updateManager.toSQL());
        }

        public void testUpdateManagerSQLLiterals() {
            ArelTable table = new ArelTable("users");

            ArelUpdateManager updateManager = new ArelUpdateManager();
            updateManager.table(table);
            updateManager.set(new Object[][]{{table.get("name"), new ArelNodeBindParam()}});

            assertEquals("UPDATE \"users\" SET \"name\" = ?", updateManager.toSQL());
        }
    }

    public static class Key extends Base {
        private ArelTable mTable;
        private ArelUpdateManager mUpdateManager;

        @Override
        protected void setUp() throws Exception {
            super.setUp();

            this.mTable = new ArelTable("users");
            this.mUpdateManager = new ArelUpdateManager();
            this.mUpdateManager.key(this.mTable.get("foo"));
        }

        public void testKeyGetter() {
            assertEquals(this.mTable.get("foo"), this.mUpdateManager.key());
        }

        public void testKeySetter() {
            assertEquals(this.mTable.get("foo"), ((ArelKeyInterface) this.mUpdateManager.ast()).key());
        }
    }

    public static class New extends Base {
        public void testNew() {
            assertNotNull(new ArelInsertManager());
        }
    }

    public static class Set extends Base {
        public void testSetMethodChain() {
            ArelTable table = new ArelTable("users");

            ArelUpdateManager updateManager = new ArelUpdateManager();

            assertEquals(updateManager, updateManager.set(new Object[][]{{table.get("id"), 1}, {table.get("name"), "hello"}}));
        }

        public void testSetWithListsList() {
            ArelTable table = new ArelTable("users");

            ArelUpdateManager updateManager = new ArelUpdateManager();
            updateManager.table(table);
            updateManager.set(new Object[][]{{table.get("id"), 1}, {table.get("name"), "hello"}});

            assertEquals("UPDATE \"users\" SET \"id\" = 1, \"name\" = 'hello'", updateManager.toSQL());
        }

        public void testSetWithNull() {
            final ArelTable table = new ArelTable("users");

            final ArelUpdateManager updateManager = new ArelUpdateManager();
            updateManager.table(table);
            updateManager.set(new Object[][]{{table.get("name"), null}});

            updateManager.set(new Object[][]{{table.get("name"), null}});

            assertEquals("UPDATE \"users\" SET \"name\" = NULL", updateManager.toSQL());
        }

        public void testSetWithString() {
            ArelTable table = new ArelTable("users");

            ArelUpdateManager updateManager = new ArelUpdateManager();
            updateManager.table(table);
            updateManager.set(new ArelNodeSqlLiteral("foo = bar"));

            assertEquals("UPDATE \"users\" SET foo = bar", updateManager.toSQL());
        }
    }

    public static class Table extends Base {
        public void testTableMethodChain() {
            ArelUpdateManager updateManager = new ArelUpdateManager();

            assertEquals(updateManager, updateManager.table(new ArelTable("users")));
        }

        public void testTableUpdateStatement() {
            ArelUpdateManager updateManager = new ArelUpdateManager();
            updateManager.table(new ArelTable("users"));

            assertEquals("UPDATE \"users\"", updateManager.toSQL());
        }

//        public void testTableUpdateStatementWithJoins() {
//            ArelTable table = new ArelTable("users");
//
//            List<Object> join = new ArrayList<>();
//            join.add(table.createJoin(new ArelTable("posts")));
//
//            ArelNodeJoinSource joinSource = new ArelNodeJoinSource(table, join);
//
//            ArelUpdateManager updateManager = new ArelUpdateManager();
//            updateManager.table(joinSource);
//
//            assertEquals("UPDATE \"users\" INNER JOIN \"posts\"", updateManager.toSQL());
//        }
    }

    public static class Where extends Base {
        public void testWhere() {
            ArelTable table = new ArelTable("users");

            ArelUpdateManager updateManager = new ArelUpdateManager();
            updateManager.table(table);
            updateManager.where(table.get("id").eq(1));

            assertEquals("UPDATE \"users\" WHERE \"users\".\"id\" = 1", updateManager.toSQL());
        }

        public void testWhereMethodChain() {
            ArelTable table = new ArelTable("users");

            ArelUpdateManager updateManager = new ArelUpdateManager();
            updateManager.table(table);

            assertEquals(updateManager, updateManager.where(table.get("id").eq(1)));
        }
    }
}

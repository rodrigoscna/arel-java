package tech.arauk.ark.arel;

import junit.framework.TestCase;
import tech.arauk.ark.arel.support.FakeRecord;

public class ArelDeleteManagerTest {
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

    public static class DeleteManager extends Base {
        public void testLimit() {
            ArelTable table = new ArelTable("users");
            ArelDeleteManager deleteManager = new ArelDeleteManager();
            deleteManager.take(10);
            deleteManager.from(table);

            assertEquals("DELETE FROM \"users\" LIMIT 10", deleteManager.toSQL());
        }
    }

    public static class From extends Base {
        public void testFrom() {
            ArelTable table = new ArelTable("users");
            ArelDeleteManager deleteManager = new ArelDeleteManager();
            deleteManager.from(table);

            assertEquals("DELETE FROM \"users\"", deleteManager.toSQL());
        }

        public void testFromMethodChain() {
            ArelTable table = new ArelTable("users");
            ArelDeleteManager deleteManager = new ArelDeleteManager();

            assertEquals(deleteManager, deleteManager.from(table));
        }
    }

    public static class New extends Base {
        public void testNew() {
            assertNotNull(new ArelDeleteManager());
        }
    }

    public static class Where extends Base {
        public void testWhere() {
            ArelTable table = new ArelTable("users");
            ArelDeleteManager deleteManager = new ArelDeleteManager();
            deleteManager.from(table);
            deleteManager.where(table.get("id").eq(10));

            assertEquals("DELETE FROM \"users\" WHERE \"users\".\"id\" = 10", deleteManager.toSQL());
        }

        public void testWhereMethodChain() {
            ArelTable table = new ArelTable("users");
            ArelDeleteManager deleteManager = new ArelDeleteManager();

            assertEquals(deleteManager, deleteManager.where(table.get("id").eq(10)));
        }
    }
}

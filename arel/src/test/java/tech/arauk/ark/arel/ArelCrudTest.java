package tech.arauk.ark.arel;

import junit.framework.TestCase;
import tech.arauk.ark.arel.attributes.ArelAttribute;

import java.util.HashMap;
import java.util.Map;

public class ArelCrudTest {
    public static abstract class Base extends TestCase {
        @Override
        protected void setUp() throws Exception {
            super.setUp();
        }

        @Override
        protected void tearDown() throws Exception {
            super.tearDown();
        }
    }

    public static class Delete extends Base {
        public void testDeleteCallOnConnection() {
            ArelTable table = new ArelTable("users");
            FakeCrudder fakeCrudder = new FakeCrudder();
            fakeCrudder.from(table);

            Object deleteManager = fakeCrudder.compileDelete();

            assertSame(ArelDeleteManager.class, deleteManager.getClass());
        }
    }

    public static class Insert extends Base {
        public void testInsertCallOnConnection() {
            ArelTable table = new ArelTable("users");
            FakeCrudder fakeCrudder = new FakeCrudder();
            fakeCrudder.from(table);

            Map<Object, Object> values = new HashMap<>();
            values.put(table.get("id"), "foo");

            Object insertManager = fakeCrudder.compileInsert(values);

            assertSame(ArelInsertManager.class, insertManager.getClass());
        }
    }

    public static class Update extends Base {
        public void testUpdateCallOnConnection() {
            ArelTable table = new ArelTable("users");
            FakeCrudder fakeCrudder = new FakeCrudder();
            fakeCrudder.from(table);

            Map<Object, Object> values = new HashMap<>();
            values.put(table.get("id"), "foo");

            Object updateManager = fakeCrudder.compileUpdate(values, new ArelAttribute(table, "id"));

            assertSame(ArelUpdateManager.class, updateManager.getClass());
        }
    }

    private static class FakeCrudder extends ArelSelectManager {
        public FakeCrudder() {
            super();
        }
    }
}

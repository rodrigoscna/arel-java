package tech.arauk.ark.arel.nodes;

import junit.framework.TestCase;
import tech.arauk.ark.arel.ArelTable;
import tech.arauk.ark.arel.support.FakeRecord;

import java.util.Objects;

public class ArelNodeCountTest {
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

    public static class As extends Base {
        public void testAs() {
            ArelTable table = new ArelTable("users");

            assertEquals("COUNT(\"users\".\"id\") AS foo", table.get("id").count().as("foo").toSQL());
        }
    }

    public static class Eq extends Base {
        public void testEq() {
            ArelTable table = new ArelTable("users");

            assertEquals("COUNT(\"users\".\"id\") = 2", table.get("id").count().eq(2).toSQL());
        }
    }

    public static class Equality extends Base {
        public void testEquality() {
            assertEquals(new ArelNodeCount("foo"), new ArelNodeCount("foo"));
        }

        public void testEqualityWithDifferentValues() {
            assertFalse(Objects.equals(new ArelNodeCount("foo"), new ArelNodeCount("foo!")));
        }
    }
}

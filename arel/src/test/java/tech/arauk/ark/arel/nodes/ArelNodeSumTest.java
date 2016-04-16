package tech.arauk.ark.arel.nodes;

import junit.framework.TestCase;
import tech.arauk.ark.arel.ArelTable;
import tech.arauk.ark.arel.support.FakeRecord;

import java.util.Objects;

public class ArelNodeSumTest {
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

    public static class Equality extends Base {
        public void testEquality() {
            assertEquals(new ArelNodeSum("foo"), new ArelNodeSum("foo"));
        }

        public void testEqualityWithDifferentValues() {
            assertFalse(Objects.equals(new ArelNodeSum("foo"), new ArelNodeSum("bar")));
        }
    }

    public static class Sum extends Base {
        public void testSumAlias() {
            ArelTable table = new ArelTable("users");

            assertEquals("SUM(\"users\".\"id\") AS foo", table.get("id").sum().as("foo").toSQL());
        }
    }
}

package tech.arauk.ark.arel.nodes;

import junit.framework.TestCase;
import tech.arauk.ark.arel.ArelNodes;
import tech.arauk.ark.arel.ArelTable;
import tech.arauk.ark.arel.support.FakeRecord;

import java.util.Objects;

public class ArelNodeGroupingTest {
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
            assertEquals(new ArelNodeGrouping("foo"), new ArelNodeGrouping("foo"));
        }

        public void testEqualityWithDifferentValues() {
            assertFalse(Objects.equals(new ArelNodeGrouping("foo"), new ArelNodeGrouping("bar")));
        }
    }

    public static class Grouping extends Base {
        public void testGrouping() {
            ArelNodeGrouping grouping = new ArelNodeGrouping(ArelNodes.buildQuoted("foo"));

            assertEquals("('foo') = 'foo'", grouping.eq("foo").toSQL());
        }
    }
}

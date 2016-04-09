package tech.arauk.ark.arel.nodes;

import junit.framework.TestCase;

import java.util.Objects;

public class ArelNodeAscendingTest {
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

    public static class Ascending extends Base {
        public void testAscending() {
            ArelNodeAscending ascending = new ArelNodeAscending("zomg");

            assertEquals("zomg", ascending.expr());
        }

        public void testAscendingDirection() {
            ArelNodeAscending ascending = new ArelNodeAscending("zomg");

            assertEquals("asc", ascending.direction());
        }

        public void testAscendingIsAscending() {
            ArelNodeAscending ascending = new ArelNodeAscending("zomg");

            assertTrue(ascending.isAscending());
        }

        public void testAscendingIsDescending() {
            ArelNodeAscending ascending = new ArelNodeAscending("zomg");

            assertFalse(ascending.isDescending());
        }

        public void testAscendingReverse() {
            ArelNodeAscending ascending = new ArelNodeAscending("zomg");
            ArelNode descending = ascending.reverse();

            assertSame(ArelNodeDescending.class, descending.getClass());
            assertEquals(ascending.expr(), ((ArelNodeDescending) descending).expr());
        }
    }

    public static class Equality extends Base {
        public void testEquality() {
            assertEquals(new ArelNodeAscending("zomg"), new ArelNodeAscending("zomg"));
        }

        public void testEqualityWithDifferentValues() {
            assertFalse(Objects.equals(new ArelNodeAscending("zomg"), new ArelNodeAscending("zomg!")));
        }
    }
}

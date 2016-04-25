package tech.arauk.ark.arel.nodes;

import junit.framework.TestCase;

import java.util.Objects;

public class ArelNodeDescendingTest {
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

    public static class Descending extends Base {
        public void testDescending() {
            ArelNodeDescending descending = new ArelNodeDescending("zomg");

            assertEquals("zomg", descending.expr());
        }

        public void testDescendingDirection() {
            ArelNodeDescending descending = new ArelNodeDescending("zomg");

            assertEquals("desc", descending.direction());
        }

        public void testDescendingIsAscending() {
            ArelNodeDescending descending = new ArelNodeDescending("zomg");

            assertFalse(descending.isAscending());
        }

        public void testDescendingIsDescending() {
            ArelNodeDescending descending = new ArelNodeDescending("zomg");

            assertTrue(descending.isDescending());
        }

        public void testDescendingReverse() {
            ArelNodeDescending descending = new ArelNodeDescending("zomg");
            ArelNode ascending = descending.reverse();

            assertSame(ArelNodeAscending.class, ascending.getClass());
            assertEquals(descending.expr(), ((ArelNodeAscending) ascending).expr());
        }
    }

    public static class Equality extends Base {
        public void testEquality() {
            assertEquals(new ArelNodeDescending("zomg"), new ArelNodeDescending("zomg"));
        }

        public void testEqualityWithDifferentValues() {
            assertFalse(Objects.equals(new ArelNodeDescending("zomg"), new ArelNodeDescending("zomg!")));
        }
    }
}

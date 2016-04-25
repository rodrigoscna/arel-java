package tech.arauk.ark.arel.nodes;

import junit.framework.TestCase;

import java.util.Objects;

public class ArelNodeNamedWindowTest {
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

    public static class Equality extends Base {
        public void testEquality() {
            ArelNodeNamedWindow window1 = new ArelNodeNamedWindow("foo");
            window1.orders(new Object[]{1, 2});
            window1.partitions(new Object[]{1});
            window1.frame(3);

            ArelNodeNamedWindow window2 = new ArelNodeNamedWindow("foo");
            window2.orders(new Object[]{1, 2});
            window2.partitions(new Object[]{1});
            window2.frame(3);

            assertEquals(window1, window2);
        }

        public void testEqualityWithDifferentValues() {
            ArelNodeNamedWindow window1 = new ArelNodeNamedWindow("foo");
            window1.orders(new Object[]{1, 2});
            window1.partitions(new Object[]{1});
            window1.frame(3);

            ArelNodeNamedWindow window2 = new ArelNodeNamedWindow("bar");
            window2.orders(new Object[]{1, 2});
            window2.partitions(new Object[]{1});
            window2.frame(3);

            assertFalse(Objects.equals(new ArelNodeWindow(), new ArelNode()));
        }
    }
}

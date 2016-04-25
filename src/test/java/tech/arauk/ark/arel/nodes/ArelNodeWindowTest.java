package tech.arauk.ark.arel.nodes;

import junit.framework.TestCase;

import java.util.Objects;

public class ArelNodeWindowTest {
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
            ArelNodeWindow window1 = new ArelNodeWindow();
            window1.orders(new Object[]{1, 2});
            window1.partitions(new Object[]{1});
            window1.frame(3);

            ArelNodeWindow window2 = new ArelNodeWindow();
            window2.orders(new Object[]{1, 2});
            window2.partitions(new Object[]{1});
            window2.frame(3);

            assertEquals(window1, window2);
        }

        public void testEqualityWithDifferentValues() {
            ArelNodeWindow window1 = new ArelNodeWindow();
            window1.orders(new Object[]{1, 2});
            window1.partitions(new Object[]{1});
            window1.frame(3);

            ArelNodeWindow window2 = new ArelNodeWindow();
            window2.orders(new Object[]{1, 2});
            window2.partitions(new Object[]{1});
            window2.frame(4);

            assertFalse(Objects.equals(new ArelNodeWindow(), new ArelNode()));
        }
    }
}

package tech.arauk.ark.arel.nodes;

import junit.framework.TestCase;

import java.util.Objects;

public class ArelNodeCurrentRowTest {
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
            assertEquals(new ArelNodeCurrentRow(), new ArelNodeCurrentRow());
        }

        public void testEqualityWithDifferentValues() {
            assertFalse(Objects.equals(new ArelNodeCurrentRow(), new ArelNode()));
        }
    }
}

package tech.arauk.ark.arel.nodes;

import junit.framework.TestCase;
import tech.arauk.ark.arel.nodes.binary.ArelNodeNotEqual;

import java.util.Objects;

public class ArelNodeBinaryTest {
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

    public static class Hash extends Base {
        public void testHashBasedOnClass() {
            ArelNodeEquality equality = new ArelNodeEquality("foo", "bar");
            ArelNodeNotEqual notEqual = new ArelNodeNotEqual("foo", "bar");

            assertFalse(Objects.equals(equality.hashCode(), notEqual.hashCode()));
        }

        public void testHashBasedOnValues() {
            ArelNodeEquality equality = new ArelNodeEquality("foo", "bar");
            ArelNodeEquality equality2 = new ArelNodeEquality("foo", "bar");
            ArelNodeEquality equality3 = new ArelNodeEquality("bar", "baz");

            assertEquals(equality.hashCode(), equality2.hashCode());
            assertFalse(Objects.equals(equality.hashCode(), equality3.hashCode()));
        }
    }
}

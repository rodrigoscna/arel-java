package tech.arauk.ark.arel.nodes;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ArelNodeAndTest {
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
            List<Object> conditions1 = new ArrayList<Object>() {{
                add("foo");
                add("bar");
            }};

            List<Object> conditions2 = new ArrayList<Object>() {{
                add("foo");
                add("bar");
            }};

            assertEquals(new ArelNodeAnd(conditions1), new ArelNodeAnd(conditions2));
        }

        public void testEqualityWithDifferentValues() {
            List<Object> conditions1 = new ArrayList<Object>() {{
                add("foo");
                add("bar");
            }};

            List<Object> conditions2 = new ArrayList<Object>() {{
                add("foo");
                add("baz");
            }};

            assertFalse(Objects.equals(new ArelNodeAnd(conditions1), new ArelNodeAnd(conditions2)));
        }
    }
}

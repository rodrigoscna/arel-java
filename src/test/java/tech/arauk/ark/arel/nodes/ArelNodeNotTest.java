package tech.arauk.ark.arel.nodes;

import junit.framework.TestCase;
import tech.arauk.ark.arel.ArelTable;
import tech.arauk.ark.arel.attributes.ArelAttribute;

import java.util.Objects;

public class ArelNodeNotTest {
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
            assertEquals(new ArelNodeNot("foo"), new ArelNodeNot("foo"));
        }

        public void testEqualityWithDifferentValues() {
            assertFalse(Objects.equals(new ArelNodeNot("foo"), new ArelNodeNot("baz")));
        }
    }

    public static class Not extends Base {
        public void testNot() {
            ArelAttribute attribute = new ArelTable("users").get("id");
            ArelNodeEquality expression = attribute.eq(10);
            ArelNode node = expression.not();

            assertSame(ArelNodeNot.class, node.getClass());
            assertEquals(expression, ((ArelNodeNot) node).expr());
        }
    }
}

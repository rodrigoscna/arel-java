package tech.arauk.ark.arel.nodes;

import junit.framework.TestCase;
import tech.arauk.ark.arel.ArelTable;
import tech.arauk.ark.arel.attributes.ArelAttribute;

import java.util.Objects;

public class ArelNodeOrTest {
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
            assertEquals(new ArelNodeOr("foo", "bar"), new ArelNodeOr("foo", "bar"));
        }

        public void testEqualityWithDifferentValues() {
            assertFalse(Objects.equals(new ArelNodeOr("foo", "bar"), new ArelNodeOr("foo", "baz")));
        }
    }

    public static class Or extends Base {
        public void testOr() {
            ArelAttribute attribute = new ArelTable("users").get("id");
            ArelNode left = attribute.eq(10);
            ArelNode right = attribute.eq(11);
            ArelNodeGrouping node = left.or(right);

            assertEquals(left, ((ArelNodeBinary) node.expr()).left());
            assertEquals(right, ((ArelNodeBinary) node.expr()).right());

            ArelNodeGrouping orOr = node.or(right);
            assertEquals(node, ((ArelNodeBinary) orOr.expr()).left());
            assertEquals(right, ((ArelNodeBinary) orOr.expr()).right());
        }
    }
}

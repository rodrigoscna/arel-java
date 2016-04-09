package tech.arauk.ark.arel.nodes;

import junit.framework.TestCase;
import tech.arauk.ark.arel.Arel;
import tech.arauk.ark.arel.ArelTable;
import tech.arauk.ark.arel.attributes.ArelAttribute;
import tech.arauk.ark.arel.nodes.binary.ArelNodeAs;

import java.util.Objects;

public class ArelNodeAsTest {
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

    public static class As extends Base {
        public void testAs() {
            ArelAttribute attribute = new ArelTable("users").get("id");
            ArelNodeAs as = attribute.as(Arel.sql("foo"));

            assertEquals(attribute, as.left());
            assertEquals("foo", as.right().toString());
        }

        public void testAsSqlLiteral() {
            ArelAttribute attribute = new ArelTable("users").get("id");
            ArelNodeAs as = attribute.as("foo");

            assertSame(ArelNodeSqlLiteral.class, as.right().getClass());
        }
    }


    public static class Equality extends Base {
        public void testEquality() {
            assertEquals(new ArelNodeAs("foo", "bar"), new ArelNodeAs("foo", "bar"));
        }

        public void testEqualityWithDifferentValues() {
            assertFalse(Objects.equals(new ArelNodeAs("foo", "bar"), new ArelNodeAs("foo", "baz")));
        }
    }
}

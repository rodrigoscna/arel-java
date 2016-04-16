package tech.arauk.ark.arel.nodes;

import junit.framework.TestCase;

import java.util.Objects;

public class ArelNodeNamedFunctionTest {
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
            assertEquals(new ArelNodeNamedFunction("omg", "zomg", "wth"), new ArelNodeNamedFunction("omg", "zomg", "wth"));
        }

        public void testEqualityWithDifferentValues() {
            assertFalse(Objects.equals(new ArelNodeNamedFunction("omg", "zomg", "wth"), new ArelNodeNamedFunction("zomg", "zomg", "wth")));
        }
    }

    public static class NamedFunction extends Base {
        public void testNamedFunction() {
            ArelNodeNamedFunction namedFunction = new ArelNodeNamedFunction("omg", "zomg");

            assertEquals("omg", namedFunction.name());
            assertEquals("zomg", namedFunction.expressions());
        }

        public void testNamedFunctionAlias() {
            ArelNodeNamedFunction namedFunction = new ArelNodeNamedFunction("omg", "zomg");
            namedFunction = (ArelNodeNamedFunction) namedFunction.as("wth");

            assertEquals("omg", namedFunction.name());
            assertEquals("zomg", namedFunction.expressions());
            assertSame(ArelNodeSqlLiteral.class, namedFunction.alias().getClass());
            assertEquals("wth", namedFunction.alias().toString());
        }

        public void testNamedFunctionWithAlias() {
            ArelNodeNamedFunction namedFunction = new ArelNodeNamedFunction("omg", "zomg", "wth");

            assertEquals("omg", namedFunction.name());
            assertEquals("zomg", namedFunction.expressions());
            assertSame(ArelNodeSqlLiteral.class, namedFunction.alias().getClass());
            assertEquals("wth", namedFunction.alias().toString());
        }
    }
}

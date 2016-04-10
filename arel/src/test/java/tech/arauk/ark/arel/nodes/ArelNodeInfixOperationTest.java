package tech.arauk.ark.arel.nodes;

import junit.framework.TestCase;

import java.util.Objects;

public class ArelNodeInfixOperationTest {
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
            assertEquals(new ArelNodeInfixOperation("+", 1, 2), new ArelNodeInfixOperation("+", 1, 2));
        }

        public void testEqualityWithDifferentValues() {
            assertFalse(Objects.equals(new ArelNodeInfixOperation("+", 1, 2), new ArelNodeInfixOperation("+", 1, 3)));
        }
    }

    public static class InfixOperation extends Base {
        public void testInfixOperation() {
            ArelNodeInfixOperation infixOperation = new ArelNodeInfixOperation("+", 1, 2);

            assertEquals("+", infixOperation.operator());
            assertEquals("1", infixOperation.left().toString());
            assertEquals("2", infixOperation.right().toString());
        }

        public void testInfixOperationAlias() {
            ArelNodeInfixOperation infixOperation = new ArelNodeInfixOperation("+", 1, 2);

            ArelNode aliaz = infixOperation.as("zomg");

            assertSame(ArelNodeAs.class, aliaz.getClass());
            assertEquals(infixOperation, ((ArelNodeAs) aliaz).left());
            assertEquals("zomg", ((ArelNodeAs) aliaz).right().toString());
        }

        public void testInfixOperationOrdering() {
            ArelNodeInfixOperation infixOperation = new ArelNodeInfixOperation("+", 1, 2);

            ArelNode ordering = infixOperation.desc();

            assertSame(ArelNodeDescending.class, ordering.getClass());
            assertEquals(infixOperation, ((ArelNodeDescending) ordering).expr());
            assertTrue(((ArelNodeDescending) ordering).isDescending());
        }
    }
}

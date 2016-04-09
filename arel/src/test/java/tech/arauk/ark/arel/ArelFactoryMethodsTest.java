package tech.arauk.ark.arel;

import junit.framework.TestCase;
import tech.arauk.ark.arel.nodes.*;
import tech.arauk.ark.arel.nodes.ArelNodeOn;

public class ArelFactoryMethodsTest {
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

    public static class Factory extends Base {
        public void testFactoryForFalse() {
            Object aFalse = ArelFactoryMethods.createFalse();

            assertSame(ArelNodeFalse.class, aFalse.getClass());
        }

        public void testFactoryForJoin() {
            Object join = ArelFactoryMethods.createJoin("one", "two");

            assertSame(ArelNodeInnerJoin.class, join.getClass());
            assertEquals("two", ((ArelNodeInnerJoin) join).right());
        }

        public void testFactoryForOn() {
            Object on = ArelFactoryMethods.createOn("one");

            assertSame(ArelNodeOn.class, on.getClass());
            assertEquals("one", ((ArelNodeOn) on).expr());
        }

        public void testFactoryForTrue() {
            Object aTrue = ArelFactoryMethods.createTrue();

            assertSame(ArelNodeTrue.class, aTrue.getClass());
        }

        public void testFactoryLower() {
            Object lower = ArelFactoryMethods.lower("one");

            assertSame(ArelNodeNamedFunction.class, lower.getClass());
            assertEquals("LOWER", ((ArelNodeNamedFunction) lower).name);
            assertSame(ArelNodeQuoted.class, ((Object[]) ((ArelNodeNamedFunction) lower).expressions)[0].getClass());
            assertEquals("one", ((ArelNodeQuoted) ((Object[]) ((ArelNodeNamedFunction) lower).expressions)[0]).expr());
        }
    }
}

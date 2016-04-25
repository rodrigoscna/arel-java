package tech.arauk.ark.arel.nodes;

import junit.framework.TestCase;

public class ArelNodeTest {
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

    public static class FactoryMethods extends Base {
        public void testFactoryMethods() {
            assertNotNull(new ArelNode().createJoin(null));
        }
    }
}

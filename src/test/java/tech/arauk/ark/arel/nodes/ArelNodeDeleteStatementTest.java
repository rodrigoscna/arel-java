package tech.arauk.ark.arel.nodes;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ArelNodeDeleteStatementTest {
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
            List<Object> wheres1 = new ArrayList<>();
            wheres1.add("a");
            wheres1.add("b");
            wheres1.add("c");

            ArelNodeDeleteStatement deleteStatement1 = new ArelNodeDeleteStatement();
            deleteStatement1.wheres(wheres1);

            List<Object> wheres2 = new ArrayList<>();
            wheres2.add("a");
            wheres2.add("b");
            wheres2.add("c");

            ArelNodeDeleteStatement deleteStatement2 = new ArelNodeDeleteStatement();
            deleteStatement2.wheres(wheres2);

            assertEquals(deleteStatement1, deleteStatement2);
        }

        public void testEqualityWithDifferentValues() {
            List<Object> wheres1 = new ArrayList<>();
            wheres1.add("a");
            wheres1.add("b");
            wheres1.add("c");

            ArelNodeDeleteStatement deleteStatement1 = new ArelNodeDeleteStatement();
            deleteStatement1.wheres(wheres1);

            List<Object> wheres2 = new ArrayList<>();
            wheres2.add("1");
            wheres2.add("2");
            wheres2.add("3");

            ArelNodeDeleteStatement deleteStatement2 = new ArelNodeDeleteStatement();
            deleteStatement2.wheres(wheres2);

            assertFalse(Objects.equals(deleteStatement1, deleteStatement2));
        }
    }
}

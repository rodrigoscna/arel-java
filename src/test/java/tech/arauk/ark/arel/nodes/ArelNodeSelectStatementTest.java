package tech.arauk.ark.arel.nodes;

import junit.framework.TestCase;
import tech.arauk.ark.arel.ArelTable;
import tech.arauk.ark.arel.support.FakeRecord;
import tech.arauk.ark.arel.support.TestUtils;

import java.util.Objects;

public class ArelNodeSelectStatementTest {
    public static abstract class Base extends TestCase {
        static {
            ArelTable.engine = new FakeRecord.Base();
        }

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
            ArelNodeSelectStatement selectStatement1 = new ArelNodeSelectStatement();
            selectStatement1.offset(1);
            selectStatement1.limit(2);
            selectStatement1.lock(false);
            selectStatement1.orders(TestUtils.objectsToList("x", "y", "z"));
            selectStatement1.with("zomg");

            ArelNodeSelectStatement selectStatement2 = new ArelNodeSelectStatement();
            selectStatement2.offset(1);
            selectStatement2.limit(2);
            selectStatement2.lock(false);
            selectStatement2.orders(TestUtils.objectsToList("x", "y", "z"));
            selectStatement2.with("zomg");

            assertEquals(selectStatement1, selectStatement2);
        }

        public void testEqualityWithDifferentValues() {
            ArelNodeSelectStatement selectStatement1 = new ArelNodeSelectStatement();
            selectStatement1.offset(1);
            selectStatement1.limit(2);
            selectStatement1.lock(false);
            selectStatement1.orders(TestUtils.objectsToList("x", "y", "z"));
            selectStatement1.with("zomg");

            ArelNodeSelectStatement selectStatement2 = new ArelNodeSelectStatement();
            selectStatement2.offset(1);
            selectStatement2.limit(2);
            selectStatement2.lock(false);
            selectStatement2.orders(TestUtils.objectsToList("x", "y", "z"));
            selectStatement2.with("wth");

            assertFalse(Objects.equals(selectStatement1, selectStatement2));
        }
    }
}

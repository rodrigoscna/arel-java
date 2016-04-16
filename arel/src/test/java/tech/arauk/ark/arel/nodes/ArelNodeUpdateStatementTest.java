package tech.arauk.ark.arel.nodes;

import junit.framework.TestCase;
import tech.arauk.ark.arel.ArelTable;
import tech.arauk.ark.arel.support.FakeRecord;
import tech.arauk.ark.arel.support.TestUtils;

import java.util.Objects;

public class ArelNodeUpdateStatementTest {
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
            ArelNodeUpdateStatement updateStatement1 = new ArelNodeUpdateStatement();
            updateStatement1.relation("zomg");
            updateStatement1.wheres(2);
            updateStatement1.values(false);
            updateStatement1.orders(TestUtils.objectsToList("x", "y", "z"));
            updateStatement1.limit(42);
            updateStatement1.key("zomg");

            ArelNodeUpdateStatement updateStatement2 = new ArelNodeUpdateStatement();
            updateStatement2.relation("zomg");
            updateStatement2.wheres(2);
            updateStatement2.values(false);
            updateStatement2.orders(TestUtils.objectsToList("x", "y", "z"));
            updateStatement2.limit(42);
            updateStatement2.key("zomg");

            assertEquals(updateStatement1, updateStatement2);
        }

        public void testEqualityWithDifferentValues() {
            ArelNodeUpdateStatement updateStatement1 = new ArelNodeUpdateStatement();
            updateStatement1.relation("zomg");
            updateStatement1.wheres(2);
            updateStatement1.values(false);
            updateStatement1.orders(TestUtils.objectsToList("x", "y", "z"));
            updateStatement1.limit(42);
            updateStatement1.key("zomg");

            ArelNodeUpdateStatement updateStatement2 = new ArelNodeUpdateStatement();
            updateStatement2.relation("zomg");
            updateStatement2.wheres(2);
            updateStatement2.values(false);
            updateStatement2.orders(TestUtils.objectsToList("x", "y", "z"));
            updateStatement2.limit(42);
            updateStatement2.key("wth");

            assertFalse(Objects.equals(updateStatement1, updateStatement2));
        }
    }
}

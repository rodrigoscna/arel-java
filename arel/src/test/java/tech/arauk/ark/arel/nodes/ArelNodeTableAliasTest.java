package tech.arauk.ark.arel.nodes;

import junit.framework.TestCase;
import tech.arauk.ark.arel.ArelTable;
import tech.arauk.ark.arel.support.FakeRecord;

import java.util.Objects;

public class ArelNodeTableAliasTest {
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
            ArelTable relation1 = new ArelTable("users");
            ArelNode node1 = new ArelNodeTableAlias(relation1, "foo");

            ArelTable relation2 = new ArelTable("users");
            ArelNode node2 = new ArelNodeTableAlias(relation2, "foo");

            assertEquals(node1, node2);
        }

        public void testEqualityWithDifferentValues() {
            ArelTable relation1 = new ArelTable("users");
            ArelNode node1 = new ArelNodeTableAlias(relation1, "foo");

            ArelTable relation2 = new ArelTable("users");
            ArelNode node2 = new ArelNodeTableAlias(relation2, "bar");

            assertFalse(Objects.equals(node1, node2));
        }
    }
}

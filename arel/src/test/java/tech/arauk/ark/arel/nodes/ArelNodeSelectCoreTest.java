package tech.arauk.ark.arel.nodes;

import junit.framework.TestCase;
import tech.arauk.ark.arel.ArelTable;
import tech.arauk.ark.arel.collectors.ArelCollector;
import tech.arauk.ark.arel.support.FakeRecord;
import tech.arauk.ark.arel.support.TestUtils;
import tech.arauk.ark.arel.visitors.ArelVisitor;
import tech.arauk.ark.arel.visitors.ArelVisitorToSql;

import java.util.Objects;

public class ArelNodeSelectCoreTest {
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
            ArelNodeSelectCore selectCore1 = new ArelNodeSelectCore();
            selectCore1.froms(TestUtils.objectsToList("a", "b", "c"));
            selectCore1.projections(TestUtils.objectsToList("d", "e", "f"));
            selectCore1.wheres(TestUtils.objectsToList("g", "h", "i"));
            selectCore1.groups(TestUtils.objectsToList("j", "k", "l"));
            selectCore1.windows(TestUtils.objectsToList("m", "n", "o"));
            selectCore1.havings(TestUtils.objectsToList("p", "q", "r"));

            ArelNodeSelectCore selectCore2 = new ArelNodeSelectCore();
            selectCore2.froms(TestUtils.objectsToList("a", "b", "c"));
            selectCore2.projections(TestUtils.objectsToList("d", "e", "f"));
            selectCore2.wheres(TestUtils.objectsToList("g", "h", "i"));
            selectCore2.groups(TestUtils.objectsToList("j", "k", "l"));
            selectCore2.windows(TestUtils.objectsToList("m", "n", "o"));
            selectCore2.havings(TestUtils.objectsToList("p", "q", "r"));

            assertEquals(selectCore1, selectCore2);
        }

        public void testEqualityWithDifferentValues() {
            ArelNodeSelectCore selectCore1 = new ArelNodeSelectCore();
            selectCore1.froms(TestUtils.objectsToList("a", "b", "c"));
            selectCore1.projections(TestUtils.objectsToList("d", "e", "f"));
            selectCore1.wheres(TestUtils.objectsToList("g", "h", "i"));
            selectCore1.groups(TestUtils.objectsToList("j", "k", "l"));
            selectCore1.windows(TestUtils.objectsToList("m", "n", "o"));
            selectCore1.havings(TestUtils.objectsToList("p", "q", "r"));

            ArelNodeSelectCore selectCore2 = new ArelNodeSelectCore();
            selectCore2.froms(TestUtils.objectsToList("a", "b", "c"));
            selectCore2.projections(TestUtils.objectsToList("d", "e", "f"));
            selectCore2.wheres(TestUtils.objectsToList("g", "h", "i"));
            selectCore2.groups(TestUtils.objectsToList("j", "k", "l"));
            selectCore2.windows(TestUtils.objectsToList("m", "n", "o"));
            selectCore2.havings(TestUtils.objectsToList("l", "o", "l"));

            assertFalse(Objects.equals(selectCore1, selectCore2));
        }
    }

    public static class SelectCore extends Base {
        public void testSelectCoreSetQuantifier() {
            ArelNodeSelectCore core = new ArelNodeSelectCore();
            core.setQuantifier(new ArelNodeDistinct());
            ArelVisitor visitor = new ArelVisitorToSql(ArelTable.engine.connection);

            assertEquals("SELECT DISTINCT", visitor.accept(core, new ArelCollector()).value());
        }
    }
}

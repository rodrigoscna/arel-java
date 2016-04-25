package tech.arauk.ark.arel.nodes;

import junit.framework.TestCase;
import tech.arauk.ark.arel.Arel;
import tech.arauk.ark.arel.ArelTable;
import tech.arauk.ark.arel.collectors.ArelCollector;
import tech.arauk.ark.arel.support.FakeRecord;
import tech.arauk.ark.arel.visitors.ArelVisitor;
import tech.arauk.ark.arel.visitors.ArelVisitorMySQL;
import tech.arauk.ark.arel.visitors.ArelVisitorToSql;

import java.util.Objects;

public class ArelNodeBinTest {
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

    public static class Bin extends Base {
        public void testBin() {
            ArelNode bin = new ArelNodeBin("zomg");

            assertSame(ArelNodeBin.class, bin.getClass());
        }

        public void testBinToSql() {
            ArelVisitor visitor = new ArelVisitorToSql(ArelTable.engine.connection);
            ArelNode node = new ArelNodeBin(Arel.sql("zomg"));

            assertEquals("zomg", visitor.accept(node, new ArelCollector()).value());
        }

        public void testBinToSqlWithMySQL() {
            ArelVisitor visitor = new ArelVisitorMySQL(ArelTable.engine.connection);
            ArelNode node = new ArelNodeBin(Arel.sql("zomg"));

            assertEquals("BINARY zomg", visitor.accept(node, new ArelCollector()).value());
        }
    }

    public static class Equality extends Base {
        public void testEquality() {
            assertEquals(new ArelNodeBin("zomg"), new ArelNodeBin("zomg"));
        }

        public void testEqualityWithDifferentValues() {
            assertFalse(Objects.equals(new ArelNodeBin("zomg"), new ArelNodeBin("zomg!")));
        }
    }
}

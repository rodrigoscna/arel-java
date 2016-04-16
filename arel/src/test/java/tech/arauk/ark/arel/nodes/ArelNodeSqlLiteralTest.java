package tech.arauk.ark.arel.nodes;

import junit.framework.TestCase;
import tech.arauk.ark.arel.Arel;
import tech.arauk.ark.arel.ArelTable;
import tech.arauk.ark.arel.collectors.ArelCollector;
import tech.arauk.ark.arel.support.FakeRecord;
import tech.arauk.ark.arel.visitors.ArelVisitor;
import tech.arauk.ark.arel.visitors.ArelVisitorToSql;

import java.util.Objects;

public class ArelNodeSqlLiteralTest {
    public static abstract class Base extends TestCase {
        static {
            ArelTable.engine = new FakeRecord.Base();
        }

        private ArelVisitor visitor;

        @Override
        protected void setUp() throws Exception {
            super.setUp();

            this.visitor = new ArelVisitorToSql(ArelTable.engine.connection);
        }

        @Override
        protected void tearDown() throws Exception {
            super.tearDown();
        }

        String compile(Object node) {
            return this.visitor.accept(node, new ArelCollector()).value();
        }
    }

    public static class Count extends Base {
        public void testCount() {
            Object node = new ArelNodeSqlLiteral("*").count();

            assertEquals("COUNT(*)", compile(node));
        }

        public void testCountWithDistinct() {
            Object node = new ArelNodeSqlLiteral("*").count(true);

            assertEquals("COUNT(DISTINCT *)", compile(node));
        }
    }

    public static class Equality extends Base {
        public void testEquality() {
            assertEquals(new ArelNodeSqlLiteral("foo"), new ArelNodeSqlLiteral("foo"));
        }

        public void testEqualityNode() {
            Object node = new ArelNodeSqlLiteral("foo").eq(1);

            assertEquals("foo = 1", compile(node));
        }

        public void testEqualityWithDifferentValues() {
            assertFalse(Objects.equals(new ArelNodeSqlLiteral("foo"), new ArelNodeSqlLiteral("bar")));
        }
    }

    public static class GroupedWithAnd extends Base {
        public void testGroupedWithAnd() {
            Object node = new ArelNodeSqlLiteral("foo").eqAll(new Object[]{1, 2});

            assertEquals("(foo = 1 AND foo = 2)", compile(node));
        }
    }

    public static class GroupedWithOr extends Base {
        public void testGroupedWithOr() {
            Object node = new ArelNodeSqlLiteral("foo").eqAny(new Object[]{1, 2});

            assertEquals("(foo = 1 OR foo = 2)", compile(node));
        }
    }

    public static class Sql extends Base {
        public void testSql() {
            Object sql = Arel.sql("foo");

            assertSame(ArelNodeSqlLiteral.class, sql.getClass());
        }
    }
}

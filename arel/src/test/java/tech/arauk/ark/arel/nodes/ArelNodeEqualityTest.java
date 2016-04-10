package tech.arauk.ark.arel.nodes;

import junit.framework.TestCase;
import tech.arauk.ark.arel.ArelTable;
import tech.arauk.ark.arel.attributes.ArelAttribute;
import tech.arauk.ark.arel.support.FakeRecord;
import tech.arauk.ark.arel.visitors.ArelVisitor;
import tech.arauk.ark.arel.visitors.ArelVisitorMySQL;

import java.util.Objects;

public class ArelNodeEqualityTest {
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

    public static class And extends Base {
        public void testAnd() {
            ArelAttribute attribute = new ArelTable("users").get("id");
            ArelNode left = attribute.eq(10);
            ArelNode right = attribute.eq(11);

            ArelNodeAnd node = left.and(right);

            assertEquals(left, node.left());
            assertEquals(right, node.right());
        }
    }

    public static class Equality extends Base {
        public void testEquality() {
            assertEquals(new ArelNodeEquality("foo", "bar"), new ArelNodeEquality("foo", "bar"));
        }

        public void testEqualityWithDifferentValues() {
            assertFalse(Objects.equals(new ArelNodeEquality("foo", "bar"), new ArelNodeEquality("foo", "baz")));
        }
    }

    public static class Operator extends Base {
        public void testOperatorEqual() {
            ArelAttribute attribute = new ArelTable("users").get("id");
            ArelNodeEquality node = attribute.eq(10);

            assertEquals("==", node.operator());
        }
    }

    public static class Operands extends Base {
        public void testOperant1() {
            ArelAttribute attribute = new ArelTable("users").get("id");
            ArelNodeEquality node = attribute.eq(10);

            assertEquals(node.left(), node.operant1());
        }

        public void testOperand2() {
            ArelAttribute attribute = new ArelTable("users").get("id");
            ArelNodeEquality node = attribute.eq(10);

            assertEquals(node.right(), node.operant2());
        }
    }

    public static class Or extends Base {
        public void testOr() {
            ArelAttribute attribute = new ArelTable("users").get("id");
            ArelNode left = attribute.eq(10);
            ArelNode right = attribute.eq(11);

            ArelNodeGrouping node = left.or(right);

            assertEquals(left, ((ArelNodeOr) node.expr()).left());
            assertEquals(right, ((ArelNodeOr) node.expr()).right());
        }
    }

    public static class ToSQL extends Base {
        public void testToSQLWithEngine() {
            QuoteCountConnection connection = new QuoteCountConnection();

            ArelVisitor engine = new FakeRecord.Base();
            engine.connection = connection;

            connection.quoteCount = 0;
            connection.visitor = new ArelVisitorMySQL(engine.connection);

            ArelAttribute attribute = new ArelTable("users").get("id");
            ArelNodeEquality node = attribute.eq(10);
            node.toSQL(engine);

            assertEquals(3, ((QuoteCountConnection) engine.connection).quoteCount);
        }

        private static class QuoteCountConnection extends FakeRecord.Connection {
            int quoteCount;

            @Override
            public Object quote(Object value) {
                quoteCount++;
                return super.quote(value);
            }

            @Override
            public Object quote(Object value, Object column) {
                quoteCount++;
                return super.quote(value, column);
            }

            @Override
            public String quoteColumnName(String columnName) {
                quoteCount++;
                return super.quoteColumnName(columnName);
            }

            @Override
            public String quoteTableName(String tableName) {
                quoteCount++;
                return super.quoteTableName(tableName);
            }
        }
    }
}

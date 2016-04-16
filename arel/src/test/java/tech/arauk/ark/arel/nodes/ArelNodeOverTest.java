package tech.arauk.ark.arel.nodes;

import junit.framework.TestCase;
import tech.arauk.ark.arel.Arel;
import tech.arauk.ark.arel.ArelTable;
import tech.arauk.ark.arel.support.FakeRecord;

import java.util.Objects;

public class ArelNodeOverTest {
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
            assertEquals(new ArelNodeOver("foo", "bar"), new ArelNodeOver("foo", "bar"));
        }

        public void testEqualityWithDifferentValues() {
            assertFalse(Objects.equals(new ArelNodeOver("foo", "bar"), new ArelNodeOver("foo", "baz")));
        }
    }

    public static class Over extends Base {
        public void testOverAlias() {
            ArelTable table = new ArelTable("users");

            assertEquals("COUNT(\"users\".\"id\") OVER () AS foo", table.get("id").count().over().as("foo").toSQL());
        }

        public void testOverWithExpression() {
            ArelTable table = new ArelTable("users");
            ArelNodeWindow window = new ArelNodeWindow().order(table.get("foo"));

            assertEquals("COUNT(\"users\".\"id\") OVER (ORDER BY \"users\".\"foo\")", table.get("id").count().over(window).toSQL());
        }

        public void testOverWithLiteral() {
            ArelTable table = new ArelTable("users");

            assertEquals("COUNT(\"users\".\"id\") OVER \"foo\"", table.get("id").count().over("foo").toSQL());
        }

        public void testOverWithNoExpression() {
            ArelTable table = new ArelTable("users");

            assertEquals("COUNT(\"users\".\"id\") OVER ()", table.get("id").count().over().toSQL());
        }

        public void testOverWithSQLLiteral() {
            ArelTable table = new ArelTable("users");

            assertEquals("COUNT(\"users\".\"id\") OVER foo", table.get("id").count().over(Arel.sql("foo")).toSQL());
        }
    }
}

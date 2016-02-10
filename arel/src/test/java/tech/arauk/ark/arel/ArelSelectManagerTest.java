package tech.arauk.ark.arel;

import junit.framework.TestCase;
import tech.arauk.ark.arel.nodes.*;
import tech.arauk.ark.arel.nodes.unary.ArelNodeOffset;
import tech.arauk.ark.arel.support.FakeRecord;

import java.util.ArrayList;
import java.util.List;

public class ArelSelectManagerTest {
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

    public static class SelectManager extends Base {
        public void testJoinSources() {
            ArelSelectManager selectManager = new ArelSelectManager();
            selectManager.joinSources().add(new ArelNodeStringJoin(ArelNodes.buildQuoted("foo")));

            assertEquals("SELECT FROM 'foo'", selectManager.toSQL());
        }

        public void testManagerStoresBindValues() {
            ArelSelectManager selectManager = new ArelSelectManager();

            assertEquals(new ArrayList<>(), selectManager.bindValues);

            selectManager.bindValues.add(1);
            List<Object> expected = new ArrayList<>();
            expected.add(1);

            assertEquals(expected, selectManager.bindValues);
        }
    }

    public static class Project extends Base {
        public void testProjectAssignment() {
            ArelTable table = new ArelTable("users");
            ArelSelectManager selectManager = new ArelSelectManager();
            selectManager.project("id");
            selectManager.from(table);

            assertEquals("SELECT id FROM \"users\"", selectManager.toSQL());
        }
    }

    public static class Order extends Base {
        public void testOrderAssignment() {
            ArelTable table = new ArelTable("users");
            ArelSelectManager selectManager = new ArelSelectManager();
            selectManager.project(new ArelNodeSqlLiteral("*"));
            selectManager.from(table);
            selectManager.order("foo");

            assertEquals("SELECT * FROM \"users\" ORDER BY foo", selectManager.toSQL());
        }
    }

    public static class Group extends Base {
        public void testGroupAssignment() {
            ArelTable table = new ArelTable("users");
            ArelSelectManager selectManager = new ArelSelectManager();
            selectManager.from(table);
            selectManager.group("foo");

            assertEquals("SELECT FROM \"users\" GROUP BY foo", selectManager.toSQL());
        }
    }

    public static class As extends Base {
        public void testAsByGroupingTheAst() {
            ArelSelectManager selectManager = new ArelSelectManager();
            ArelNodeTableAlias tableAlias = selectManager.as(Arel.sql("foo"));

            assertSame(ArelNodeGrouping.class, tableAlias.left.getClass());
            assertEquals(selectManager.ast, ((ArelNodeGrouping) tableAlias.left).expr);
            assertEquals(Arel.sql("foo"), tableAlias.right);
        }

        public void testAsConversionToSQLLiteral() {
            ArelSelectManager selectManager = new ArelSelectManager();
            ArelNodeTableAlias tableAlias = selectManager.as("foo");

            assertSame(ArelNodeSqlLiteral.class, tableAlias.right.getClass());
        }

        public void testAsWithSubselect() {
            ArelSelectManager selectManager;

            selectManager = new ArelSelectManager();
            selectManager.project(Arel.star());
            selectManager.from(Arel.sql("zomg"));
            ArelNodeTableAlias tableAlias = selectManager.as(Arel.sql("foo"));

            selectManager = new ArelSelectManager();
            selectManager.project(Arel.sql("name"));
            selectManager.from(tableAlias);

            assertEquals("SELECT name FROM (SELECT * FROM zomg) foo", selectManager.toSQL());
        }
    }

    public static class From extends Base {
        public void testFromWithStringEqualToTableName() {
            ArelTable table = new ArelTable("users");
            ArelSelectManager selectManager = new ArelSelectManager();
            selectManager.from(table);
            selectManager.from("users");
            selectManager.project(table.get("id"));

            assertEquals("SELECT \"users\".\"id\" FROM users", selectManager.toSQL());
        }

        public void testFromForAnyAst() {
            ArelTable table = new ArelTable("users");

            ArelSelectManager selectManager1 = new ArelSelectManager();

            ArelSelectManager selectManager2 = new ArelSelectManager();
            selectManager2.project(Arel.sql("*"));
            selectManager2.from(table);

            selectManager1.project(Arel.sql("lol"));
            ArelNodeTableAlias tableAlias = selectManager2.as(Arel.sql("omg"));
            selectManager1.from(tableAlias);

            assertEquals("SELECT lol FROM (SELECT * FROM \"users\") omg", selectManager1.toSQL());
        }
    }

    public static class Having extends Base {
        public void testHavingConversionToSQLLiteral() {
            ArelTable table = new ArelTable("users");

            ArelSelectManager selectManager = table.from();
            selectManager.having(Arel.sql("foo"));

            assertEquals("SELECT FROM \"users\" HAVING foo", selectManager.toSQL());
        }

        public void testHavingToReceiveMultipleItems() {
            ArelTable table = new ArelTable("users");

            ArelSelectManager selectManager = table.from();
            selectManager.having(Arel.sql("foo"));
            selectManager.having(Arel.sql("bar"));

            assertEquals("SELECT FROM \"users\" HAVING foo AND bar", selectManager.toSQL());
        }

        public void testHavingToReceiveAnyNode() {
            ArelTable table = new ArelTable("users");

            List<Object> andNodes = new ArrayList<>();
            andNodes.add(Arel.sql("foo"));
            andNodes.add(Arel.sql("bar"));

            ArelSelectManager selectManager = table.from();
            selectManager.having(new ArelNodeAnd(andNodes));

            assertEquals("SELECT FROM \"users\" HAVING foo AND bar", selectManager.toSQL());
        }
    }

    public static class On extends Base {
        public void testOnConversionToSQLLiteral() {
            ArelTable table = new ArelTable("users");

            ArelRelation right = table.alias();

            ArelSelectManager selectManager = table.from();
            selectManager.join(right).on("omg");

            assertEquals("SELECT FROM \"users\" INNER JOIN \"users\" \"users_2\" ON omg", selectManager.toSQL());
        }

        public void testOnConversionToSQLLiteralWithMultipleItems() {
            ArelTable table = new ArelTable("users");

            ArelRelation right = table.alias();

            ArelSelectManager selectManager = table.from();
            selectManager.join(right).on("omg", "123");

            assertEquals("SELECT FROM \"users\" INNER JOIN \"users\" \"users_2\" ON omg AND 123", selectManager.toSQL());
        }
    }

    public static class Initialize extends Base {
        public void testInitializeWithAlias() {
            ArelTable table = new ArelTable("users", "foo");
            ArelSelectManager selectManager = table.from();
            selectManager.skip(10);

            assertEquals("SELECT FROM \"users\" \"foo\" OFFSET 10", selectManager.toSQL());
        }
    }

    public static class Skip extends Base {
        public void testSkip() {
            ArelTable table = new ArelTable("users");
            ArelSelectManager selectManager = table.from();
            selectManager.skip(10);

            assertEquals("SELECT FROM \"users\" OFFSET 10", selectManager.toSQL());
        }

        public void testSkipMethodChain() {
            ArelTable table = new ArelTable("users");
            ArelSelectManager selectManager = table.from();

            assertEquals("SELECT FROM \"users\" OFFSET 10", selectManager.skip(10).toSQL());
        }
    }

    public static class Offset extends Base {
        public void testOffset() {
            ArelTable table = new ArelTable("users");
            ArelSelectManager selectManager = table.from();
            selectManager.offset(10);

            assertEquals("SELECT FROM \"users\" OFFSET 10", selectManager.toSQL());
        }

        public void testOffsetRemoval() {
            ArelTable table = new ArelTable("users");
            ArelSelectManager selectManager = table.from();
            selectManager.offset(10);

            assertEquals("SELECT FROM \"users\" OFFSET 10", selectManager.toSQL());

            selectManager.offset(null);

            assertEquals("SELECT FROM \"users\"", selectManager.toSQL());
        }

        public void testOffsetAccessor() {
            ArelTable table = new ArelTable("users");
            ArelSelectManager selectManager = table.from();
            selectManager.offset(10);

            assertEquals(new ArelNodeOffset(10), selectManager.offset());
        }
    }

    public static class Exists extends Base {
        public void testExists() {
            ArelTable table = new ArelTable("users");

            ArelSelectManager selectManager = new ArelSelectManager(table);
            selectManager.project(new ArelNodeSqlLiteral("*"));

            ArelSelectManager selectManager2 = new ArelSelectManager(selectManager.engine);
            selectManager2.project(selectManager.exists());

            assertEquals(String.format("SELECT EXISTS (%s)", selectManager.toSQL()), selectManager2.toSQL());
        }

        public void testExistsWithAlias() {
            ArelTable table = new ArelTable("users");

            ArelSelectManager selectManager = new ArelSelectManager(table);
            selectManager.project(new ArelNodeSqlLiteral("*"));

            ArelSelectManager selectManager2 = new ArelSelectManager(selectManager.engine);
            selectManager2.project(selectManager.exists().as("foo"));

            assertEquals(String.format("SELECT EXISTS (%s) AS foo", selectManager.toSQL()), selectManager2.toSQL());
        }
    }

    public static class Union extends Base {
        private ArelSelectManager mSelectManager1;
        private ArelSelectManager mSelectManager2;

        @Override
        protected void setUp() throws Exception {
            super.setUp();

            ArelTable table = new ArelTable("users");

            mSelectManager1 = new ArelSelectManager(table);
            mSelectManager1.project(Arel.star());
            mSelectManager1.where(table.get("age").lt(18));

            mSelectManager2 = new ArelSelectManager(table);
            mSelectManager2.project(Arel.star());
            mSelectManager2.where(table.get("age").gt(99));
        }

        public void testUnionWithTwoManagers() {
            ArelNode node = mSelectManager1.union(mSelectManager2);

            assertEquals("(SELECT * FROM \"users\" WHERE \"users\".\"age\" < 18 UNION SELECT * FROM \"users\" WHERE \"users\".\"age\" > 99)", node.toSQL());
        }

        public void testUnionAllWithTwoManagers() {
            ArelNode node = mSelectManager1.union(mSelectManager2, "all");

            assertEquals("(SELECT * FROM \"users\" WHERE \"users\".\"age\" < 18 UNION ALL SELECT * FROM \"users\" WHERE \"users\".\"age\" > 99)", node.toSQL());
        }
    }

    public static class Intersect extends Base {
        private ArelSelectManager mSelectManager1;
        private ArelSelectManager mSelectManager2;

        @Override
        protected void setUp() throws Exception {
            super.setUp();

            ArelTable table = new ArelTable("users");

            mSelectManager1 = new ArelSelectManager(table);
            mSelectManager1.project(Arel.star());
            mSelectManager1.where(table.get("age").gt(18));

            mSelectManager2 = new ArelSelectManager(table);
            mSelectManager2.project(Arel.star());
            mSelectManager2.where(table.get("age").lt(99));
        }

        public void testIntersectWithTwoManagers() {
            ArelNode node = mSelectManager1.intersect(mSelectManager2);

            assertEquals("(SELECT * FROM \"users\" WHERE \"users\".\"age\" > 18 INTERSECT SELECT * FROM \"users\" WHERE \"users\".\"age\" < 99)", node.toSQL());
        }
    }
}

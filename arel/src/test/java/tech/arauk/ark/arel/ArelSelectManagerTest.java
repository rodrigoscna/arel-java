package tech.arauk.ark.arel;

import junit.framework.TestCase;
import tech.arauk.ark.arel.attributes.ArelAttribute;
import tech.arauk.ark.arel.nodes.*;
import tech.arauk.ark.arel.nodes.binary.ArelNodeAs;
import tech.arauk.ark.arel.nodes.binary.ArelNodeBetween;
import tech.arauk.ark.arel.nodes.unary.ArelNodeFollowing;
import tech.arauk.ark.arel.nodes.unary.ArelNodeLimit;
import tech.arauk.ark.arel.nodes.unary.ArelNodeOffset;
import tech.arauk.ark.arel.nodes.unary.ArelNodePreceding;
import tech.arauk.ark.arel.support.FakeRecord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        public void testAndNodes() {
            ArelSelectManager selectManager = new ArelSelectManager();
            List<Object> children = new ArrayList<>();
            children.add("foo");
            children.add("bar");
            children.add("baz");
            ArelNodeAnd clause = selectManager.createAnd(children);

            assertSame(ArelNodeAnd.class, clause.getClass());
            assertEquals(children, clause.children);
        }

        public void testFroms() {
            ArelSelectManager selectManager = new ArelSelectManager();

            assertEquals(new ArrayList<>(), selectManager.froms());
        }

        public void testInsertManagers() {
            ArelSelectManager selectManager = new ArelSelectManager();
            Object insertManager = selectManager.createInsert();

            assertSame(ArelInsertManager.class, insertManager.getClass());
        }

        public void testJoinNodes() {
            ArelSelectManager selectManager = new ArelSelectManager();
            Object join = selectManager.createJoin("foo", "bar");

            assertSame(ArelNodeInnerJoin.class, join.getClass());
            assertEquals("foo", ((ArelNodeJoin) join).left());
            assertEquals("bar", ((ArelNodeJoin) join).right());
        }

        public void testJoinNodesWithFullOuterJoin() {
            ArelSelectManager selectManager = new ArelSelectManager();
            Object join = selectManager.createJoin("foo", "bar", ArelNodeFullOuterJoin.class);

            assertSame(ArelNodeFullOuterJoin.class, join.getClass());
            assertEquals("foo", ((ArelNodeFullOuterJoin) join).left());
            assertEquals("bar", ((ArelNodeFullOuterJoin) join).right());
        }

        public void testJoinNodesWithOuterJoin() {
            ArelSelectManager selectManager = new ArelSelectManager();
            Object join = selectManager.createJoin("foo", "bar", ArelNodeOuterJoin.class);

            assertSame(ArelNodeOuterJoin.class, join.getClass());
            assertEquals("foo", ((ArelNodeOuterJoin) join).left());
            assertEquals("bar", ((ArelNodeOuterJoin) join).right());
        }

        public void testJoinNodesWithRightOuterJoin() {
            ArelSelectManager selectManager = new ArelSelectManager();
            Object join = selectManager.createJoin("foo", "bar", ArelNodeRightOuterJoin.class);

            assertSame(ArelNodeRightOuterJoin.class, join.getClass());
            assertEquals("foo", ((ArelNodeRightOuterJoin) join).left());
            assertEquals("bar", ((ArelNodeRightOuterJoin) join).right());
        }

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

        public void testOrder() {
            ArelTable table = new ArelTable("users");
            ArelSelectManager selectManager = new ArelSelectManager();
            selectManager.project(new ArelNodeSqlLiteral("*"));
            selectManager.from(table);
            selectManager.order(table.get("id"));

            assertEquals("SELECT * FROM \"users\" ORDER BY \"users\".\"id\"", selectManager.toSQL());
        }

        public void testOrderWithMultipleItems() {
            ArelTable table = new ArelTable("users");
            ArelSelectManager selectManager = new ArelSelectManager();
            selectManager.project(new ArelNodeSqlLiteral("*"));
            selectManager.from(table);
            selectManager.order(table.get("id"), table.get("name"));

            assertEquals("SELECT * FROM \"users\" ORDER BY \"users\".\"id\", \"users\".\"name\"", selectManager.toSQL());
        }

        public void testOrderMethodChain() {
            ArelTable table = new ArelTable("users");
            ArelSelectManager selectManager = new ArelSelectManager();

            assertEquals(selectManager, selectManager.order(table.get("id")));
        }

        public void testOrderAttributes() {
            ArelTable table = new ArelTable("users");
            ArelSelectManager selectManager = new ArelSelectManager();
            selectManager.project(new ArelNodeSqlLiteral("*"));
            selectManager.from(table);
            selectManager.order(table.get("id").desc());

            assertEquals("SELECT * FROM \"users\" ORDER BY \"users\".\"id\" DESC", selectManager.toSQL());
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

        public void testGroupMethodChain() {
            ArelTable table = new ArelTable("users");
            ArelSelectManager selectManager = new ArelSelectManager();

            assertEquals(selectManager, selectManager.group(table.get("id")));
        }

        public void testGroupToReceiveMultipleItems() {
            ArelTable table = new ArelTable("users");
            ArelSelectManager selectManager = new ArelSelectManager();
            selectManager.from(table);
            selectManager.group(table.get("id"), table.get("name"));

            assertEquals("SELECT FROM \"users\" GROUP BY \"users\".\"id\", \"users\".\"name\"", selectManager.toSQL());
        }

        public void testGroupWithAnAttribute() {
            ArelTable table = new ArelTable("users");
            ArelSelectManager selectManager = new ArelSelectManager();
            selectManager.from(table);
            selectManager.group(table.get("id"));

            assertEquals("SELECT FROM \"users\" GROUP BY \"users\".\"id\"", selectManager.toSQL());
        }
    }

    public static class As extends Base {
        public void testAsByGroupingTheAst() {
            ArelSelectManager selectManager = new ArelSelectManager();
            ArelNodeTableAlias tableAlias = selectManager.as(Arel.sql("foo"));

            assertSame(ArelNodeGrouping.class, tableAlias.left().getClass());
            assertEquals(selectManager.ast, ((ArelNodeGrouping) tableAlias.left()).expr());
            assertEquals(Arel.sql("foo"), tableAlias.right());
        }

        public void testAsConversionToSQLLiteral() {
            ArelSelectManager selectManager = new ArelSelectManager();
            ArelNodeTableAlias tableAlias = selectManager.as("foo");

            assertSame(ArelNodeSqlLiteral.class, tableAlias.right().getClass());
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

        public void testOnWithTwoParams() {
            ArelTable left = new ArelTable("users");
            ArelNodeTableAlias right = left.alias();
            ArelNode predicate = left.get("id").eq(right.get("id"));

            ArelSelectManager selectManager = new ArelSelectManager();
            selectManager.from(left);
            selectManager.join(right).on(predicate, predicate);

            assertEquals("SELECT FROM \"users\" INNER JOIN \"users\" \"users_2\" ON \"users\".\"id\" = \"users_2\".\"id\" AND \"users\".\"id\" = \"users_2\".\"id\"", selectManager.toSQL());
        }

        public void testOnWithThreeParams() {
            ArelTable left = new ArelTable("users");
            ArelNodeTableAlias right = left.alias();
            ArelNode predicate = left.get("id").eq(right.get("id"));

            ArelSelectManager selectManager = new ArelSelectManager();
            selectManager.from(left);
            selectManager.join(right).on(predicate, predicate, left.get("name").eq(right.get("name")));

            assertEquals("SELECT FROM \"users\" INNER JOIN \"users\" \"users_2\" ON \"users\".\"id\" = \"users_2\".\"id\" AND \"users\".\"id\" = \"users_2\".\"id\" AND \"users\".\"name\" = \"users_2\".\"name\"", selectManager.toSQL());
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

    public static class Except extends Base {
        private ArelSelectManager mSelectManager1;
        private ArelSelectManager mSelectManager2;

        @Override
        protected void setUp() throws Exception {
            super.setUp();

            ArelTable table = new ArelTable("users");

            mSelectManager1 = new ArelSelectManager(table);
            mSelectManager1.project(Arel.star());
            mSelectManager1.where(table.get("age").between(18, 60));

            mSelectManager2 = new ArelSelectManager(table);
            mSelectManager2.project(Arel.star());
            mSelectManager2.where(table.get("age").between(40, 99));
        }

        public void testExceptWithTwoManagers() {
            ArelNode node = mSelectManager1.except(mSelectManager2);

            assertEquals("(SELECT * FROM \"users\" WHERE \"users\".\"age\" BETWEEN 18 AND 60 EXCEPT SELECT * FROM \"users\" WHERE \"users\".\"age\" BETWEEN 40 AND 99)", node.toSQL());
        }
    }

    public static class With extends Base {
        public void testWith() {
            ArelTable users = new ArelTable("users");
            ArelTable usersTop = new ArelTable("users_top");
            ArelTable comments = new ArelTable("comments");

            ArelTreeManager top = users.project(users.get("id")).where(users.get("karma").gt(100));
            ArelNodeAs usersAs = new ArelNodeAs(usersTop, top);

            ArelSelectManager selectManager = comments.project(Arel.star()).with(usersAs);
            selectManager = selectManager.where(comments.get("author_id").in(usersTop.project(usersTop.get("id"))));

            assertEquals("WITH \"users_top\" AS (SELECT \"users\".\"id\" FROM \"users\" WHERE \"users\".\"karma\" > 100) SELECT * FROM \"comments\" WHERE \"comments\".\"author_id\" IN (SELECT \"users_top\".\"id\" FROM \"users_top\")", selectManager.toSQL());
        }

        public void testWithRecursive() {
            ArelTable comments = new ArelTable("comments");
            ArelAttribute commentsId = comments.get("id");
            ArelAttribute commentsParentId = comments.get("parent_id");

            ArelTable replies = new ArelTable("replies");
            ArelAttribute repliesId = replies.get("id");

            ArelSelectManager recursiveTerm = new ArelSelectManager();
            recursiveTerm.from(comments).project(commentsId, commentsParentId).where(commentsId.eq(42));

            ArelSelectManager nonRecursiveTerm = new ArelSelectManager();
            nonRecursiveTerm.from(comments).project(commentsId, commentsParentId).join(replies).on(commentsParentId.eq(repliesId));

            ArelNode union = recursiveTerm.union(nonRecursiveTerm);

            ArelNodeAs asStatement = new ArelNodeAs(replies, union);

            ArelSelectManager selectManager = new ArelSelectManager();
            selectManager.withRecursive(asStatement).from(replies).project(Arel.star());

            assertEquals("WITH RECURSIVE \"replies\" AS (" +
                    "SELECT \"comments\".\"id\", \"comments\".\"parent_id\" FROM \"comments\" WHERE \"comments\".\"id\" = 42 " +
                    "UNION " +
                    "SELECT \"comments\".\"id\", \"comments\".\"parent_id\" FROM \"comments\" INNER JOIN \"replies\" ON \"comments\".\"parent_id\" = \"replies\".\"id\"" +
                    ") " +
                    "SELECT * FROM \"replies\"", selectManager.toSQL());
        }
    }

    public static class Statement extends Base {
        public void testStatement() {
            ArelTable table = new ArelTable("users");
            ArelSelectManager selectManager = table.from();
            assertNotNull(selectManager.ast);
        }

        public void testOrder() {
            ArelTable table = new ArelTable("users");
            ArelSelectManager selectManager = table.from();
            selectManager.project(Arel.sql("*"));
            selectManager.from(table);
            selectManager.order(new ArelNodeAscending(Arel.sql("foo")));

            assertEquals("SELECT * FROM \"users\" ORDER BY foo ASC", selectManager.toSQL());
        }
    }

    public static class Taken extends Base {
        public void testLimit() {
            ArelSelectManager selectManager = new ArelSelectManager();
            selectManager.take(10);

            assertEquals(new ArelNodeLimit(10), selectManager.taken());
        }
    }

    public static class Lock extends Base {
        public void testLock() {
            ArelTable table = new ArelTable("users");
            ArelSelectManager selectManager = table.from();
            selectManager.lock();

            assertEquals("SELECT FROM \"users\" FOR UPDATE", selectManager.toSQL());
        }
    }

    public static class Orders extends Base {
        public void testOrders() {
            ArelTable table = new ArelTable("users");
            ArelSelectManager selectManager = new ArelSelectManager();
            List<Object> orders = new ArrayList<>();
            orders.add(table.get("id"));
            selectManager.order(table.get("id"));

            assertEquals(orders, selectManager.orders());
        }
    }

    public static class Join extends Base {
        public void testJoin() {
            ArelTable left = new ArelTable("users");
            ArelNodeTableAlias right = left.alias();
            ArelNode predicate = left.get("id").eq(right.get("id"));

            ArelSelectManager selectManager = new ArelSelectManager();
            selectManager.from(left);
            selectManager.join(right).on(predicate);

            assertEquals("SELECT FROM \"users\" INNER JOIN \"users\" \"users_2\" ON \"users\".\"id\" = \"users_2\".\"id\"", selectManager.toSQL());
        }

        public void testJoinWithClass() {
            ArelTable left = new ArelTable("users");
            ArelNodeTableAlias right = left.alias();
            ArelNode predicate = left.get("id").eq(right.get("id"));

            ArelSelectManager selectManager = new ArelSelectManager();
            selectManager.from(left);
            selectManager.join(right, ArelNodeOuterJoin.class).on(predicate);

            assertEquals("SELECT FROM \"users\" LEFT OUTER JOIN \"users\" \"users_2\" ON \"users\".\"id\" = \"users_2\".\"id\"", selectManager.toSQL());
        }

        public void testJoinWithFullOuter() {
            ArelTable left = new ArelTable("users");
            ArelNodeTableAlias right = left.alias();
            ArelNode predicate = left.get("id").eq(right.get("id"));

            ArelSelectManager selectManager = new ArelSelectManager();
            selectManager.from(left);
            selectManager.join(right, ArelNodeFullOuterJoin.class).on(predicate);

            assertEquals("SELECT FROM \"users\" FULL OUTER JOIN \"users\" \"users_2\" ON \"users\".\"id\" = \"users_2\".\"id\"", selectManager.toSQL());
        }

        public void testJoinWithRightOuter() {
            ArelTable left = new ArelTable("users");
            ArelNodeTableAlias right = left.alias();
            ArelNode predicate = left.get("id").eq(right.get("id"));

            ArelSelectManager selectManager = new ArelSelectManager();
            selectManager.from(left);
            selectManager.join(right, ArelNodeRightOuterJoin.class).on(predicate);

            assertEquals("SELECT FROM \"users\" RIGHT OUTER JOIN \"users\" \"users_2\" ON \"users\".\"id\" = \"users_2\".\"id\"", selectManager.toSQL());
        }

        public void testJoinWithNull() {
            ArelSelectManager selectManager = new ArelSelectManager();

            assertEquals(selectManager, selectManager.join(null));
        }
    }

    public static class OuterJoin extends Base {
        public void testOuterJoin() {
            ArelTable left = new ArelTable("users");
            ArelNodeTableAlias right = left.alias();
            ArelNodeBinary predicate = left.get("id").eq(right.get("id"));

            ArelSelectManager selectManager = new ArelSelectManager();
            selectManager.from(left);
            selectManager.outerJoin(right).on(predicate);

            assertEquals("SELECT FROM \"users\" LEFT OUTER JOIN \"users\" \"users_2\" ON \"users\".\"id\" = \"users_2\".\"id\"", selectManager.toSQL());
        }

        public void testOuterJoinWithNull() {
            ArelSelectManager selectManager = new ArelSelectManager();

            assertEquals(selectManager, selectManager.outerJoin(null));
        }
    }

    public static class Joins extends Base {
        public void testInnerJoin() {
            ArelTable table = new ArelTable("users");
            ArelNodeTableAlias alias = table.alias();

            ArelSelectManager selectManager = new ArelSelectManager();
            selectManager.from(new ArelNodeInnerJoin(alias, table.get("id").eq(alias.get("id"))));

            assertEquals("SELECT FROM INNER JOIN \"users\" \"users_2\" \"users\".\"id\" = \"users_2\".\"id\"", selectManager.toSQL());
        }

        public void testOuterJoin() {
            ArelTable table = new ArelTable("users");
            ArelNodeTableAlias alias = table.alias();

            ArelSelectManager selectManager = new ArelSelectManager();
            selectManager.from(new ArelNodeOuterJoin(alias, table.get("id").eq(alias.get("id"))));

            assertEquals("SELECT FROM LEFT OUTER JOIN \"users\" \"users_2\" \"users\".\"id\" = \"users_2\".\"id\"", selectManager.toSQL());
        }

        public void testJoinWithSelf() {
            ArelTable left = new ArelTable("users");
            ArelNodeTableAlias right = left.alias();
            ArelNodeBinary predicate = left.get("id").eq(right.get("id"));

            ArelSelectManager selectManager = left.join(right);
            selectManager.project(new ArelNodeSqlLiteral("*"));

            assertEquals(selectManager, selectManager.on(predicate));
            assertEquals("SELECT * FROM \"users\" INNER JOIN \"users\" \"users_2\" ON \"users\".\"id\" = \"users_2\".\"id\"", selectManager.toSQL());
        }

        public void testJoinWithString() {
            ArelSelectManager selectManager = new ArelSelectManager();
            selectManager.from(new ArelNodeStringJoin(ArelNodes.buildQuoted("hello")));

            assertEquals("SELECT FROM 'hello'", selectManager.toSQL());
        }

        public void testJoinWithTable() {
            ArelTable users = new ArelTable("users");
            ArelTable comments = new ArelTable("comments");

            ArelNodeTableAlias counts = comments.from().group(comments.get("user_id")).project(comments.get("user_id").as("user_id"), comments.get("user_id").count().as("count")).as("counts");

            ArelSelectManager joins = users.join(counts).on(counts.get("user_id").eq(10));

            assertEquals("SELECT FROM \"users\" INNER JOIN (SELECT \"comments\".\"user_id\" AS user_id, COUNT(\"comments\".\"user_id\") AS count FROM \"comments\" GROUP BY \"comments\".\"user_id\") counts ON counts.\"user_id\" = 10", joins.toSQL());
        }
    }

    public static class Window extends Base {
        public void testWindow() {
            ArelTable table = new ArelTable("users");
            ArelSelectManager selectManager = new ArelSelectManager();
            selectManager.from(table);
            selectManager.window("a_window");

            assertEquals("SELECT FROM \"users\" WINDOW \"a_window\" AS ()", selectManager.toSQL());
        }

        public void testWindowWithOrder() {
            ArelTable table = new ArelTable("users");
            ArelSelectManager selectManager = new ArelSelectManager();
            selectManager.from(table);
            selectManager.window("a_window").order(table.get("foo").asc());

            assertEquals("SELECT FROM \"users\" WINDOW \"a_window\" AS (ORDER BY \"users\".\"foo\" ASC)", selectManager.toSQL());
        }

        public void testWindowWithOrderWithMultipleItems() {
            ArelTable table = new ArelTable("users");
            ArelSelectManager selectManager = new ArelSelectManager();
            selectManager.from(table);
            selectManager.window("a_window").order(table.get("foo").asc(), table.get("bar").desc());

            assertEquals("SELECT FROM \"users\" WINDOW \"a_window\" AS (ORDER BY \"users\".\"foo\" ASC, \"users\".\"bar\" DESC)", selectManager.toSQL());
        }

        public void testWindowWithPartition() {
            ArelTable table = new ArelTable("users");
            ArelSelectManager selectManager = new ArelSelectManager();
            selectManager.from(table);
            selectManager.window("a_window").partition(table.get("bar"));

            assertEquals("SELECT FROM \"users\" WINDOW \"a_window\" AS (PARTITION BY \"users\".\"bar\")", selectManager.toSQL());
        }

        public void testWindowWithPartitionAndOrder() {
            ArelTable table = new ArelTable("users");
            ArelSelectManager selectManager = new ArelSelectManager();
            selectManager.from(table);
            selectManager.window("a_window").partition(table.get("foo")).order(table.get("foo").asc());

            assertEquals("SELECT FROM \"users\" WINDOW \"a_window\" AS (PARTITION BY \"users\".\"foo\" ORDER BY \"users\".\"foo\" ASC)", selectManager.toSQL());
        }

        public void testWindowWithPartitionWithMultipleItems() {
            ArelTable table = new ArelTable("users");
            ArelSelectManager selectManager = new ArelSelectManager();
            selectManager.from(table);
            selectManager.window("a_window").partition(table.get("bar"), table.get("baz"));

            assertEquals("SELECT FROM \"users\" WINDOW \"a_window\" AS (PARTITION BY \"users\".\"bar\", \"users\".\"baz\")", selectManager.toSQL());
        }

        public void testWindowWithUnboundedPrecedingRows() {
            ArelTable table = new ArelTable("users");
            ArelSelectManager selectManager = new ArelSelectManager();
            selectManager.from(table);
            selectManager.window("a_window").rows(new ArelNodePreceding());

            assertEquals("SELECT FROM \"users\" WINDOW \"a_window\" AS (ROWS UNBOUNDED PRECEDING)", selectManager.toSQL());
        }

        public void testWindowWithBoundedPrecedingRows() {
            ArelTable table = new ArelTable("users");
            ArelSelectManager selectManager = new ArelSelectManager();
            selectManager.from(table);
            selectManager.window("a_window").rows(new ArelNodePreceding(5));

            assertEquals("SELECT FROM \"users\" WINDOW \"a_window\" AS (ROWS 5 PRECEDING)", selectManager.toSQL());
        }

        public void testWindowWithUnboundedFollowingRows() {
            ArelTable table = new ArelTable("users");
            ArelSelectManager selectManager = new ArelSelectManager();
            selectManager.from(table);
            selectManager.window("a_window").rows(new ArelNodeFollowing());

            assertEquals("SELECT FROM \"users\" WINDOW \"a_window\" AS (ROWS UNBOUNDED FOLLOWING)", selectManager.toSQL());
        }

        public void testWindowWithBoundedFollowingRows() {
            ArelTable table = new ArelTable("users");
            ArelSelectManager selectManager = new ArelSelectManager();
            selectManager.from(table);
            selectManager.window("a_window").rows(new ArelNodeFollowing(5));

            assertEquals("SELECT FROM \"users\" WINDOW \"a_window\" AS (ROWS 5 FOLLOWING)", selectManager.toSQL());
        }

        public void testWindowWithCurrentRow() {
            ArelTable table = new ArelTable("users");
            ArelSelectManager selectManager = new ArelSelectManager();
            selectManager.from(table);
            selectManager.window("a_window").rows(new ArelNodeCurrentRow());

            assertEquals("SELECT FROM \"users\" WINDOW \"a_window\" AS (ROWS CURRENT ROW)", selectManager.toSQL());
        }

        public void testWindowWithTwoRowsDelimiters() {
            ArelTable table = new ArelTable("users");
            ArelSelectManager selectManager = new ArelSelectManager();
            selectManager.from(table);

            List<Object> ands = new ArrayList<>();
            ands.add(new ArelNodePreceding());
            ands.add(new ArelNodeCurrentRow());

            ArelNodeWindow window = selectManager.window("a_window");
            window.frame(new ArelNodeBetween(window.rows(), new ArelNodeAnd(ands)));

            assertEquals("SELECT FROM \"users\" WINDOW \"a_window\" AS (ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW)", selectManager.toSQL());
        }

        public void testWindowWithUnboundedPrecedingRange() {
            ArelTable table = new ArelTable("users");
            ArelSelectManager selectManager = new ArelSelectManager();
            selectManager.from(table);
            selectManager.window("a_window").range(new ArelNodePreceding());

            assertEquals("SELECT FROM \"users\" WINDOW \"a_window\" AS (RANGE UNBOUNDED PRECEDING)", selectManager.toSQL());
        }

        public void testWindowWithBoundedPrecedingRange() {
            ArelTable table = new ArelTable("users");
            ArelSelectManager selectManager = new ArelSelectManager();
            selectManager.from(table);
            selectManager.window("a_window").range(new ArelNodePreceding(5));

            assertEquals("SELECT FROM \"users\" WINDOW \"a_window\" AS (RANGE 5 PRECEDING)", selectManager.toSQL());
        }

        public void testWindowWithUnboundedFollowingRange() {
            ArelTable table = new ArelTable("users");
            ArelSelectManager selectManager = new ArelSelectManager();
            selectManager.from(table);
            selectManager.window("a_window").range(new ArelNodeFollowing());

            assertEquals("SELECT FROM \"users\" WINDOW \"a_window\" AS (RANGE UNBOUNDED FOLLOWING)", selectManager.toSQL());
        }

        public void testWindowWithBoundedFollowingRange() {
            ArelTable table = new ArelTable("users");
            ArelSelectManager selectManager = new ArelSelectManager();
            selectManager.from(table);
            selectManager.window("a_window").range(new ArelNodeFollowing(5));

            assertEquals("SELECT FROM \"users\" WINDOW \"a_window\" AS (RANGE 5 FOLLOWING)", selectManager.toSQL());
        }

        public void testWindowWithCurrentRowRange() {
            ArelTable table = new ArelTable("users");
            ArelSelectManager selectManager = new ArelSelectManager();
            selectManager.from(table);
            selectManager.window("a_window").range(new ArelNodeCurrentRow());

            assertEquals("SELECT FROM \"users\" WINDOW \"a_window\" AS (RANGE CURRENT ROW)", selectManager.toSQL());
        }

        public void testWindowWithTwoRangeDelimiters() {
            ArelTable table = new ArelTable("users");
            ArelSelectManager selectManager = new ArelSelectManager();
            selectManager.from(table);

            List<Object> ands = new ArrayList<>();
            ands.add(new ArelNodePreceding());
            ands.add(new ArelNodeCurrentRow());

            ArelNodeWindow window = selectManager.window("a_window");
            window.frame(new ArelNodeBetween(window.range(), new ArelNodeAnd(ands)));

            assertEquals("SELECT FROM \"users\" WINDOW \"a_window\" AS (RANGE BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW)", selectManager.toSQL());
        }
    }

    public static class Delete extends Base {
        public void testDeleteFrom() {
            ArelTable table = new ArelTable("users");
            ArelSelectManager selectManager = new ArelSelectManager();
            selectManager.from(table);

            ArelDeleteManager deleteManager = selectManager.compileDelete();

            assertEquals("DELETE FROM \"users\"", deleteManager.toSQL());
        }

        public void testDeleteFromWithWhere() {
            ArelTable table = new ArelTable("users");
            ArelSelectManager selectManager = new ArelSelectManager();
            selectManager.from(table);
            selectManager.where(table.get("id").eq(10));

            ArelDeleteManager deleteManager = selectManager.compileDelete();

            assertEquals("DELETE FROM \"users\" WHERE \"users\".\"id\" = 10", deleteManager.toSQL());
        }
    }

    public static class WhereSql extends Base {
        public void testWhereSql() {
            ArelTable table = new ArelTable("users");
            ArelSelectManager selectManager = new ArelSelectManager();
            selectManager.from(table);
            selectManager.where(table.get("id").eq(10));

            assertEquals("WHERE \"users\".\"id\" = 10", selectManager.whereSql());
        }

        public void testWhereSqlWithNull() {
            ArelTable table = new ArelTable("users");
            ArelSelectManager selectManager = new ArelSelectManager();
            selectManager.from(table);

            assertEquals("", selectManager.whereSql());
        }
    }

    public static class Update extends Base {
        public void testUpdate() {
            ArelTable table = new ArelTable("users");
            ArelSelectManager selectManager = new ArelSelectManager();
            selectManager.from(table);

            Map<Object, Object> values = new HashMap<>();
            values.put(table.get("id"), 1);

            ArelUpdateManager updateManager = selectManager.compileUpdate(values, new ArelAttribute(table, "id"));

            assertEquals("UPDATE \"users\" SET \"id\" = 1", updateManager.toSQL());
        }

        public void testUpdateWithString() {
            ArelTable table = new ArelTable("users");
            ArelSelectManager selectManager = new ArelSelectManager();
            selectManager.from(table);

            ArelUpdateManager updateManager = selectManager.compileUpdate(new ArelNodeSqlLiteral("foo = bar"), new ArelAttribute(table, "id"));

            assertEquals("UPDATE \"users\" SET foo = bar", updateManager.toSQL());
        }

        public void testUpdateWithLimit() {
            ArelTable table = new ArelTable("users");
            ArelSelectManager selectManager = new ArelSelectManager();
            selectManager.from(table);
            selectManager.take(1);

            ArelUpdateManager updateManager = selectManager.compileUpdate(new ArelNodeSqlLiteral("foo = bar"), new ArelAttribute(table, "id"));
            updateManager.key(table.get("id"));

            assertEquals("UPDATE \"users\" SET foo = bar WHERE \"users\".\"id\" IN (SELECT \"users\".\"id\" FROM \"users\" LIMIT 1)", updateManager.toSQL());
        }

        public void testUpdateWithOrder() {
            ArelTable table = new ArelTable("users");
            ArelSelectManager selectManager = new ArelSelectManager();
            selectManager.from(table);
            selectManager.order("foo");

            ArelUpdateManager updateManager = selectManager.compileUpdate(new ArelNodeSqlLiteral("foo = bar"), new ArelAttribute(table, "id"));
            updateManager.key(table.get("id"));

            assertEquals("UPDATE \"users\" SET foo = bar WHERE \"users\".\"id\" IN (SELECT \"users\".\"id\" FROM \"users\" ORDER BY foo)", updateManager.toSQL());
        }

        public void testUpdateWithWhere() {
            ArelTable table = new ArelTable("users");
            ArelSelectManager selectManager = new ArelSelectManager();
            selectManager.from(table);
            selectManager.where(table.get("id").eq(10));

            Map<Object, Object> values = new HashMap<>();
            values.put(table.get("id"), 1);

            ArelUpdateManager updateManager = selectManager.compileUpdate(values, new ArelAttribute(table, "id"));

            assertEquals("UPDATE \"users\" SET \"id\" = 1 WHERE \"users\".\"id\" = 10", updateManager.toSQL());
        }

        public void testUpdateWithWhereAndLimit() {
            ArelTable table = new ArelTable("users");
            ArelSelectManager selectManager = new ArelSelectManager();
            selectManager.from(table);
            selectManager.where(table.get("foo").eq(10));
            selectManager.take(42);

            Map<Object, Object> values = new HashMap<>();
            values.put(table.get("id"), 1);

            ArelUpdateManager updateManager = selectManager.compileUpdate(values, new ArelAttribute(table, "id"));

            assertEquals("UPDATE \"users\" SET \"id\" = 1 WHERE \"users\".\"id\" IN (SELECT \"users\".\"id\" FROM \"users\" WHERE \"users\".\"foo\" = 10 LIMIT 42)", updateManager.toSQL());
        }
    }
}

package tech.arauk.ark.arel;

import junit.framework.TestCase;
import tech.arauk.ark.arel.attributes.ArelAttribute;
import tech.arauk.ark.arel.nodes.*;
import tech.arauk.ark.arel.support.FakeRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ArelTableTest {
    public static abstract class Base extends TestCase {
        static {
            ArelTable.engine = new FakeRecord.Base();
        }

        ArelTable mRelation;

        @Override
        protected void setUp() throws Exception {
            mRelation = new ArelTable("users");

            super.setUp();
        }

        @Override
        protected void tearDown() throws Exception {
            super.tearDown();
        }
    }

    public static class Alias extends Base {
        public void testAlias() {
            List<Object> nodes = new ArrayList<>();

            assertEquals(nodes, mRelation.aliases());

            Object tableAlias = mRelation.alias();
            nodes.add(tableAlias);

            assertSame(ArelNodeTableAlias.class, tableAlias.getClass());
            assertEquals(nodes, mRelation.aliases());
            assertEquals("users_2", ((ArelNodeTableAlias) tableAlias).tableName());
            assertEquals(tableAlias, ((ArelNodeTableAlias) tableAlias).get("id").relation);
        }
    }

    public static class Attribute extends Base {
        public void testAttributeGetter() {
            Object attribute = mRelation.get("id");

            assertSame(ArelAttribute.class, attribute.getClass());
            assertEquals("id", ((ArelAttribute) attribute).name);
        }
    }

    public static class Equality extends Base {
        public void testEquality() {
            ArelTable relation1 = new ArelTable("users");
            relation1.aliases(new ArrayList<>());
            relation1.aliases().add("a");
            relation1.aliases().add("b");
            relation1.aliases().add("c");
            relation1.tableAlias("zomg");

            ArelTable relation2 = new ArelTable("users");
            relation2.aliases(new ArrayList<>());
            relation2.aliases().add("a");
            relation2.aliases().add("b");
            relation2.aliases().add("c");
            relation2.tableAlias("zomg");

            assertEquals(relation1, relation2);
        }

        public void testEqualityWithDifferentValues() {
            ArelTable relation1 = new ArelTable("users");
            relation1.aliases(new ArrayList<>());
            relation1.aliases().add("a");
            relation1.aliases().add("b");
            relation1.aliases().add("c");
            relation1.tableAlias("zomg");

            ArelTable relation2 = new ArelTable("users");
            relation2.aliases(new ArrayList<>());
            relation2.aliases().add("x");
            relation2.aliases().add("y");
            relation2.aliases().add("z");
            relation2.tableAlias("zomg");

            assertFalse(Objects.equals(relation1, relation2));
        }
    }

    public static class Group extends Base {
        public void testGroup() {
            Object selectManager = mRelation.group(mRelation.get("id"));

            assertSame(ArelSelectManager.class, selectManager.getClass());
            assertEquals("SELECT FROM \"users\" GROUP BY \"users\".\"id\"", ((ArelSelectManager) selectManager).toSQL());
        }
    }

    public static class Having extends Base {
        public void testHaving() {
            Object selectManager = mRelation.having(mRelation.get("id").eq(10));

            assertSame(ArelSelectManager.class, selectManager.getClass());
            assertEquals("SELECT FROM \"users\" HAVING \"users\".\"id\" = 10", ((ArelSelectManager) selectManager).toSQL());
        }
    }

    public static class Join extends Base {
        public void testJoinWithNull() {
            Object selectManager = mRelation.join(null);

            assertSame(ArelSelectManager.class, selectManager.getClass());
            assertEquals("SELECT FROM \"users\"", ((ArelSelectManager) selectManager).toSQL());
        }

        public void testJoinWithJoinType() {
            ArelNodeTableAlias right = mRelation.alias();
            Object predicate = mRelation.get("id").eq(right.get("id"));

            Object selectManager = mRelation.join(right, ArelNodeOuterJoin.class).on(predicate);

            assertSame(ArelSelectManager.class, selectManager.getClass());
            assertEquals("SELECT FROM \"users\" LEFT OUTER JOIN \"users\" \"users_2\" ON \"users\".\"id\" = \"users_2\".\"id\"", ((ArelSelectManager) selectManager).toSQL());
        }

        public void testJoinWithOuterJoinType() {
            ArelNodeTableAlias right = mRelation.alias();
            Object predicate = mRelation.get("id").eq(right.get("id"));

            Object selectManager = mRelation.outerJoin(right).on(predicate);

            assertSame(ArelSelectManager.class, selectManager.getClass());
            assertEquals("SELECT FROM \"users\" LEFT OUTER JOIN \"users\" \"users_2\" ON \"users\".\"id\" = \"users_2\".\"id\"", ((ArelSelectManager) selectManager).toSQL());
        }
    }

    public static class New extends Base {
        public void testNewWithAlias() {
            Object table = new ArelTable("users", "foo");

            assertSame(ArelTable.class, table.getClass());
            assertEquals("foo", ((ArelTable) table).tableAlias());
        }

        public void testNewWithTableNamedAlias() {
            Object table = new ArelTable("users", "users");

            assertSame(ArelTable.class, table.getClass());
            assertEquals(null, ((ArelTable) table).tableAlias());
        }
    }

    public static class Order extends Base {
        public void testOrder() {
            Object selectManager = mRelation.order("foo");

            assertSame(ArelSelectManager.class, selectManager.getClass());
            assertEquals("SELECT FROM \"users\" ORDER BY foo", ((ArelSelectManager) selectManager).toSQL());
        }
    }

    public static class Project extends Base {
        public void testProject() {
            Object selectManager = mRelation.project(new ArelNodeSqlLiteral("*"));

            assertSame(ArelSelectManager.class, selectManager.getClass());
            assertEquals("SELECT * FROM \"users\"", ((ArelSelectManager) selectManager).toSQL());
        }

        public void testProjectWithMultipleParameters() {
            Object selectManager = mRelation.project(new ArelNodeSqlLiteral("*"), new ArelNodeSqlLiteral("*"));

            assertSame(ArelSelectManager.class, selectManager.getClass());
            assertEquals("SELECT *, * FROM \"users\"", ((ArelSelectManager) selectManager).toSQL());
        }
    }

    public static class Skip extends Base {
        public void testSkip() {
            Object selectManager = mRelation.skip(2);

            assertSame(ArelSelectManager.class, selectManager.getClass());
            assertEquals("SELECT FROM \"users\" OFFSET 2", ((ArelSelectManager) selectManager).toSQL());
        }
    }

    public static class Table extends Base {
        public void testTableFullOuterJoinCreator() {
            Object join = mRelation.createJoin("foo", "bar", ArelNodeFullOuterJoin.class);

            assertSame(ArelNodeFullOuterJoin.class, join.getClass());
            assertEquals("foo", ((ArelNodeFullOuterJoin) join).left());
            assertEquals("bar", ((ArelNodeFullOuterJoin) join).right());
        }

        public void testTableInnerJoinCreator() {
            Object join = mRelation.createJoin("foo", "bar");

            assertSame(ArelNodeInnerJoin.class, join.getClass());
            assertEquals("foo", ((ArelNodeInnerJoin) join).left());
            assertEquals("bar", ((ArelNodeInnerJoin) join).right());
        }

        public void testTableInsertManagerCreator() {
            Object insertManager = mRelation.compileInsert("VALUES(NULL)");

            assertSame(ArelInsertManager.class, insertManager.getClass());
            ((ArelInsertManager) insertManager).into(new ArelTable("users"));
            assertEquals("INSERT INTO \"users\" VALUES(NULL)", ((ArelInsertManager) insertManager).toSQL());
        }

        public void testTableName() {
            assertEquals("users", mRelation.tableName());
        }

        public void testTableOuterJoinCreator() {
            Object join = mRelation.createJoin("foo", "bar", ArelNodeOuterJoin.class);

            assertSame(ArelNodeOuterJoin.class, join.getClass());
            assertEquals("foo", ((ArelNodeOuterJoin) join).left());
            assertEquals("bar", ((ArelNodeOuterJoin) join).right());
        }

        public void testTableRightOuterJoinCreator() {
            Object join = mRelation.createJoin("foo", "bar", ArelNodeRightOuterJoin.class);

            assertSame(ArelNodeRightOuterJoin.class, join.getClass());
            assertEquals("foo", ((ArelNodeRightOuterJoin) join).left());
            assertEquals("bar", ((ArelNodeRightOuterJoin) join).right());
        }

        public void testTableStringJoinCreator() {
            Object join = mRelation.createStringJoin("foo");

            assertSame(ArelNodeStringJoin.class, join.getClass());
            assertEquals("foo", ((ArelNodeStringJoin) join).left());
        }
    }

    public static class Take extends Base {
        public void testTake() {
            Object selectManager = mRelation.take(1);

            assertSame(ArelSelectManager.class, selectManager.getClass());
            assertEquals("SELECT  FROM \"users\" LIMIT 1", ((ArelSelectManager) selectManager).toSQL());
        }
    }

    public static class Where extends Base {
        public void testWhere() {
            Object selectManager = mRelation.where(mRelation.get("id").eq(1));

            assertSame(ArelSelectManager.class, selectManager.getClass());
            ((ArelSelectManager) selectManager).project(mRelation.get("id"));
            assertEquals("SELECT \"users\".\"id\" FROM \"users\" WHERE \"users\".\"id\" = 1", ((ArelSelectManager) selectManager).toSQL());
        }
    }
}

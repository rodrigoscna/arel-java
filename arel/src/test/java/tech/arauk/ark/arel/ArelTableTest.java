package tech.arauk.ark.arel;

import junit.framework.TestCase;
import tech.arauk.ark.arel.attributes.ArelAttribute;
import tech.arauk.ark.arel.nodes.*;
import tech.arauk.ark.arel.support.FakeRecord;

import java.util.ArrayList;
import java.util.List;

public class ArelTableTest extends TestCase {
    static {
        ArelTable.engine = new FakeRecord.Base();
    }

    private ArelTable mRelation;

    @Override
    protected void setUp() throws Exception {
        mRelation = new ArelTable("users");

        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testCreateStringJoinNodes() {
        Object join = mRelation.createStringJoin("foo");

        assertSame(ArelNodeStringJoin.class, join.getClass());
        assertEquals("foo", ((ArelNodeStringJoin) join).left);
    }

    public void testCreateInnerJoinNodes() {
        Object join = mRelation.createJoin("foo", "bar");

        assertSame(ArelNodeInnerJoin.class, join.getClass());
        assertEquals("foo", ((ArelNodeInnerJoin) join).left);
        assertEquals("bar", ((ArelNodeInnerJoin) join).right);
    }

    public void testCreateJoinNodesWithAFullOuterJoinClass() {
        Object join = mRelation.createJoin("foo", "bar", ArelNodeFullOuterJoin.class);

        assertSame(ArelNodeFullOuterJoin.class, join.getClass());
        assertEquals("foo", ((ArelNodeFullOuterJoin) join).left);
        assertEquals("bar", ((ArelNodeFullOuterJoin) join).right);
    }

    public void testCreateJoinNodesWithAnOuterJoinClass() {
        Object join = mRelation.createJoin("foo", "bar", ArelNodeOuterJoin.class);

        assertSame(ArelNodeOuterJoin.class, join.getClass());
        assertEquals("foo", ((ArelNodeOuterJoin) join).left);
        assertEquals("bar", ((ArelNodeOuterJoin) join).right);
    }

    public void testCreateJoinNodesWithARightOuterJoinClass() {
        Object join = mRelation.createJoin("foo", "bar", ArelNodeRightOuterJoin.class);

        assertSame(ArelNodeRightOuterJoin.class, join.getClass());
        assertEquals("foo", ((ArelNodeRightOuterJoin) join).left);
        assertEquals("bar", ((ArelNodeRightOuterJoin) join).right);
    }

    public void testInsertManager() {
        Object insertManager = mRelation.compileInsert("VALUES(NULL)");

        assertSame(ArelInsertManager.class, insertManager.getClass());
        ((ArelInsertManager) insertManager).into(new ArelTable("users"));
        assertEquals("INSERT INTO \"users\" VALUES(NULL)", ((ArelInsertManager) insertManager).toSQL());
    }

    public void testSkip() {
        Object selectManager = mRelation.skip(2);

        assertSame(ArelSelectManager.class, selectManager.getClass());
        assertEquals("SELECT FROM \"users\" OFFSET 2", ((ArelSelectManager) selectManager).toSQL());
    }

    public void testHaving() {
        Object selectManager = mRelation.having(mRelation.get("id").eq(10));

        assertSame(ArelSelectManager.class, selectManager.getClass());
        assertEquals("SELECT FROM \"users\" HAVING \"users\".\"id\" = 10", ((ArelSelectManager) selectManager).toSQL());
    }

    public void testJoinOnNull() {
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

    public void testOuterJoin() {
        ArelNodeTableAlias right = mRelation.alias();
        Object predicate = mRelation.get("id").eq(right.get("id"));

        Object selectManager = mRelation.outerJoin(right).on(predicate);

        assertSame(ArelSelectManager.class, selectManager.getClass());
        assertEquals("SELECT FROM \"users\" LEFT OUTER JOIN \"users\" \"users_2\" ON \"users\".\"id\" = \"users_2\".\"id\"", ((ArelSelectManager) selectManager).toSQL());
    }

    public void testGroup() {
        Object selectManager = mRelation.group(mRelation.get("id"));

        assertSame(ArelSelectManager.class, selectManager.getClass());
        assertEquals("SELECT FROM \"users\" GROUP BY \"users\".\"id\"", ((ArelSelectManager) selectManager).toSQL());
    }

    public void testTableProxy() {
        List<Object> nodes = new ArrayList<>();

        assertEquals(nodes, mRelation.aliases);

        Object tableAlias = mRelation.alias();
        nodes.add(tableAlias);

        assertSame(ArelNodeTableAlias.class, tableAlias.getClass());
        assertEquals(nodes, mRelation.aliases);
        assertEquals("users_2", ((ArelNodeTableAlias) tableAlias).tableName());
        assertEquals(tableAlias, ((ArelNodeTableAlias) tableAlias).get("id").relation);
    }

    public void testTableAlias() {
        Object table = new ArelTable("users", "foo");

        assertSame(ArelTable.class, table.getClass());
        assertEquals("foo", ((ArelTable) table).tableAlias());
    }

    public void testTableAliasWithTableName() {
        Object table = new ArelTable("users", "users");

        assertSame(ArelTable.class, table.getClass());
        assertEquals(null, ((ArelTable) table).tableAlias());
    }

    public void testOrder() {
        Object selectManager = mRelation.order("foo");

        assertSame(ArelSelectManager.class, selectManager.getClass());
        assertEquals("SELECT FROM \"users\" ORDER BY foo", ((ArelSelectManager) selectManager).toSQL());
    }

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

    public void testTake() {
        Object selectManager = mRelation.take(1);

        assertSame(ArelSelectManager.class, selectManager.getClass());
        assertEquals("SELECT  FROM \"users\" LIMIT 1", ((ArelSelectManager) selectManager).toSQL());
    }

    public void testWhere() {
        Object selectManager = mRelation.where(mRelation.get("id").eq(1));

        assertSame(ArelSelectManager.class, selectManager.getClass());
        ((ArelSelectManager) selectManager).project(mRelation.get("id"));
        assertEquals("SELECT \"users\".\"id\" FROM \"users\" WHERE \"users\".\"id\" = 1", ((ArelSelectManager) selectManager).toSQL());
    }

    public void testTableName() {
        assertEquals("users", mRelation.tableName());
    }

    public void testAttributeManufacturer() {
        Object attribute = mRelation.get("id");

        assertSame(ArelAttribute.class, attribute.getClass());
        assertEquals("id", ((ArelAttribute) attribute).name);
    }
}

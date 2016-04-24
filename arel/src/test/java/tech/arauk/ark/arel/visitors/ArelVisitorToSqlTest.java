package tech.arauk.ark.arel.visitors;

import junit.framework.TestCase;
import tech.arauk.ark.arel.Arel;
import tech.arauk.ark.arel.ArelNodes;
import tech.arauk.ark.arel.ArelTable;
import tech.arauk.ark.arel.attributes.ArelAttribute;
import tech.arauk.ark.arel.attributes.ArelAttributeTime;
import tech.arauk.ark.arel.collectors.ArelCollector;
import tech.arauk.ark.arel.connection.ArelConnection;
import tech.arauk.ark.arel.nodes.*;
import tech.arauk.ark.arel.support.FakeRecord;
import tech.arauk.ark.arel.support.TestUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;

public class ArelVisitorToSqlTest {
    public static abstract class Base extends TestCase {
        static {
            ArelTable.engine = new FakeRecord.Base();
        }

        ArelConnection connection;
        ArelVisitorToSql visitor;
        ArelTable table;
        ArelAttribute attribute;

        @Override
        protected void setUp() throws Exception {
            super.setUp();

            this.connection = new FakeRecord.Base().connection;
            this.visitor = new ArelVisitorToSql(this.connection);
            this.table = new ArelTable("users");
            this.attribute = this.table.get("id");
        }

        @Override
        protected void tearDown() throws Exception {
            super.tearDown();
        }

        String compile(Object node) {
            return this.visitor.accept(node, new ArelCollector()).value();
        }
    }

    public static class BindParamNode extends Base {
        public void testBindParam() {
            ArelNodeBindParam node = new ArelNodeBindParam();

            assertEquals("?", compile(node));
        }

        public void testBindParamWithValues() {
            ArelNodeBindParam bindParam = new ArelNodeBindParam();
            ArelNodeValues values = new ArelNodeValues(TestUtils.objectsToList(bindParam));

            assertEquals("VALUES (?)", compile(values));
        }
    }

    public static class EqualityNode extends Base {
        public void testEquality() {
            ArelNodeEquality equality = new ArelTable("users").get("name").eq("Aaron Patterson");

            assertEquals("\"users\".\"name\" = 'Aaron Patterson'", compile(equality));
        }

        public void testEqualityColumnQuote() {
            Object value = ArelNodes.buildQuoted("1-fooo", this.table.get("id"));

            assertEquals("\"users\".\"id\" = 1", compile(new ArelNodeEquality(table.get("id"), value)));
        }

        public void testEqualityColumnQuoteInteger() {
            assertEquals("\"users\".\"name\" = '0'", compile(this.table.get("name").eq(0)));
        }

        public void testEqualityWithFalse() {
            Object value = ArelNodes.buildQuoted(false, this.table.get("active"));

            assertEquals("'f' = 'f'", compile(new ArelNodeEquality(value, value)));
        }

        public void testEqualityWithNull() {
            assertEquals("\"users\".\"name\" IS NULL", compile(new ArelNodeEquality(this.table.get("name"), null)));
        }
    }

    public static class GroupingNode extends Base {
        public void testGrouping() {
            assertEquals("('foo')", compile(new ArelNodeGrouping(new ArelNodeGrouping(ArelNodes.buildQuoted("foo")))));
        }
    }

    public static class Limit extends Base {
        public void testLimit() {
            ArelNodeSelectStatement selectStatement = new ArelNodeSelectStatement();
            selectStatement.limit(new ArelNodeLimit(ArelNodes.buildQuoted("omg")));

            assertEquals("SELECT LIMIT 'omg'", compile(selectStatement));
        }

        public void testLimitWithoutColumnTypeCoersion() {
            Object selectStatement = this.table.where(table.get("name").eq(0)).take(1).ast();

            assertEquals("SELECT  FROM \"users\" WHERE \"users\".\"name\" = '0' LIMIT 1", compile(selectStatement));
        }
    }

    public static class MatchesNode extends Base {
        public void testMatchesNode() {
            Object matches = this.table.get("name").matches("foo%");
            assertEquals("\"users\".\"name\" LIKE 'foo%'", compile(matches));
        }
    }

    public static class NotEqualNode extends Base {
        public void testNotEqualWithFalse() {
            Object value = ArelNodes.buildQuoted(false, this.table.get("active"));
            ArelNodeNotEqual node = new ArelNodeNotEqual(this.table.get("active"), value);

            assertEquals("\"users\".\"active\" != 'f'", compile(node));
        }

        public void testNotEqualWithNull() {
            Object value = ArelNodes.buildQuoted(null, this.table.get("active"));
            ArelNodeNotEqual node = new ArelNodeNotEqual(this.table.get("name"), value);

            assertEquals("\"users\".\"name\" IS NOT NULL", compile(node));
        }
    }

    public static class ToSql extends Base {
        public void testToSqlWithExpressions() {
            ArelNodeFunction function;

            function = new ArelNodeAvg(TestUtils.objectsToList(Arel.star()));

            assertEquals("AVG(*)", compile(function));

            function = new ArelNodeCount(TestUtils.objectsToList(Arel.star()));

            assertEquals("COUNT(*)", compile(function));

            function = new ArelNodeMax(TestUtils.objectsToList(Arel.star()));

            assertEquals("MAX(*)", compile(function));

            function = new ArelNodeMin(TestUtils.objectsToList(Arel.star()));

            assertEquals("MIN(*)", compile(function));

            function = new ArelNodeSum(TestUtils.objectsToList(Arel.star()));

            assertEquals("SUM(*)", compile(function));
        }

        public void testToSqlWithExpressionsDistinct() {
            ArelNodeFunction function;

            function = new ArelNodeAvg(TestUtils.objectsToList(Arel.star()));
            function.distinct(true);

            assertEquals("AVG(DISTINCT *)", compile(function));

            function = new ArelNodeCount(TestUtils.objectsToList(Arel.star()));
            function.distinct(true);

            assertEquals("COUNT(DISTINCT *)", compile(function));

            function = new ArelNodeMax(TestUtils.objectsToList(Arel.star()));
            function.distinct(true);

            assertEquals("MAX(DISTINCT *)", compile(function));

            function = new ArelNodeMin(TestUtils.objectsToList(Arel.star()));
            function.distinct(true);

            assertEquals("MIN(DISTINCT *)", compile(function));

            function = new ArelNodeSum(TestUtils.objectsToList(Arel.star()));
            function.distinct(true);

            assertEquals("SUM(DISTINCT *)", compile(function));
        }

        public void testToSqlWithLists() {
            ArelNodeFunction function = new ArelNodeNamedFunction("omg", TestUtils.objectsToList(Arel.star(), Arel.star()));

            assertEquals("omg(*, *)", compile(function));
        }

        public void testToSqlWithNamedFunctions() {
            ArelNodeFunction function = new ArelNodeNamedFunction("omg", TestUtils.objectsToList(Arel.star()));

            assertEquals("omg(*)", compile(function));
        }

        public void testToSqlWithNamedFunctionsMethodChain() {
            ArelNodeFunction function = new ArelNodeNamedFunction("omg", TestUtils.objectsToList(Arel.star()));

            assertEquals("omg(*) = 2", compile(function.eq(2)));
        }

        public void testToSqlWithSqlLiterals() {
            ArelAttribute attribute = this.table.get(Arel.star());

            assertEquals("\"users\".*", compile(attribute));
        }
    }

    public static class Visit extends Base {
        public void testVisitBigDecimal() {
            assertEquals("2.14", compile(BigDecimal.valueOf(2.14d)));
        }

        public void testVisitBigInteger() {
            assertEquals("8787878092", compile(BigInteger.valueOf(8787878092L)));
        }

        public void testVisitBoolean() {
            assertEquals("\"users\".\"bool\" = 't'", compile(this.table.get("bool").eq(true)));
        }

        public void testVisitDate() {
            Date date = new Date();

            assertEquals(String.format("\"users\".\"created_at_date\" = '%s'", new SimpleDateFormat("yyyy-MM-dd").format(date)), compile(this.table.get("created_at_date").eq(date)));
        }

        public void testVisitDateTime() {
            Date date = new Date();

            assertEquals(String.format("\"users\".\"created_at\" = '%s'", new SimpleDateFormat("yyyy-MM-dd H:m:s").format(date)), compile(this.table.get("created_at").eq(date)));
        }

        public void testVisitDouble() {
            assertEquals("2.14", compile(2.14d));
        }

        public void testVisitFloat() {
            assertEquals("\"products\".\"price\" = 2.14", compile(new ArelTable("products").get("price").eq(2.14f)));
        }

        public void testVisitNull() {
            assertEquals("NULL", compile(ArelNodes.buildQuoted(null)));
        }

        public void testVisitQuotedClass() {
            assertEquals("'class java.util.Date'", compile(ArelNodes.buildQuoted(Date.class)));
        }

        public void testVisitQuotedHashMap() {
            Map<String, Integer> map = new HashMap<String, Integer>() {{
                put("a", 1);
            }};

            assertEquals("'{a=1}'", compile(ArelNodes.buildQuoted(map)));
        }

        public void testVisitQuotedLinkedHashMap() {
            Map<String, Integer> map = new LinkedHashMap<String, Integer>() {{
                put("a", 1);
            }};

            assertEquals("'{a=1}'", compile(ArelNodes.buildQuoted(map)));
        }

        public void testVisitQuotedTreeMap() {
            Map<String, Integer> map = new TreeMap<String, Integer>() {{
                put("a", 1);
            }};

            assertEquals("'{a=1}'", compile(ArelNodes.buildQuoted(map)));
        }

        public void testVisitQuotedHashSet() {
            Set<Integer> set = new HashSet<Integer>() {{
                add(1);
                add(2);
            }};

            assertEquals("'[1, 2]'", compile(ArelNodes.buildQuoted(set)));
        }

        public void testVisitQuotedLinkedHashSet() {
            Set<Integer> set = new LinkedHashSet<Integer>() {{
                add(1);
                add(2);
            }};

            assertEquals("'[1, 2]'", compile(ArelNodes.buildQuoted(set)));
        }

        public void testVisitQuotedTreeSet() {
            Set<Integer> set = new TreeSet<Integer>() {{
                add(1);
                add(2);
            }};

            assertEquals("'[1, 2]'", compile(ArelNodes.buildQuoted(set)));
        }

        public void testVisitTime() {
            Date date = new Date();

            assertEquals(String.format("\"users\".\"created_at_time\" = '%s'", new SimpleDateFormat("H:m:s").format(date)), compile(this.table.get("created_at_time").eq(date)));
        }
    }

    public static class VisitAttribute extends Base {
        public void testVisitAttributeTime() {
            assertEquals("\"users\".\"id\"", compile(new ArelAttributeTime(this.attribute.relation, this.attribute.name)));
        }
    }

    public static class VisitNode extends Base {
        public void testVisitNodeAnd() {
            assertEquals("\"users\".\"id\" = 10 AND \"users\".\"id\" = 11", compile(new ArelNodeAnd(TestUtils.objectsToList(this.attribute.eq(10), this.attribute.eq(11)))));
        }

        public void testVisitNodeAs() {
            assertEquals("foo AS bar", compile(new ArelNodeAs(Arel.sql("foo"), Arel.sql("bar"))));
        }

        public void testVisitNodeAssignment() {
            Object column = this.table.get("id");
            assertEquals("\"id\" = \"id\"", compile(new ArelNodeAssignment(new ArelNodeUnqualifiedColumn(column), new ArelNodeUnqualifiedColumn(column))));
        }

        public void testVisitNodeNot() {
            assertEquals("NOT (foo)", compile(new ArelNodeNot(Arel.sql("foo"))));
        }

        public void testVisitNodeOr() {
            assertEquals("\"users\".\"id\" = 10 OR \"users\".\"id\" = 11", compile(new ArelNodeOr(this.attribute.eq(10), this.attribute.eq(11))));
        }

        public void testVisitNodeSelectManager() {
            Object selectManager = new ArelTable("foo").project("bar");

            assertEquals("(SELECT bar FROM \"foo\")", compile(selectManager));
        }
    }
}

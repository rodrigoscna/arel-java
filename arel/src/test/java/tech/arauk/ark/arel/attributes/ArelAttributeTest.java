package tech.arauk.ark.arel.attributes;

import junit.framework.TestCase;
import tech.arauk.ark.arel.*;
import tech.arauk.ark.arel.connection.ArelTypeCaster;
import tech.arauk.ark.arel.nodes.*;
import tech.arauk.ark.arel.nodes.ArelNodeAvg;
import tech.arauk.ark.arel.nodes.ArelNodeMax;
import tech.arauk.ark.arel.nodes.ArelNodeMin;
import tech.arauk.ark.arel.nodes.ArelNodeSum;
import tech.arauk.ark.arel.support.FakeRecord;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class ArelAttributeTest {
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

    public static class Attribute {
        public static class Average extends Base {
            public void testAverage() {
                ArelTable relation = new ArelTable("users");

                assertSame(ArelNodeAvg.class, relation.get("id").average().getClass());
            }

            public void testAverageToSql() {
                ArelTable relation = new ArelTable("users");

                ArelSelectManager selectManager = relation.project(relation.get("id").average());

                assertEquals("SELECT AVG(\"users\".\"id\") FROM \"users\"", selectManager.toSql());
            }
        }

        public static class Asc extends Base {
            public void testAsc() {
                ArelTable relation = new ArelTable("users");

                assertSame(ArelNodeAscending.class, relation.get("id").asc().getClass());
            }

            public void testAscToSql() {
                ArelTable relation = new ArelTable("users");

                ArelSelectManager selectManager = relation.project(relation.get("id"));
                selectManager.order(relation.get("id").asc());

                assertEquals("SELECT \"users\".\"id\" FROM \"users\" ORDER BY \"users\".\"id\" ASC", selectManager.toSql());
            }
        }

        public static class Between extends Base {
            public void testBetween() {
                ArelAttribute attribute = new ArelAttribute(null, null);
                ArelNode node = attribute.between(1, 3);

                List<Object> and = new ArrayList<>();
                and.add(new ArelNodeCasted(1, attribute));
                and.add(new ArelNodeCasted(3, attribute));

                assertEquals(new ArelNodeBetween(attribute, new ArelNodeAnd(and)), node);
            }

            public void testBetweenWithExclusiveRange() {
                ArelAttribute attribute = new ArelAttribute(null, null);
                ArelNode node = attribute.between(0, 3, false);

                List<Object> and = new ArrayList<>();
                and.add(new ArelNodeGreaterThanOrEqual(attribute, new ArelNodeCasted(0, attribute)));
                and.add(new ArelNodeLessThan(attribute, new ArelNodeCasted(3, attribute)));

                assertEquals(new ArelNodeAnd(and), node);
            }

            public void testBetweenWithInfiniteRange() {
                ArelAttribute attribute = new ArelAttribute(null, null);
                ArelNode node = attribute.between(Float.NEGATIVE_INFINITY, Float.POSITIVE_INFINITY);

                assertEquals(new ArelNodeNotIn(attribute, new ArrayList<>()), node);
            }

            public void testBetweenWithQuotedInfiniteRange() {
                ArelAttribute attribute = new ArelAttribute(null, null);
                ArelNode node = attribute.between(new ArelNodeQuoted(Float.NEGATIVE_INFINITY), new ArelNodeQuoted(Float.POSITIVE_INFINITY));

                assertEquals(new ArelNodeNotIn(attribute, new ArrayList<>()), node);
            }

            public void testBetweenWithQuotedRangeEndingWithPositiveInfinity() {
                ArelAttribute attribute = new ArelAttribute(null, null);
                ArelNode node = attribute.between(new ArelNodeQuoted(0), new ArelNodeQuoted(Float.POSITIVE_INFINITY));

                assertEquals(new ArelNodeGreaterThanOrEqual(attribute, new ArelNodeQuoted(0)), node);
            }

            public void testBetweenWithQuotedRangeStartingWithNegativeInfinity() {
                ArelAttribute attribute = new ArelAttribute(null, null);
                ArelNode node = attribute.between(new ArelNodeQuoted(Float.NEGATIVE_INFINITY), new ArelNodeQuoted(3));

                assertEquals(new ArelNodeLessThanOrEqual(attribute, new ArelNodeQuoted(3)), node);
            }

            public void testBetweenWithQuotedRangeStartingWithNegativeInfinityExclusive() {
                ArelAttribute attribute = new ArelAttribute(null, null);
                ArelNode node = attribute.between(new ArelNodeQuoted(Float.NEGATIVE_INFINITY), new ArelNodeQuoted(3), false);

                assertEquals(new ArelNodeLessThan(attribute, new ArelNodeQuoted(3)), node);
            }

            public void testBetweenWithRangeEndingWithPositiveInfinity() {
                ArelAttribute attribute = new ArelAttribute(null, null);
                ArelNode node = attribute.between(0, Float.POSITIVE_INFINITY);

                assertEquals(new ArelNodeGreaterThanOrEqual(attribute, new ArelNodeCasted(0, attribute)), node);
            }

            public void testBetweenWithRangeStartingWithNegativeInfinity() {
                ArelAttribute attribute = new ArelAttribute(null, null);
                ArelNode node = attribute.between(Float.NEGATIVE_INFINITY, 3);

                assertEquals(new ArelNodeLessThanOrEqual(attribute, new ArelNodeCasted(3, attribute)), node);
            }

            public void testBetweenWithRangeStartingWithNegativeInfinityExclusive() {
                ArelAttribute attribute = new ArelAttribute(null, null);
                ArelNode node = attribute.between(Float.NEGATIVE_INFINITY, 3, false);

                assertEquals(new ArelNodeLessThan(attribute, new ArelNodeCasted(3, attribute)), node);
            }
        }

        public static class Count extends Base {
            public void testCount() {
                ArelTable relation = new ArelTable("users");

                assertSame(ArelNodeCount.class, relation.get("id").count().getClass());
            }

            public void testCountToSql() {
                ArelTable relation = new ArelTable("users");

                ArelSelectManager selectManager = relation.project(relation.get("id").count());

                assertEquals("SELECT COUNT(\"users\".\"id\") FROM \"users\"", selectManager.toSql());
            }

            public void testCountWithDistinct() {
                ArelTable relation = new ArelTable("users");

                assertSame(ArelNodeCount.class, relation.get("id").count(true).getClass());
            }

            public void testCountWithDistinctToSql() {
                ArelTable relation = new ArelTable("users");

                ArelSelectManager selectManager = relation.project(relation.get("id").count(true));

                assertEquals("SELECT COUNT(DISTINCT \"users\".\"id\") FROM \"users\"", selectManager.toSql());
            }
        }

        public static class Desc extends Base {
            public void testDesc() {
                ArelTable relation = new ArelTable("users");

                assertSame(ArelNodeDescending.class, relation.get("id").desc().getClass());
            }

            public void testDescToSql() {
                ArelTable relation = new ArelTable("users");

                ArelSelectManager selectManager = relation.project(relation.get("id"));
                selectManager.order(relation.get("id").desc());

                assertEquals("SELECT \"users\".\"id\" FROM \"users\" ORDER BY \"users\".\"id\" DESC", selectManager.toSql());
            }
        }

        public static class DoesNotMatch extends Base {
            public void testDoesNotMatch() {
                ArelTable relation = new ArelTable("users");

                assertSame(ArelNodeDoesNotMatch.class, relation.get("name").doesNotMatch("%bacon%").getClass());
            }

            public void testDoesNotMatchToSql() {
                ArelTable relation = new ArelTable("users");

                ArelSelectManager selectManager = relation.project(relation.get("id"));
                selectManager.where(relation.get("name").doesNotMatch("%bacon%"));

                assertEquals("SELECT \"users\".\"id\" FROM \"users\" WHERE \"users\".\"name\" NOT LIKE '%bacon%'", selectManager.toSql());
            }
        }

        public static class DoesNotMatchAll extends Base {
            public void testDoesNotMatchAll() {
                ArelTable relation = new ArelTable("users");

                assertSame(ArelNodeGrouping.class, relation.get("name").doesNotMatchAll("%bacon%").getClass());
            }

            public void testDoesNotMatchAllToSql() {
                ArelTable relation = new ArelTable("users");

                ArelSelectManager selectManager = relation.project(relation.get("id"));
                selectManager.where(relation.get("name").doesNotMatchAll(new Object[]{"%chunky%", "%bacon%"}));

                assertEquals("SELECT \"users\".\"id\" FROM \"users\" WHERE (\"users\".\"name\" NOT LIKE '%chunky%' AND \"users\".\"name\" NOT LIKE '%bacon%')", selectManager.toSql());
            }
        }

        public static class DoesNotMatchAny extends Base {
            public void testDoesNotMatchAny() {
                ArelTable relation = new ArelTable("users");

                assertSame(ArelNodeGrouping.class, relation.get("name").doesNotMatchAny("%bacon%").getClass());
            }

            public void testDoesNotMatchAnyToSql() {
                ArelTable relation = new ArelTable("users");

                ArelSelectManager selectManager = relation.project(relation.get("id"));
                selectManager.where(relation.get("name").doesNotMatchAny(new Object[]{"%chunky%", "%bacon%"}));

                assertEquals("SELECT \"users\".\"id\" FROM \"users\" WHERE (\"users\".\"name\" NOT LIKE '%chunky%' OR \"users\".\"name\" NOT LIKE '%bacon%')", selectManager.toSql());
            }
        }

        public static class Eq extends Base {
            public void testEq() {
                ArelAttribute attribute = new ArelAttribute(null, null);

                Object equality = attribute.eq(1);

                assertSame(ArelNodeEquality.class, equality.getClass());
                assertEquals(attribute, ((ArelNodeEquality) equality).left());
                assertEquals(ArelNodes.buildQuoted(1, attribute), ((ArelNodeEquality) equality).right());
            }

            public void testEqToSql() {
                ArelTable relation = new ArelTable("users");

                ArelSelectManager selectManager = relation.project(relation.get("id"));
                selectManager.where(relation.get("id").eq(10));

                assertEquals("SELECT \"users\".\"id\" FROM \"users\" WHERE \"users\".\"id\" = 10", selectManager.toSql());
            }

            public void testEqWithNull() {
                ArelTable relation = new ArelTable("users");

                ArelSelectManager selectManager = relation.project(relation.get("id"));
                selectManager.where(relation.get("id").eq(null));

                assertEquals("SELECT \"users\".\"id\" FROM \"users\" WHERE \"users\".\"id\" IS NULL", selectManager.toSql());
            }
        }

        public static class EqAll extends Base {
            public void testEqAll() {
                ArelTable relation = new ArelTable("users");

                assertSame(ArelNodeGrouping.class, relation.get("id").eqAll(new Object[]{1, 2}).getClass());
            }

            public void testEqAllToSql() {
                ArelTable relation = new ArelTable("users");

                ArelSelectManager selectManager = relation.project(relation.get("id"));
                selectManager.where(relation.get("id").eqAll(new Object[]{1, 2}));

                assertEquals("SELECT \"users\".\"id\" FROM \"users\" WHERE (\"users\".\"id\" = 1 AND \"users\".\"id\" = 2)", selectManager.toSql());
            }
        }

        public static class EqAny extends Base {
            public void testEqAny() {
                ArelTable relation = new ArelTable("users");

                assertSame(ArelNodeGrouping.class, relation.get("id").eqAny(new Object[]{1, 2}).getClass());
            }

            public void testEqAnyToSql() {
                ArelTable relation = new ArelTable("users");

                ArelSelectManager selectManager = relation.project(relation.get("id"));
                selectManager.where(relation.get("id").eqAny(new Object[]{1, 2}));

                assertEquals("SELECT \"users\".\"id\" FROM \"users\" WHERE (\"users\".\"id\" = 1 OR \"users\".\"id\" = 2)", selectManager.toSql());
            }
        }

        public static class Gt extends Base {
            public void testGt() {
                ArelTable relation = new ArelTable("users");

                assertSame(ArelNodeGreaterThan.class, relation.get("id").gt(10).getClass());
            }

            public void testGtToSql() {
                ArelTable relation = new ArelTable("users");

                ArelSelectManager selectManager = relation.project(relation.get("id"));
                selectManager.where(relation.get("id").gt(10));

                assertEquals("SELECT \"users\".\"id\" FROM \"users\" WHERE \"users\".\"id\" > 10", selectManager.toSql());
            }

            public void testGtWithSubquery() {
                ArelTable users = new ArelTable("users");

                ArelSelectManager average = users.project(users.get("karma").average());
                ArelSelectManager selectManager = users.project(Arel.star()).where(users.get("karma").gt(average));

                assertEquals("SELECT * FROM \"users\" WHERE \"users\".\"karma\" > (SELECT AVG(\"users\".\"karma\") FROM \"users\")", selectManager.toSql());
            }

            public void testGtWithVariousDataTypes() {
                ArelTable relation = new ArelTable("users");

                ArelSelectManager selectManager = relation.project(relation.get("id"));
                selectManager.where(relation.get("name").gt("fake_name"));

                assertEquals("SELECT \"users\".\"id\" FROM \"users\" WHERE \"users\".\"name\" > 'fake_name'", selectManager.toSql());

                Date currentTime = new Date();
                selectManager.where(relation.get("created_at").gt(currentTime));

                assertEquals(String.format("SELECT \"users\".\"id\" FROM \"users\" WHERE \"users\".\"name\" > 'fake_name' AND \"users\".\"created_at\" > %s", ArelTable.engine.connection.quote(currentTime)), selectManager.toSql());
            }
        }

        public static class GtAll extends Base {
            public void testGtAll() {
                ArelTable relation = new ArelTable("users");

                assertSame(ArelNodeGrouping.class, relation.get("id").gtAll(new Object[]{1, 2}).getClass());
            }

            public void testGtAllToSql() {
                ArelTable relation = new ArelTable("users");

                ArelSelectManager selectManager = relation.project(relation.get("id"));
                selectManager.where(relation.get("id").gtAll(new Object[]{1, 2}));

                assertEquals("SELECT \"users\".\"id\" FROM \"users\" WHERE (\"users\".\"id\" > 1 AND \"users\".\"id\" > 2)", selectManager.toSql());
            }
        }

        public static class GtAny extends Base {
            public void testGtAny() {
                ArelTable relation = new ArelTable("users");

                assertSame(ArelNodeGrouping.class, relation.get("id").gtAny(new Object[]{1, 2}).getClass());
            }

            public void testGtAnyToSql() {
                ArelTable relation = new ArelTable("users");

                ArelSelectManager selectManager = relation.project(relation.get("id"));
                selectManager.where(relation.get("id").gtAny(new Object[]{1, 2}));

                assertEquals("SELECT \"users\".\"id\" FROM \"users\" WHERE (\"users\".\"id\" > 1 OR \"users\".\"id\" > 2)", selectManager.toSql());
            }
        }

        public static class GtEq extends Base {
            public void testGtEq() {
                ArelTable relation = new ArelTable("users");

                assertSame(ArelNodeGreaterThanOrEqual.class, relation.get("id").gteq(10).getClass());
            }

            public void testGtEqToSql() {
                ArelTable relation = new ArelTable("users");

                ArelSelectManager selectManager = relation.project(relation.get("id"));
                selectManager.where(relation.get("id").gteq(10));

                assertEquals("SELECT \"users\".\"id\" FROM \"users\" WHERE \"users\".\"id\" >= 10", selectManager.toSql());
            }

            public void testGtEqWithVariousDataTypes() {
                ArelTable relation = new ArelTable("users");

                ArelSelectManager selectManager = relation.project(relation.get("id"));
                selectManager.where(relation.get("name").gteq("fake_name"));

                assertEquals("SELECT \"users\".\"id\" FROM \"users\" WHERE \"users\".\"name\" >= 'fake_name'", selectManager.toSql());

                Date currentTime = new Date();
                selectManager.where(relation.get("created_at").gteq(currentTime));

                assertEquals(String.format("SELECT \"users\".\"id\" FROM \"users\" WHERE \"users\".\"name\" >= 'fake_name' AND \"users\".\"created_at\" >= %s", ArelTable.engine.connection.quote(currentTime)), selectManager.toSql());
            }
        }

        public static class GtEqAll extends Base {
            public void testGtEqAll() {
                ArelTable relation = new ArelTable("users");

                assertSame(ArelNodeGrouping.class, relation.get("id").gteqAll(new Object[]{1, 2}).getClass());
            }

            public void testGtEqAllToSql() {
                ArelTable relation = new ArelTable("users");

                ArelSelectManager selectManager = relation.project(relation.get("id"));
                selectManager.where(relation.get("id").gteqAll(new Object[]{1, 2}));

                assertEquals("SELECT \"users\".\"id\" FROM \"users\" WHERE (\"users\".\"id\" >= 1 AND \"users\".\"id\" >= 2)", selectManager.toSql());
            }
        }

        public static class GtEqAny extends Base {
            public void testGtEqAny() {
                ArelTable relation = new ArelTable("users");

                assertSame(ArelNodeGrouping.class, relation.get("id").gteqAny(new Object[]{1, 2}).getClass());
            }

            public void testGtEqAnyToSql() {
                ArelTable relation = new ArelTable("users");

                ArelSelectManager selectManager = relation.project(relation.get("id"));
                selectManager.where(relation.get("id").gteqAny(new Object[]{1, 2}));

                assertEquals("SELECT \"users\".\"id\" FROM \"users\" WHERE (\"users\".\"id\" >= 1 OR \"users\".\"id\" >= 2)", selectManager.toSql());
            }
        }

        public static class In extends Base {
            public void testInWithList() {
                ArelAttribute attribute = new ArelAttribute(null, null);
                ArelNode node = attribute.in(new Object[]{1, 2, 3});

                assertEquals(new ArelNodeIn(attribute, new Object[]{
                        new ArelNodeCasted(1, attribute),
                        new ArelNodeCasted(2, attribute),
                        new ArelNodeCasted(3, attribute)
                }), node);
            }

            public void testInWithRandomObject() {
                ArelAttribute attribute = new ArelAttribute(null, null);

                Object randomObject = new Object();

                ArelNode node = attribute.in(randomObject);

                assertEquals(new ArelNodeIn(attribute, new ArelNodeCasted(randomObject, attribute)), node);
            }

            public void testInWithSubquery() {
                ArelTable relation = new ArelTable("users");

                ArelSelectManager selectManager = relation.project(relation.get("id"));
                selectManager.where(relation.get("name").doesNotMatchAll(new Object[]{"%chunky%", "%bacon%"}));

                ArelAttribute attribute = new ArelAttribute(null, null);
                ArelNode node = attribute.in(selectManager);

                assertEquals(new ArelNodeIn(attribute, selectManager.ast()), node);
            }

            public void testInToSql() {
                ArelTable relation = new ArelTable("users");

                ArelSelectManager selectManager = relation.project(relation.get("id"));
                selectManager.where(relation.get("id").in(new Object[]{1, 2, 3}));

                assertEquals("SELECT \"users\".\"id\" FROM \"users\" WHERE \"users\".\"id\" IN (1, 2, 3)", selectManager.toSql());
            }
        }

        public static class InAll extends Base {
            public void testInAll() {
                ArelTable relation = new ArelTable("users");

                assertSame(ArelNodeGrouping.class, relation.get("id").inAll(new Object[]{1, 2}).getClass());
            }

            public void testInAllToSql() {
                ArelTable relation = new ArelTable("users");

                ArelSelectManager selectManager = relation.project(relation.get("id"));
                selectManager.where(relation.get("id").inAll((Object[]) new Object[][]{{1, 2}, {3, 4}}));

                assertEquals("SELECT \"users\".\"id\" FROM \"users\" WHERE (\"users\".\"id\" IN (1, 2) AND \"users\".\"id\" IN (3, 4))", selectManager.toSql());
            }
        }

        public static class InAny extends Base {
            public void testInAny() {
                ArelTable relation = new ArelTable("users");

                assertSame(ArelNodeGrouping.class, relation.get("id").inAny(new Object[]{1, 2}).getClass());
            }

            public void testInAnyToSql() {
                ArelTable relation = new ArelTable("users");

                ArelSelectManager selectManager = relation.project(relation.get("id"));
                selectManager.where(relation.get("id").inAny((Object[]) new Object[][]{{1, 2}, {3, 4}}));

                assertEquals("SELECT \"users\".\"id\" FROM \"users\" WHERE (\"users\".\"id\" IN (1, 2) OR \"users\".\"id\" IN (3, 4))", selectManager.toSql());
            }
        }

        public static class Lt extends Base {
            public void testLt() {
                ArelTable relation = new ArelTable("users");

                assertSame(ArelNodeLessThan.class, relation.get("id").lt(10).getClass());
            }

            public void testLtToSql() {
                ArelTable relation = new ArelTable("users");

                ArelSelectManager selectManager = relation.project(relation.get("id"));
                selectManager.where(relation.get("id").lt(10));

                assertEquals("SELECT \"users\".\"id\" FROM \"users\" WHERE \"users\".\"id\" < 10", selectManager.toSql());
            }

            public void testLtWithVariousDataTypes() {
                ArelTable relation = new ArelTable("users");

                ArelSelectManager selectManager = relation.project(relation.get("id"));
                selectManager.where(relation.get("name").lt("fake_name"));

                assertEquals("SELECT \"users\".\"id\" FROM \"users\" WHERE \"users\".\"name\" < 'fake_name'", selectManager.toSql());

                Date currentTime = new Date();
                selectManager.where(relation.get("created_at").lt(currentTime));

                assertEquals(String.format("SELECT \"users\".\"id\" FROM \"users\" WHERE \"users\".\"name\" < 'fake_name' AND \"users\".\"created_at\" < %s", ArelTable.engine.connection.quote(currentTime)), selectManager.toSql());
            }
        }

        public static class LtAll extends Base {
            public void testLtAll() {
                ArelTable relation = new ArelTable("users");

                assertSame(ArelNodeGrouping.class, relation.get("id").ltAll(new Object[]{1, 2}).getClass());
            }

            public void testLtAllToSql() {
                ArelTable relation = new ArelTable("users");

                ArelSelectManager selectManager = relation.project(relation.get("id"));
                selectManager.where(relation.get("id").ltAll(new Object[]{1, 2}));

                assertEquals("SELECT \"users\".\"id\" FROM \"users\" WHERE (\"users\".\"id\" < 1 AND \"users\".\"id\" < 2)", selectManager.toSql());
            }
        }

        public static class LtAny extends Base {
            public void testLtAny() {
                ArelTable relation = new ArelTable("users");

                assertSame(ArelNodeGrouping.class, relation.get("id").ltAny(new Object[]{1, 2}).getClass());
            }

            public void testLtAnyToSql() {
                ArelTable relation = new ArelTable("users");

                ArelSelectManager selectManager = relation.project(relation.get("id"));
                selectManager.where(relation.get("id").ltAny(new Object[]{1, 2}));

                assertEquals("SELECT \"users\".\"id\" FROM \"users\" WHERE (\"users\".\"id\" < 1 OR \"users\".\"id\" < 2)", selectManager.toSql());
            }
        }

        public static class LtEq extends Base {
            public void testLtEq() {
                ArelTable relation = new ArelTable("users");

                assertSame(ArelNodeLessThanOrEqual.class, relation.get("id").lteq(10).getClass());
            }

            public void testLtEqToSql() {
                ArelTable relation = new ArelTable("users");

                ArelSelectManager selectManager = relation.project(relation.get("id"));
                selectManager.where(relation.get("id").lteq(10));

                assertEquals("SELECT \"users\".\"id\" FROM \"users\" WHERE \"users\".\"id\" <= 10", selectManager.toSql());
            }

            public void testLtEqWithVariousDataTypes() {
                ArelTable relation = new ArelTable("users");

                ArelSelectManager selectManager = relation.project(relation.get("id"));
                selectManager.where(relation.get("name").lteq("fake_name"));

                assertEquals("SELECT \"users\".\"id\" FROM \"users\" WHERE \"users\".\"name\" <= 'fake_name'", selectManager.toSql());

                Date currentTime = new Date();
                selectManager.where(relation.get("created_at").lteq(currentTime));

                assertEquals(String.format("SELECT \"users\".\"id\" FROM \"users\" WHERE \"users\".\"name\" <= 'fake_name' AND \"users\".\"created_at\" <= %s", ArelTable.engine.connection.quote(currentTime)), selectManager.toSql());
            }
        }

        public static class LtEqAll extends Base {
            public void testLtEqAll() {
                ArelTable relation = new ArelTable("users");

                assertSame(ArelNodeGrouping.class, relation.get("id").lteqAll(new Object[]{1, 2}).getClass());
            }

            public void testLtEqAllToSql() {
                ArelTable relation = new ArelTable("users");

                ArelSelectManager selectManager = relation.project(relation.get("id"));
                selectManager.where(relation.get("id").lteqAll(new Object[]{1, 2}));

                assertEquals("SELECT \"users\".\"id\" FROM \"users\" WHERE (\"users\".\"id\" <= 1 AND \"users\".\"id\" <= 2)", selectManager.toSql());
            }
        }

        public static class LtEqAny extends Base {
            public void testLtEqAny() {
                ArelTable relation = new ArelTable("users");

                assertSame(ArelNodeGrouping.class, relation.get("id").lteqAny(new Object[]{1, 2}).getClass());
            }

            public void testLtEqAnyToSql() {
                ArelTable relation = new ArelTable("users");

                ArelSelectManager selectManager = relation.project(relation.get("id"));
                selectManager.where(relation.get("id").lteqAny(new Object[]{1, 2}));

                assertEquals("SELECT \"users\".\"id\" FROM \"users\" WHERE (\"users\".\"id\" <= 1 OR \"users\".\"id\" <= 2)", selectManager.toSql());
            }
        }

        public static class Matches extends Base {
            public void testMatches() {
                ArelTable relation = new ArelTable("users");

                assertSame(ArelNodeMatches.class, relation.get("name").matches("%bacon%").getClass());
            }

            public void testMatchesToSql() {
                ArelTable relation = new ArelTable("users");

                ArelSelectManager selectManager = relation.project(relation.get("id"));
                selectManager.where(relation.get("name").matches("%bacon%"));

                assertEquals("SELECT \"users\".\"id\" FROM \"users\" WHERE \"users\".\"name\" LIKE '%bacon%'", selectManager.toSql());
            }
        }

        public static class MatchesAll extends Base {
            public void testMatchesAll() {
                ArelTable relation = new ArelTable("users");

                assertSame(ArelNodeGrouping.class, relation.get("name").matchesAll("%bacon%").getClass());
            }

            public void testMatchesAllToSql() {
                ArelTable relation = new ArelTable("users");

                ArelSelectManager selectManager = relation.project(relation.get("id"));
                selectManager.where(relation.get("name").matchesAll(new Object[]{"%chunky%", "%bacon%"}));

                assertEquals("SELECT \"users\".\"id\" FROM \"users\" WHERE (\"users\".\"name\" LIKE '%chunky%' AND \"users\".\"name\" LIKE '%bacon%')", selectManager.toSql());
            }
        }

        public static class MatchesAny extends Base {
            public void testMatchesAny() {
                ArelTable relation = new ArelTable("users");

                assertSame(ArelNodeGrouping.class, relation.get("name").matchesAny("%bacon%").getClass());
            }

            public void testMatchesAnyToSql() {
                ArelTable relation = new ArelTable("users");

                ArelSelectManager selectManager = relation.project(relation.get("id"));
                selectManager.where(relation.get("name").matchesAny(new Object[]{"%chunky%", "%bacon%"}));

                assertEquals("SELECT \"users\".\"id\" FROM \"users\" WHERE (\"users\".\"name\" LIKE '%chunky%' OR \"users\".\"name\" LIKE '%bacon%')", selectManager.toSql());
            }
        }

        public static class Maximum extends Base {
            public void testMaximum() {
                ArelTable relation = new ArelTable("users");

                assertSame(ArelNodeMax.class, relation.get("id").maximum().getClass());
            }

            public void testMaximumToSql() {
                ArelTable relation = new ArelTable("users");

                ArelSelectManager selectManager = relation.project(relation.get("id").maximum());

                assertEquals("SELECT MAX(\"users\".\"id\") FROM \"users\"", selectManager.toSql());
            }
        }

        public static class Minimum extends Base {
            public void testMinimum() {
                ArelTable relation = new ArelTable("users");

                assertSame(ArelNodeMin.class, relation.get("id").minimum().getClass());
            }

            public void testMinimumToSql() {
                ArelTable relation = new ArelTable("users");

                ArelSelectManager selectManager = relation.project(relation.get("id").minimum());

                assertEquals("SELECT MIN(\"users\".\"id\") FROM \"users\"", selectManager.toSql());
            }
        }

        public static class NotBetween extends Base {
            public void testNotBetween() {
                ArelAttribute attribute = new ArelAttribute(null, null);
                ArelNode node = attribute.notBetween(1, 3);

                assertEquals(new ArelNodeGrouping(new ArelNodeOr(
                        new ArelNodeLessThan(attribute, new ArelNodeCasted(1, attribute)),
                        new ArelNodeGreaterThan(attribute, new ArelNodeCasted(3, attribute))
                )), node);
            }

            public void testNotBetweenWithExclusiveRange() {
                ArelAttribute attribute = new ArelAttribute(null, null);
                ArelNode node = attribute.notBetween(0, 3, false);

                assertEquals(new ArelNodeGrouping(new ArelNodeOr(
                        new ArelNodeLessThan(attribute, new ArelNodeCasted(0, attribute)),
                        new ArelNodeGreaterThanOrEqual(attribute, new ArelNodeCasted(3, attribute))
                )), node);
            }

            public void testNotBetweenWithInfiniteRange() {
                ArelAttribute attribute = new ArelAttribute(null, null);
                ArelNode node = attribute.notBetween(Float.NEGATIVE_INFINITY, Float.POSITIVE_INFINITY);

                assertEquals(new ArelNodeIn(attribute, new ArrayList<>()), node);
            }

            public void testNotBetweenWithRangeEndingWithPositiveInfinity() {
                ArelAttribute attribute = new ArelAttribute(null, null);
                ArelNode node = attribute.notBetween(0, Float.POSITIVE_INFINITY);

                assertEquals(new ArelNodeLessThan(attribute, new ArelNodeCasted(0, attribute)), node);
            }

            public void testNotBetweenWithRangeStartingWithNegativeInfinity() {
                ArelAttribute attribute = new ArelAttribute(null, null);
                ArelNode node = attribute.notBetween(Float.NEGATIVE_INFINITY, 3);

                assertEquals(new ArelNodeGreaterThan(attribute, new ArelNodeCasted(3, attribute)), node);
            }

            public void testNotBetweenWithRangeStartingWithNegativeInfinityExclusive() {
                ArelAttribute attribute = new ArelAttribute(null, null);
                ArelNode node = attribute.notBetween(Float.NEGATIVE_INFINITY, 3, false);

                assertEquals(new ArelNodeGreaterThanOrEqual(attribute, new ArelNodeCasted(3, attribute)), node);
            }
        }

        public static class NotEq extends Base {
            public void testNotEq() {
                ArelRelation relation = new ArelTable("users");

                assertSame(ArelNodeNotEqual.class, relation.get("id").notEq(10).getClass());
            }

            public void testNotEqToSql() {
                ArelTable relation = new ArelTable("users");

                ArelSelectManager selectManager = relation.project(relation.get("id"));
                selectManager.where(relation.get("id").notEq(10));

                assertEquals("SELECT \"users\".\"id\" FROM \"users\" WHERE \"users\".\"id\" != 10", selectManager.toSql());
            }

            public void testNotEqWithNull() {
                ArelTable relation = new ArelTable("users");

                ArelSelectManager selectManager = relation.project(relation.get("id"));
                selectManager.where(relation.get("id").notEq(null));

                assertEquals("SELECT \"users\".\"id\" FROM \"users\" WHERE \"users\".\"id\" IS NOT NULL", selectManager.toSql());
            }
        }

        public static class NotEqAll extends Base {
            public void testNotEqAllGrouping() {
                ArelTable relation = new ArelTable("users");

                assertSame(ArelNodeGrouping.class, relation.get("id").notEqAll(new Object[]{1, 2}).getClass());
            }

            public void testNotEqAllToSql() {
                ArelTable relation = new ArelTable("users");

                ArelSelectManager selectManager = relation.project(relation.get("id"));
                selectManager.where(relation.get("id").notEqAll(new Object[]{1, 2}));

                assertEquals("SELECT \"users\".\"id\" FROM \"users\" WHERE (\"users\".\"id\" != 1 AND \"users\".\"id\" != 2)", selectManager.toSql());
            }
        }

        public static class NotEqAny extends Base {
            public void testNotEqAnyGrouping() {
                ArelTable relation = new ArelTable("users");

                assertSame(ArelNodeGrouping.class, relation.get("id").notEqAny(new Object[]{1, 2}).getClass());
            }

            public void testNotEqAnyToSql() {
                ArelTable relation = new ArelTable("users");

                ArelSelectManager selectManager = relation.project(relation.get("id"));
                selectManager.where(relation.get("id").notEqAny(new Object[]{1, 2}));

                assertEquals("SELECT \"users\".\"id\" FROM \"users\" WHERE (\"users\".\"id\" != 1 OR \"users\".\"id\" != 2)", selectManager.toSql());
            }
        }

        public static class NotIn extends Base {
            public void testNotInWithList() {
                ArelAttribute attribute = new ArelAttribute(null, null);
                ArelNode node = attribute.notIn(new Object[]{1, 2, 3});

                assertEquals(new ArelNodeNotIn(attribute, new Object[]{
                        new ArelNodeCasted(1, attribute),
                        new ArelNodeCasted(2, attribute),
                        new ArelNodeCasted(3, attribute)
                }), node);
            }

            public void testNotInWithRandomObject() {
                ArelAttribute attribute = new ArelAttribute(null, null);

                Object randomObject = new Object();

                ArelNode node = attribute.notIn(randomObject);

                assertEquals(new ArelNodeNotIn(attribute, new ArelNodeCasted(randomObject, attribute)), node);
            }

            public void testNotInWithSubquery() {
                ArelTable relation = new ArelTable("users");

                ArelSelectManager selectManager = relation.project(relation.get("id"));
                selectManager.where(relation.get("name").doesNotMatchAll(new Object[]{"%chunky%", "%bacon%"}));

                ArelAttribute attribute = new ArelAttribute(null, null);
                ArelNode node = attribute.notIn(selectManager);

                assertEquals(new ArelNodeNotIn(attribute, selectManager.ast()), node);
            }

            public void testNotInToSql() {
                ArelTable relation = new ArelTable("users");

                ArelSelectManager selectManager = relation.project(relation.get("id"));
                selectManager.where(relation.get("id").notIn(new Object[]{1, 2, 3}));

                assertEquals("SELECT \"users\".\"id\" FROM \"users\" WHERE \"users\".\"id\" NOT IN (1, 2, 3)", selectManager.toSql());
            }
        }

        public static class NotInAll extends Base {
            public void testNotInAll() {
                ArelTable relation = new ArelTable("users");

                assertSame(ArelNodeGrouping.class, relation.get("id").notInAll(new Object[]{1, 2}).getClass());
            }

            public void testNotInAllToSql() {
                ArelTable relation = new ArelTable("users");

                ArelSelectManager selectManager = relation.project(relation.get("id"));
                selectManager.where(relation.get("id").notInAll((Object[]) new Object[][]{{1, 2}, {3, 4}}));

                assertEquals("SELECT \"users\".\"id\" FROM \"users\" WHERE (\"users\".\"id\" NOT IN (1, 2) AND \"users\".\"id\" NOT IN (3, 4))", selectManager.toSql());
            }
        }

        public static class NotInAny extends Base {
            public void testNotInAny() {
                ArelTable relation = new ArelTable("users");

                assertSame(ArelNodeGrouping.class, relation.get("id").notInAny(new Object[]{1, 2}).getClass());
            }

            public void testNotInAnyToSql() {
                ArelTable relation = new ArelTable("users");

                ArelSelectManager selectManager = relation.project(relation.get("id"));
                selectManager.where(relation.get("id").notInAny((Object[]) new Object[][]{{1, 2}, {3, 4}}));

                assertEquals("SELECT \"users\".\"id\" FROM \"users\" WHERE (\"users\".\"id\" NOT IN (1, 2) OR \"users\".\"id\" NOT IN (3, 4))", selectManager.toSql());
            }
        }

        public static class Sum extends Base {
            public void testSum() {
                ArelTable relation = new ArelTable("users");

                assertSame(ArelNodeSum.class, relation.get("id").sum().getClass());
            }

            public void testSumToSql() {
                ArelTable relation = new ArelTable("users");

                ArelSelectManager selectManager = relation.project(relation.get("id").sum());

                assertEquals("SELECT SUM(\"users\".\"id\") FROM \"users\"", selectManager.toSql());
            }
        }
    }

    public static class Equality {
        public static class ToSql extends Base {
            public void testToSql() {
                ArelTable table = new ArelTable("users");

                ArelNode condition = table.get("id").eq(1);

                assertEquals("\"users\".\"id\" = 1", condition.toSql());
            }
        }
    }

    public static class TypeCasting extends Base {
        public void testTypeCasting() {
            ArelTable table = new ArelTable("foo");
            ArelNode condition = table.get("id").eq("1");

            assertFalse(table.isAbleToTypeCast());
            assertEquals("\"foo\".\"id\" = '1'", condition.toSql());
        }

        public void testTypeCastingWitbExplicitCaster() {
            ArelTypeCaster fakeCaster = new ArelTypeCaster() {
                @Override
                public Object typeCastForDatabase(Object attributeName, Object value) {
                    if (Objects.deepEquals(attributeName, "id")) {
                        return Integer.valueOf(String.valueOf(value));
                    } else {
                        return value;
                    }
                }
            };

            ArelTable table = new ArelTable("foo", fakeCaster);
            ArelNode condition = table.get("id").eq("1").and(table.get("other_id").eq("2"));

            assertTrue(table.isAbleToTypeCast());
            assertEquals("\"foo\".\"id\" = 1 AND \"foo\".\"other_id\" = '2'", condition.toSql());
        }

        public void testTypeCastingWithFallbackCaster() {
            ArelTable table = new ArelTable("users");
            ArelNode condition = table.get("id").eq("1");

            assertFalse(table.isAbleToTypeCast());
            assertEquals("\"users\".\"id\" = 1", condition.toSql());
        }
    }
}

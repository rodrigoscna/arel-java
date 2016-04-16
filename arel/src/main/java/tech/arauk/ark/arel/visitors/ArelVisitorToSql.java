package tech.arauk.ark.arel.visitors;

import tech.arauk.ark.arel.ArelSelectManager;
import tech.arauk.ark.arel.ArelTable;
import tech.arauk.ark.arel.ArelUtils;
import tech.arauk.ark.arel.attributes.ArelAttribute;
import tech.arauk.ark.arel.collectors.ArelCollector;
import tech.arauk.ark.arel.connection.ArelConnection;
import tech.arauk.ark.arel.connection.SchemaCache;
import tech.arauk.ark.arel.nodes.*;

import java.util.*;

public class ArelVisitorToSql extends ArelVisitor {
    public static final String AND = " AND ";
    public static final String AS = " AS ";
    public static final String ASC = " ASC";
    public static final String BETWEEN = " BETWEEN ";
    public static final String COMMA = ", ";
    public static final String COUNT = "COUNT";
    public static final String CURRENT_ROW = "CURRENT ROW";
    public static final String DELETE_FROM = "DELETE FROM ";
    public static final String DESC = " DESC";
    public static final String DISTINCT = "DISTINCT";
    public static final String EQUALS = " = ";
    public static final String ESCAPE = " ESCAPE ";
    public static final String EXCEPT = " EXCEPT ";
    public static final String EXISTS = "EXISTS ";
    public static final String EXTRACT = "EXTRACT";
    public static final String FOLLOWING = " FOLLOWING";
    public static final String FROM = " FROM ";
    public static final String FULL_OUTER_JOIN = "FULL OUTER JOIN ";
    public static final String GREATER_THAN = " > ";
    public static final String GREATER_THAN_OR_EQUAL = " >= ";
    public static final String GROUP_BY = " GROUP BY ";
    public static final String GROUPING_CLOSE = ")";
    public static final String GROUPING_OPEN = "(";
    public static final String IN = " IN ";
    public static final String INNER_JOIN = "INNER JOIN ";
    public static final String INSERT_INTO = "INSERT INTO ";
    public static final String INTERSECT = " INTERSECT ";
    public static final String IS_NOT_NULL = " IS NOT NULL";
    public static final String IS_NULL = " IS NULL";
    public static final String LEFT_OUTER_JOIN = "LEFT OUTER JOIN ";
    public static final String LESS_THAN = " < ";
    public static final String LESS_THAN_OR_EQUAL = " <= ";
    public static final String LIKE = " LIKE ";
    public static final String LIMIT = "LIMIT ";
    public static final String MAX = "MAX";
    public static final String MIN = "MIN";
    public static final String NOT_EQUALS = " != ";
    public static final String NOT_IN = " NOT IN ";
    public static final String NOT_LIKE = " NOT LIKE ";
    public static final String OFFSET = "OFFSET ";
    public static final String ON = "ON ";
    public static final String OR = " OR ";
    public static final String ORDER_BY = "ORDER BY ";
    public static final String OVER = " OVER ";
    public static final String PARTITION_BY = "PARTITION BY ";
    public static final String PRECEDING = " PRECEDING";
    public static final String RANGE = "RANGE";
    public static final String RIGHT_OUTER_JOIN = "RIGHT OUTER JOIN ";
    public static final String ROWS = "ROWS";
    public static final String SET = " SET ";
    public static final String SPACE = " ";
    public static final String SUM = "SUM";
    public static final String UNBOUNDED = "UNBOUNDED";
    public static final String UNION = " UNION ";
    public static final String UNION_ALL = " UNION ALL ";
    public static final String UPDATE = "UPDATE ";
    public static final String VALUES = "VALUES ";
    public static final String WHERE = "WHERE ";
    public static final String WINDOW = " WINDOW ";
    public static final String WITH = "WITH ";

    public static final String WITH_RECURSIVE = "WITH RECURSIVE ";
    private static final String ONE_EQUALS_ONE = "1=1";
    private static final String ONE_EQUALS_ZERO = "1=0";

    public ArelVisitorToSql(ArelConnection arelConnection) {
        super(arelConnection);
    }

    public ArelCollector injectJoin(List<Object> list, ArelCollector collector, String joinStr) {
        int len = list.size() - 1;

        for (int i = 0; i < list.size(); i++) {
            collector = visit(list.get(i), collector);

            if (i != len) {
                collector.append(joinStr);
            }
        }

        return collector;
    }

    public ArelCollector injectJoin(Object[] list, ArelCollector collector, String joinStr) {
        int len = list.length - 1;

        for (int i = 0; i < list.length; i++) {
            collector = visit(list[i], collector);

            if (i != len) {
                collector.append(joinStr);
            }
        }

        return collector;
    }

    public ArelCollector visitArelAttribute(ArelAttribute attribute, ArelCollector collector) {
        Object joinName = attribute.relation.tableAlias();
        if (joinName == null) {
            joinName = attribute.relation.tableName();
        }

        collector.append(quoteTableName(joinName) + "." + quoteColumnName(attribute.name));

        return collector;
    }

    public ArelCollector visitArelNodeAnd(ArelNodeAnd and, ArelCollector collector) {
        collector = injectJoin(and.children, collector, AND);

        return collector;
    }

    public ArelCollector visitArelNodeAs(ArelNodeAs as, ArelCollector collector) {
        collector = visit(as.left(), collector);
        collector.append(AS);
        collector = visit(as.right(), collector);

        return collector;
    }

    public ArelCollector visitArelNodeAscending(ArelNodeAscending ascending, ArelCollector collector) {
        collector = visit(ascending.expr(), collector);
        collector.append(ASC);

        return collector;
    }

    public ArelCollector visitArelNodeAssignment(ArelNodeAssignment assignment, ArelCollector collector) {
        if (assignment.right() instanceof ArelNodeUnqualifiedColumn || assignment.right() instanceof ArelAttribute || assignment.right() instanceof ArelNodeBindParam) {
            collector = visit(assignment.left(), collector);
            collector.append(EQUALS);
            collector = visit(assignment.right(), collector);
        } else {
            collector = visit(assignment.left(), collector);
            collector.append(EQUALS);
            collector.append(quote(assignment.right()));
        }

        return collector;
    }

    public ArelCollector visitArelNodeAvg(ArelNodeAvg avg, ArelCollector collector) {
        collector = aggregate("AVG", avg, collector);

        return collector;
    }

    public ArelCollector visitArelNodeBetween(ArelNodeBetween between, ArelCollector collector) {
        collector = visit(between.left(), collector);
        collector.append(BETWEEN);
        collector = visit(between.right(), collector);

        return collector;
    }

    public ArelCollector visitArelNodeBin(ArelNodeBin bin, ArelCollector collector) {
        collector = visit(bin.expr(), collector);

        return collector;
    }

    public ArelCollector visitArelNodeBindParam(ArelNodeBindParam bindParam, ArelCollector collector) {
        collector.addBind(bindParam, new ArelCollector.Bindable() {
            @Override
            public String bind(int bindIndex) {
                return "?";
            }
        });

        return collector;
    }

    public ArelCollector visitArelNodeCasted(ArelNodeCasted casted, ArelCollector collector) {
        collector.append(quoted(casted.val, casted.attribute));

        return collector;
    }

    public ArelCollector visitArelNodeCount(ArelNodeCount count, ArelCollector collector) {
        collector = aggregate(COUNT, count, collector);

        return collector;
    }

    public ArelCollector visitArelNodeCurrentRow(ArelNodeCurrentRow currentRow, ArelCollector collector) {
        collector.append(CURRENT_ROW);

        return collector;
    }

    public ArelCollector visitArelNodeDeleteStatement(ArelNodeDeleteStatement deleteStatement, ArelCollector collector) {
        collector.append(DELETE_FROM);
        collector = visit(deleteStatement.relation(), collector);
        if (!((List<Object>) deleteStatement.wheres()).isEmpty()) {
            collector.append(SPACE);
            collector.append(WHERE);
            collector = injectJoin(((List<Object>) deleteStatement.wheres()), collector, AND);
        }

        maybeVisit(deleteStatement.limit, collector);

        return collector;
    }

    public ArelCollector visitArelNodeDescending(ArelNodeDescending descending, ArelCollector collector) {
        collector = visit(descending.expr(), collector);
        collector.append(DESC);

        return collector;
    }

    public ArelCollector visitArelNodeDistinct(ArelNodeDistinct distinct, ArelCollector collector) {
        collector.append(DISTINCT);

        return collector;
    }

    public ArelCollector visitArelNodeDoesNotMatch(ArelNodeDoesNotMatch doesNotMatch, ArelCollector collector) {
        collector = visit(doesNotMatch.left(), collector);
        collector.append(NOT_LIKE);
        collector = visit(doesNotMatch.right(), collector);

        if (doesNotMatch.escape() != null) {
            collector.append(ESCAPE);
            collector = visit(doesNotMatch.escape(), collector);
        }

        return collector;
    }

    public ArelCollector visitArelNodeEquality(ArelNodeEquality equality, ArelCollector collector) {
        collector = visit(equality.left(), collector);

        if (Objects.equals(equality.right(), null)) {
            collector.append(IS_NULL);
        } else {
            collector.append(EQUALS);
            visit(equality.right(), collector);
        }

        return collector;
    }

    public ArelCollector visitArelNodeExcept(ArelNodeExcept except, ArelCollector collector) {
        collector.append(GROUPING_OPEN);
        collector = infixValue(except, collector, EXCEPT);
        collector.append(GROUPING_CLOSE);

        return collector;
    }

    public ArelCollector visitArelNodeExists(ArelNodeExists exists, ArelCollector collector) {
        collector.append(EXISTS);
        collector.append(GROUPING_OPEN);
        collector = visit(exists.expressions, collector);
        collector.append(GROUPING_CLOSE);

        if (exists.alias != null) {
            collector.append(AS);
            collector = visit(exists.alias, collector);
        }

        return collector;
    }

    public ArelCollector visitArelNodeExtract(ArelNodeExtract extract, ArelCollector collector) {
        collector.append(EXTRACT);
        collector.append(GROUPING_OPEN);
        collector.append(extract.field().toString().toUpperCase());
        collector.append(FROM);
        collector = visit(extract.expr(), collector);
        collector.append(GROUPING_CLOSE);

        return collector;
    }

    public ArelCollector visitArelNodeFollowing(ArelNodeFollowing following, ArelCollector collector) {
        if (following.expr() != null) {
            collector = visit(following.expr(), collector);
        } else {
            collector.append(UNBOUNDED);
        }

        collector.append(FOLLOWING);

        return collector;
    }

    public ArelCollector visitArelNodeFullOuterJoin(ArelNodeFullOuterJoin fullOuterJoin, ArelCollector collector) {
        collector.append(FULL_OUTER_JOIN);
        collector = visit(fullOuterJoin.left(), collector);
        collector.append(SPACE);
        collector = visit(fullOuterJoin.right(), collector);

        return collector;
    }

    public ArelCollector visitArelNodeGroup(ArelNodeGroup group, ArelCollector collector) {
        collector = visit(group.expr(), collector);

        return collector;
    }

    public ArelCollector visitArelNodeGrouping(ArelNodeGrouping grouping, ArelCollector collector) {
        if (grouping.expr() instanceof ArelNodeGrouping) {
            collector = visit(grouping.expr(), collector);
        } else {
            collector.append(GROUPING_OPEN);
            collector = visit(grouping.expr(), collector);
            collector.append(GROUPING_CLOSE);
        }

        return collector;
    }

    public ArelCollector visitArelNodeGreaterThan(ArelNodeGreaterThan greaterThan, ArelCollector collector) {
        collector = visit(greaterThan.left(), collector);
        collector.append(GREATER_THAN);
        collector = visit(greaterThan.right(), collector);

        return collector;
    }

    public ArelCollector visitArelNodeGreaterThanOrEqual(ArelNodeGreaterThanOrEqual greaterThanOrEqual, ArelCollector collector) {
        collector = visit(greaterThanOrEqual.left(), collector);
        collector.append(GREATER_THAN_OR_EQUAL);
        collector = visit(greaterThanOrEqual.right(), collector);

        return collector;
    }

    public ArelCollector visitArelNodeIn(ArelNodeIn in, ArelCollector collector) {
        if (in.right() instanceof List && ((List<Object>) in.right()).isEmpty()) {
            collector.append(ONE_EQUALS_ZERO);
        } else {
            collector = visit(in.left(), collector);
            collector.append(IN);
            collector.append(GROUPING_OPEN);
            collector = visit(in.right(), collector);
            collector.append(GROUPING_CLOSE);
        }

        return collector;
    }

    public ArelCollector visitArelNodeInnerJoin(ArelNodeInnerJoin innerJoin, ArelCollector collector) {
        collector.append(INNER_JOIN);
        collector = visit(innerJoin.left(), collector);

        if (innerJoin.right() != null) {
            collector.append(SPACE);
            collector = visit(innerJoin.right(), collector);
        }

        return collector;
    }

    public ArelCollector visitArelNodeInsertStatement(ArelNodeInsertStatement insertStatement, ArelCollector collector) {
        collector.append(INSERT_INTO);
        collector = visit(insertStatement.relation, collector);

        if (insertStatement.columns != null && !insertStatement.columns.isEmpty()) {
            List<String> quotedColumns = new ArrayList<>();
            for (Object column : insertStatement.columns) {
                if (column instanceof ArelAttribute) {
                    quotedColumns.add(quoteColumnName(((ArelAttribute) column).name));
                } else {
                    quotedColumns.add(quoteColumnName(column));
                }
            }
            collector.append(String.format(" (%s)", ArelUtils.join(quotedColumns, ", ")));
        }

        if (insertStatement.values != null) {
            return maybeVisit(insertStatement.values, collector);
        } else if (insertStatement.select != null) {
            return maybeVisit(insertStatement.select, collector);
        } else {
            return collector;
        }
    }

    public ArelCollector visitArelNodeIntersect(ArelNodeIntersect intersect, ArelCollector collector) {
        collector.append(GROUPING_OPEN);
        collector = infixValue(intersect, collector, INTERSECT);
        collector.append(GROUPING_CLOSE);

        return collector;
    }

    public ArelCollector visitArelNodeJoinSource(ArelNodeJoinSource joinSource, ArelCollector collector) {
        if (joinSource.left() != null) {
            collector = visit(joinSource.left(), collector);
        }

        if (joinSource.right() != null && !((List<Object>) joinSource.right()).isEmpty()) {
            if (joinSource.left() != null) {
                collector.append(SPACE);
            }
            collector = injectJoin(((List<Object>) joinSource.right()), collector, SPACE);
        }

        return collector;
    }

    public ArelCollector visitArelNodeLessThan(ArelNodeLessThan lessThan, ArelCollector collector) {
        collector = visit(lessThan.left(), collector);
        collector.append(LESS_THAN);
        collector = visit(lessThan.right(), collector);

        return collector;
    }

    public ArelCollector visitArelNodeLessThanOrEqual(ArelNodeLessThanOrEqual lessThanOrEqual, ArelCollector collector) {
        collector = visit(lessThanOrEqual.left(), collector);
        collector.append(LESS_THAN_OR_EQUAL);
        collector = visit(lessThanOrEqual.right(), collector);

        return collector;
    }

    public ArelCollector visitArelNodeLimit(ArelNodeLimit limit, ArelCollector collector) {
        collector.append(LIMIT);
        collector = visit(limit.expr(), collector);

        return collector;
    }

    public ArelCollector visitArelNodeLock(ArelNodeLock lock, ArelCollector collector) {
        collector = visit(lock.expr(), collector);

        return collector;
    }

    public ArelCollector visitArelNodeMatches(ArelNodeMatches matches, ArelCollector collector) {
        collector = visit(matches.left(), collector);
        collector.append(LIKE);
        collector = visit(matches.right(), collector);

        if (matches.escape() != null) {
            collector.append(ESCAPE);
            collector = visit(matches.escape(), collector);
        }

        return collector;
    }

    public ArelCollector visitArelNodeMax(ArelNodeMax max, ArelCollector collector) {
        collector = aggregate(MAX, max, collector);

        return collector;
    }

    public ArelCollector visitArelNodeMin(ArelNodeMin min, ArelCollector collector) {
        collector = aggregate(MIN, min, collector);

        return collector;
    }

    public ArelCollector visitArelNodeNamedWindow(ArelNodeNamedWindow namedWindow, ArelCollector collector) {
        collector.append(quoteColumnName(namedWindow.name));
        collector.append(AS);
        collector = visitArelNodeWindow(namedWindow, collector);

        return collector;
    }

    public ArelCollector visitArelNodeNotEqual(ArelNodeNotEqual notEqual, ArelCollector collector) {
        Object right = notEqual.right();

        collector = visit(notEqual.left(), collector);

        if (right instanceof ArelNodeCasted && ((ArelNodeCasted) right).val == null) {
            collector.append(IS_NOT_NULL);
        } else if (right instanceof ArelNodeQuoted && ((ArelNodeQuoted) right).expr() == null) {
            collector.append(IS_NOT_NULL);
        } else {
            collector.append(NOT_EQUALS);
            collector = visit(notEqual.right(), collector);
        }

        return collector;
    }

    public ArelCollector visitArelNodeNotIn(ArelNodeNotIn notIn, ArelCollector collector) {
        if (notIn.right() instanceof List && ((List<Object>) notIn.right()).isEmpty()) {
            collector.append(ONE_EQUALS_ONE);
        } else {
            collector = visit(notIn.left(), collector);
            collector.append(NOT_IN);
            collector.append(GROUPING_OPEN);
            collector = visit(notIn.right(), collector);
            collector.append(GROUPING_CLOSE);
        }

        return collector;
    }

    public ArelCollector visitArelNodeOffset(ArelNodeOffset offset, ArelCollector collector) {
        collector.append(OFFSET);

        if (offset.expr() != null) {
            collector = visit(offset.expr(), collector);
        }

        return collector;
    }

    public ArelCollector visitArelNodeOn(ArelNodeOn nodeOn, ArelCollector collector) {
        collector.append(ON);
        collector = visit(nodeOn.expr(), collector);

        return collector;
    }

    public ArelCollector visitArelNodeOr(ArelNodeOr nodeOr, ArelCollector collector) {
        collector = visit(nodeOr.left(), collector);
        collector.append(OR);
        collector = visit(nodeOr.right(), collector);

        return collector;
    }

    public ArelCollector visitArelNodeOuterJoin(ArelNodeOuterJoin outerJoin, ArelCollector collector) {
        collector.append(LEFT_OUTER_JOIN);
        collector = visit(outerJoin.left(), collector);
        collector.append(SPACE);
        collector = visit(outerJoin.right(), collector);

        return collector;
    }

    public ArelCollector visitArelNodeOver(ArelNodeOver over, ArelCollector collector) {
        Object right = over.right();

        if (right == null) {
            collector = visit(over.left(), collector);
            collector.append(OVER);
            collector.append(GROUPING_OPEN);
            collector.append(GROUPING_CLOSE);
        } else if (right instanceof ArelNodeSqlLiteral) {
            collector = infixValue(over, collector, OVER);
        } else if (right instanceof String) {
            collector = visit(over.left(), collector);
            collector.append(OVER);
            collector.append(quoteColumnName(right));
        } else {
            collector = infixValue(over, collector, OVER);
        }

        return collector;
    }

    public ArelCollector visitArelNodePreceding(ArelNodePreceding preceding, ArelCollector collector) {
        if (preceding.expr() != null) {
            collector = visit(preceding.expr(), collector);
        } else {
            collector.append(UNBOUNDED);
        }

        collector.append(PRECEDING);

        return collector;
    }

    public ArelCollector visitArelNodeQuoted(ArelNodeQuoted quoted, ArelCollector collector) {
        collector.append(quoted(quoted.expr(), null));

        return collector;
    }

    public ArelCollector visitArelNodeRange(ArelNodeRange range, ArelCollector collector) {
        if (range.expr() != null) {
            collector.append(RANGE);
            collector.append(SPACE);
            collector = visit(range.expr(), collector);
        } else {
            collector.append(RANGE);
        }

        return collector;
    }

    public ArelCollector visitArelNodeRightOuterJoin(ArelNodeRightOuterJoin rightOuterJoin, ArelCollector collector) {
        collector.append(RIGHT_OUTER_JOIN);
        collector = visit(rightOuterJoin.left(), collector);
        collector.append(SPACE);
        collector = visit(rightOuterJoin.right(), collector);

        return collector;
    }

    public ArelCollector visitArelNodeRows(ArelNodeRows rows, ArelCollector collector) {
        if (rows.expr() != null) {
            collector.append(ROWS);
            collector.append(SPACE);
            collector = visit(rows.expr(), collector);
        } else {
            collector.append(ROWS);
        }

        return collector;
    }

    public ArelCollector visitArelNodeSelectStatement(ArelNodeSelectStatement selectStatement, ArelCollector collector) {
        if (selectStatement.with != null) {
            collector = visit(selectStatement.with, collector);
            collector.append(SPACE);
        }

        for (ArelNodeSelectCore selectCore : selectStatement.cores) {
            collector = visitArelNodeSelectCore(selectCore, collector);
        }

        if (selectStatement.orders != null && selectStatement.orders.size() > 0) {
            collector.append(SPACE);
            collector.append(ORDER_BY);

            int len = selectStatement.orders.size() - 1;

            for (int i = 0; i < selectStatement.orders.size(); i++) {
                collector = visit(selectStatement.orders.get(i), collector);
                if (len != i) {
                    collector.append(COMMA);
                }
            }
        }

        collector = visitArelNodeSelectOptions(selectStatement, collector);

        return collector;
    }

    public ArelCollector visitArelNodeSelectCore(ArelNodeSelectCore selectCore, ArelCollector collector) {
        collector.append("SELECT");

        collector = maybeVisit(selectCore.top, collector);

        collector = maybeVisit(selectCore.setQuantifier, collector);

        if (selectCore.projections != null && selectCore.projections.size() > 0) {
            collector.append(SPACE);

            int len = selectCore.projections.size() - 1;

            for (int i = 0; i < selectCore.projections.size(); i++) {
                collector = visit(selectCore.projections.get(i), collector);
                if (len != i) {
                    collector.append(COMMA);
                }
            }
        }

        if (selectCore.source != null && !selectCore.source.isEmpty()) {
            collector.append(FROM);
            collector = visit(selectCore.source, collector);
        }

        if (selectCore.wheres != null && selectCore.wheres.size() > 0) {
            collector.append(SPACE);
            collector.append(WHERE);

            int len = selectCore.wheres.size() - 1;

            for (int i = 0; i < selectCore.wheres.size(); i++) {
                collector = visit(selectCore.wheres.get(i), collector);
                if (len != i) {
                    collector.append(AND);
                }
            }
        }

        if (selectCore.groups != null && selectCore.groups.size() > 0) {
            collector.append(GROUP_BY);

            int len = selectCore.groups.size() - 1;

            for (int i = 0; i < selectCore.groups.size(); i++) {
                collector = visit(selectCore.groups.get(i), collector);
                if (len != i) {
                    collector.append(COMMA);
                }
            }
        }

        if (selectCore.havings != null && selectCore.havings.size() > 0) {
            collector.append(" HAVING ");
            injectJoin(selectCore.havings, collector, AND);
        }

        if (selectCore.windows != null && selectCore.windows.size() > 0) {
            collector.append(WINDOW);

            int len = selectCore.windows.size() - 1;

            for (int i = 0; i < selectCore.windows.size(); i++) {
                collector = visit(selectCore.windows.get(i), collector);
                if (len != i) {
                    collector.append(COMMA);
                }
            }
        }

        return collector;
    }

    public ArelCollector visitArelNodeSelectOptions(ArelNodeSelectStatement selectStatement, ArelCollector collector) {
        maybeVisit(selectStatement.limit, collector);
        maybeVisit(selectStatement.offset, collector);
        maybeVisit(selectStatement.lock, collector);

        return collector;
    }

    public ArelCollector visitArelNodeSqlLiteral(ArelNodeSqlLiteral sqlLiteral, ArelCollector collector) {
        return literal(sqlLiteral, collector);
    }

    public ArelCollector visitArelNodeStringJoin(ArelNodeStringJoin stringJoin, ArelCollector collector) {
        collector = visit(stringJoin.left(), collector);

        return collector;
    }

    public ArelCollector visitArelNodeSum(ArelNodeSum sum, ArelCollector collector) {
        collector = aggregate(SUM, sum, collector);

        return collector;
    }

    public ArelCollector visitArelNodeTableAlias(ArelNodeTableAlias tableAlias, ArelCollector collector) {
        collector = visit(tableAlias.relation(), collector);
        collector.append(SPACE);
        collector.append(quoteTableName(tableAlias.name()));

        return collector;
    }

    public ArelCollector visitArelNodeTop(ArelNodeTop top, ArelCollector collector) {
        return collector;
    }

    public ArelCollector visitArelNodeUnion(ArelNodeUnion union, ArelCollector collector) {
        collector.append(GROUPING_OPEN);
        collector = infixValue(union, collector, UNION);
        collector.append(GROUPING_CLOSE);

        return collector;
    }

    public ArelCollector visitArelNodeUnionAll(ArelNodeUnionAll unionAll, ArelCollector collector) {
        collector.append(GROUPING_OPEN);
        collector = infixValue(unionAll, collector, UNION_ALL);
        collector.append(GROUPING_CLOSE);

        return collector;
    }

    public ArelCollector visitArelNodeUnqualifiedColumn(ArelNodeUnqualifiedColumn unqualifiedColumn, ArelCollector collector) {
        collector.append(quoteColumnName(unqualifiedColumn.name()));

        return collector;
    }

    public ArelCollector visitArelNodeUpdateStatement(ArelNodeUpdateStatement updateStatement, ArelCollector collector) {
        collector.append(UPDATE);
        collector = visit(updateStatement.relation(), collector);

        if (updateStatement.values() != null && !updateStatement.values().isEmpty()) {
            collector.append(SET);
            collector = injectJoin(updateStatement.values(), collector, COMMA);
        }

        List<Object> wheres;
        if ((updateStatement.orders() == null || updateStatement.orders().isEmpty()) && updateStatement.limit() == null) {
            wheres = updateStatement.wheres();
        } else {
            List<Object> in = new ArrayList<>();
            in.add(buildSubselect(updateStatement.key(), updateStatement));

            wheres = new ArrayList<>();
            wheres.add(new ArelNodeIn(updateStatement.key(), in));
        }

        if (wheres != null && !wheres.isEmpty()) {
            collector.append(SPACE);
            collector.append(WHERE);
            collector = injectJoin(wheres, collector, AND);
        }

        return collector;
    }

    public ArelCollector visitArelNodeValues(ArelNodeValues values, ArelCollector collector) {
        collector.append(VALUES);
        collector.append(GROUPING_OPEN);

        List<Object> valuesList = (List<Object>) values.expressions();
        List<Object> columnsList = (List<Object>) values.columns();
        int lastIndex = valuesList.size() - 1;
        for (int i = 0; i <= lastIndex; i++) {
            Object value = valuesList.get(i);

            Object column = null;
            if (columnsList.size() > i) {
                column = columnsList.get(i);
            }

            if (value instanceof ArelNodeSqlLiteral || value instanceof ArelNodeBindParam) {
                collector = visit(value, collector);
            } else if (column != null) {
                collector.append(quote(value, columnFor(column)));
            } else {
                collector.append(quote(value));
            }

            if (i < lastIndex) {
                collector.append(COMMA);
            }
        }

        collector.append(GROUPING_CLOSE);

        return collector;
    }

    public ArelCollector visitArelNodeWindow(ArelNodeWindow window, ArelCollector collector) {
        collector.append(GROUPING_OPEN);

        boolean partitions = false;
        if (window.partitions != null && !window.partitions.isEmpty()) {
            partitions = true;

            collector.append(PARTITION_BY);
            collector = injectJoin(window.partitions, collector, COMMA);
        }

        boolean orders = false;
        if (window.orders != null && !window.orders.isEmpty()) {
            orders = true;

            if (partitions) {
                collector.append(SPACE);
            }

            collector.append(ORDER_BY);
            collector = injectJoin(window.orders, collector, ", ");
        }

        if (window.framing != null) {
            if (partitions || orders) {
                collector.append(SPACE);
            }

            collector = visit(window.framing, collector);
        }

        collector.append(GROUPING_CLOSE);

        return collector;
    }

    public ArelCollector visitArelNodeWith(ArelNodeWith with, ArelCollector collector) {
        collector.append(WITH);
        collector = injectJoin(with.children(), collector, COMMA);

        return collector;
    }

    public ArelCollector visitArelNodeWithRecursive(ArelNodeWithRecursive withRecursive, ArelCollector collector) {
        collector.append(WITH_RECURSIVE);
        collector = injectJoin(withRecursive.children(), collector, COMMA);

        return collector;
    }

    public ArelCollector visitArelSelectManager(ArelSelectManager selectManager, ArelCollector collector) {
        collector.append(GROUPING_OPEN);
        collector.append(selectManager.toSQL());
        collector.append(GROUPING_CLOSE);

        return collector;
    }

    public ArelCollector visitArelTable(ArelTable table, ArelCollector collector) {
        if ((table.tableAlias() != null) && (table.tableAlias().length() > 0)) {
            collector.append(String.format("%s %s", quoteTableName(table.tableName()), quoteTableName(table.tableAlias())));
        } else {
            collector.append(quoteTableName(table.tableName()));
        }
        return collector;
    }

    public ArelCollector visitArrayList(ArrayList arrayList, ArelCollector collector) {
        return visitList(arrayList, collector);
    }

    public ArelCollector visitInteger(Integer integer, ArelCollector collector) {
        return literal(integer, collector);
    }

    public ArelCollector visitLinkedList(LinkedList linkedList, ArelCollector collector) {
        return visitList(linkedList, collector);
    }

    public ArelCollector visitList(List list, ArelCollector collector) {
        collector = injectJoin(list, collector, COMMA);

        return collector;
    }

    public ArelCollector visitObjectArray(Object[] list, ArelCollector collector) {
        collector = injectJoin(list, collector, COMMA);

        return collector;
    }

    private ArelCollector aggregate(String name, ArelNodeFunction function, ArelCollector collector) {
        collector.append(name);
        collector.append(GROUPING_OPEN);
        try {
            if (function.distinct()) {
                collector.append(DISTINCT);
                collector.append(SPACE);
            }
        } catch (ClassCastException ignored) {
        }
        collector = injectJoin(((List<Object>) function.expressions), collector, COMMA);
        collector.append(GROUPING_CLOSE);
        if (function.alias != null) {
            collector.append(AS);
            collector = visit(function.alias, collector);
        }

        return collector;
    }

    private ArelNodeSelectStatement buildSubselect(Object key, ArelNodeUpdateStatement updateStatement) {
        ArelNodeSelectStatement selectStatement = new ArelNodeSelectStatement();

        List<Object> projections = new ArrayList<>();
        projections.add(key);

        ArelNodeSelectCore core = selectStatement.cores[0];
        core.from(updateStatement.relation());
        core.wheres = updateStatement.wheres();
        core.projections = projections;

        selectStatement.limit = updateStatement.limit();
        selectStatement.orders = updateStatement.orders();

        return selectStatement;
    }

    private Map<Object, Object> columnCache(Object table) {
        return schemaCache().columnsHash(table);
    }

    private Object columnFor(Object object) {
        if (object == null) {
            return null;
        }

        ArelAttribute attr = (ArelAttribute) object;

        String name = attr.name;
        Object table = attr.relation.tableName();

        if (!tableExists(table)) {
            return null;
        }

        return columnCache(table).get(name);
    }

    private ArelCollector infixValue(Object object, ArelCollector collector, Object value) {
        ArelNodeBinary binary = (ArelNodeBinary) object;

        collector = visit(binary.left(), collector);
        collector.append(String.valueOf(value));
        collector = visit(binary.right(), collector);

        return collector;
    }

    private ArelCollector literal(Object object, ArelCollector collector) {
        collector.append(String.valueOf(object));
        return collector;
    }

    private ArelCollector maybeVisit(Object thing, ArelCollector collector) {
        if (thing == null) {
            return collector;
        }

        collector.append(SPACE);
        visit(thing, collector);

        return collector;
    }

    private String quote(Object value) {
        if (value instanceof ArelNodeSqlLiteral) {
            return ((ArelNodeSqlLiteral) value).getValue();
        }

        return String.valueOf(this.connection.quote(value));
    }

    private String quote(Object value, Object column) {
        if (value instanceof ArelNodeSqlLiteral) {
            return ((ArelNodeSqlLiteral) value).getValue();
        }

        return String.valueOf(this.connection.quote(value, column));
    }

    private String quoteColumnName(Object columnName) {
        if (columnName instanceof ArelNodeSqlLiteral) {
            return ((ArelNodeSqlLiteral) columnName).getValue();
        } else {
            return this.connection.quoteColumnName(String.valueOf(columnName));
        }
    }

    private String quoteTableName(Object tableName) {
        if (tableName instanceof ArelNodeSqlLiteral) {
            return ((ArelNodeSqlLiteral) tableName).getValue();
        } else {
            return this.connection.quoteTableName(String.valueOf(tableName));
        }
    }

    private String quoted(Object object, ArelAttribute attribute) {
        if (attribute != null && attribute.isAbleToTypeCast()) {
            return quote(attribute.typeCastForDatabase(object));
        } else {
            return quote(object);
        }
    }

    private SchemaCache schemaCache() {
        return this.connection.schemaCache();
    }

    private boolean tableExists(Object name) {
        return schemaCache().tableExists(name);
    }
}

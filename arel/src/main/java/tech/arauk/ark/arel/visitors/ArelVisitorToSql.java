package tech.arauk.ark.arel.visitors;

import tech.arauk.ark.arel.ArelSelectManager;
import tech.arauk.ark.arel.ArelTable;
import tech.arauk.ark.arel.ArelUtils;
import tech.arauk.ark.arel.attributes.ArelAttribute;
import tech.arauk.ark.arel.collectors.ArelCollector;
import tech.arauk.ark.arel.connection.ArelConnection;
import tech.arauk.ark.arel.nodes.*;
import tech.arauk.ark.arel.nodes.binary.*;
import tech.arauk.ark.arel.nodes.function.ArelNodeExists;
import tech.arauk.ark.arel.nodes.unary.*;

import java.util.ArrayList;
import java.util.List;

public class ArelVisitorToSql extends ArelVisitor {
    private static final String AND = " AND ";
    private static final String AS = " AS ";
    private static final String ASC = " ASC";
    private static final String BETWEEN = " BETWEEN ";
    private static final String COMMA = ", ";
    private static final String DESC = " DESC";
    private static final String EXCEPT = " EXCEPT ";
    private static final String EXISTS = "EXISTS ";
    private static final String FROM = " FROM ";
    private static final String FULL_OUTER_JOIN = "FULL OUTER JOIN ";
    private static final String GREATER_THAN = " > ";
    private static final String GROUP_BY = " GROUP BY ";
    private static final String GROUPING_CLOSE = ")";
    private static final String GROUPING_OPEN = "(";
    private static final String IN = " IN ";
    private static final String INNER_JOIN = "INNER JOIN ";
    private static final String INTERSECT = " INTERSECT ";
    private static final String IS_NULL = " IS NULL";
    private static final String LEFT_OUTER_JOIN = "LEFT OUTER JOIN ";
    private static final String LESS_THAN = " < ";
    private static final String LIMIT = "LIMIT ";
    private static final String OFFSET = "OFFSET ";
    private static final String ON = "ON ";
    private static final String ORDER_BY = " ORDER BY ";
    private static final String RIGHT_OUTER_JOIN = "RIGHT OUTER JOIN ";
    private static final String SPACE = " ";
    private static final String UNION = " UNION ";
    private static final String UNION_ALL = " UNION ALL ";
    private static final String WHERE = " WHERE ";
    private static final String WINDOW = " WINDOW ";
    private static final String WITH = "WITH ";

    private static final String WITH_RECURSIVE = "WITH RECURSIVE ";
    private static final String ONE_EQUALS_ZERO = "1=0";

    public ArelVisitorToSql(ArelConnection arelConnection) {
        super(arelConnection);
    }

    public ArelCollector visitArelAttribute(Object object, ArelCollector collector) {
        ArelAttribute attribute = (ArelAttribute) object;

        Object joinName = attribute.relation.tableAlias();
        if (joinName == null) {
            joinName = attribute.relation.tableName();
        }

        collector.append(quoteTableName(joinName) + "." + quoteColumnName(attribute.name));

        return collector;
    }

    public ArelCollector visitArelNodeAnd(Object object, ArelCollector collector) {
        ArelNodeAnd and = (ArelNodeAnd) object;

        collector = injectJoin(and.children, collector, AND);

        return collector;
    }

    public ArelCollector visitArelNodeAs(Object object, ArelCollector collector) {
        ArelNodeAs as = (ArelNodeAs) object;

        collector = visit(as.left, collector);
        collector.append(AS);
        collector = visit(as.right, collector);

        return collector;
    }

    public ArelCollector visitArelNodeAscending(Object object, ArelCollector collector) {
        ArelNodeAscending ascending = (ArelNodeAscending) object;

        collector = visit(ascending.expr, collector);
        collector.append(ASC);

        return collector;
    }

    public ArelCollector visitArelNodeBetween(Object object, ArelCollector collector) {
        ArelNodeBetween between = (ArelNodeBetween) object;

        collector = visit(between.left, collector);
        collector.append(BETWEEN);
        collector = visit(between.right, collector);

        return collector;
    }

    public ArelCollector visitArelNodeCasted(Object object, ArelCollector collector) {
        ArelNodeCasted casted = (ArelNodeCasted) object;

        collector.append(quoted(casted.val, casted.attribute));

        return collector;
    }

    public ArelCollector visitArelNodeDescending(Object object, ArelCollector collector) {
        ArelNodeDescending descending = (ArelNodeDescending) object;

        collector = visit(descending.expr, collector);
        collector.append(DESC);

        return collector;
    }

    public ArelCollector visitArelNodeEquality(Object object, ArelCollector collector) {
        ArelNodeEquality equality = (ArelNodeEquality) object;

        collector = visit(equality.left, collector);

        if (equality.right == null) {
            collector.append(IS_NULL);
        } else {
            collector.append(" = ");
            visit(equality.right, collector);
        }

        return collector;
    }

    public ArelCollector visitArelNodeExcept(Object object, ArelCollector collector) {
        ArelNodeExcept except = (ArelNodeExcept) object;

        collector.append(GROUPING_OPEN);
        collector = infixValue(except, collector, EXCEPT);
        collector.append(GROUPING_CLOSE);

        return collector;
    }

    public ArelCollector visitArelNodeExists(Object object, ArelCollector collector) {
        ArelNodeExists exists = (ArelNodeExists) object;

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

    public ArelCollector visitArelNodeFullOuterJoin(Object object, ArelCollector collector) {
        ArelNodeFullOuterJoin fullOuterJoin = (ArelNodeFullOuterJoin) object;

        collector.append(FULL_OUTER_JOIN);
        collector = visit(fullOuterJoin.left, collector);
        collector.append(SPACE);
        collector = visit(fullOuterJoin.right, collector);

        return collector;
    }

    public ArelCollector visitArelNodeGroup(Object object, ArelCollector collector) {
        ArelNodeGroup group = (ArelNodeGroup) object;

        collector = visit(group.expr, collector);

        return collector;
    }

    public ArelCollector visitArelNodeGrouping(Object object, ArelCollector collector) {
        ArelNodeGrouping grouping = (ArelNodeGrouping) object;

        if (grouping.expr instanceof ArelNodeGrouping) {
            collector = visit(grouping.expr, collector);
        } else {
            collector.append(GROUPING_OPEN);
            collector = visit(grouping.expr, collector);
            collector.append(GROUPING_CLOSE);
        }

        return collector;
    }

    public ArelCollector visitArelNodeGreaterThan(Object object, ArelCollector collector) {
        ArelNodeGreaterThan greaterThan = (ArelNodeGreaterThan) object;

        collector = visit(greaterThan.left, collector);
        collector.append(GREATER_THAN);
        collector = visit(greaterThan.right, collector);

        return collector;
    }

    public ArelCollector visitArelNodeIn(Object object, ArelCollector collector) {
        ArelNodeIn in = (ArelNodeIn) object;

        if (in.right instanceof List && ((List<Object>) in.right).isEmpty()) {
            collector.append(ONE_EQUALS_ZERO);
        } else {
            collector = visit(in.left, collector);
            collector.append(IN);
            collector.append(GROUPING_OPEN);
            collector = visit(in.right, collector);
            collector.append(GROUPING_CLOSE);
        }

        return collector;
    }

    public ArelCollector visitArelNodeInnerJoin(Object object, ArelCollector collector) {
        ArelNodeInnerJoin innerJoin = (ArelNodeInnerJoin) object;

        collector.append(INNER_JOIN);
        collector = visit(innerJoin.left, collector);

        if (innerJoin.right != null) {
            collector.append(SPACE);
            collector = visit(innerJoin.right, collector);
        }

        return collector;
    }

    public ArelCollector visitArelNodeInsertStatement(Object object, ArelCollector collector) {
        ArelNodeInsertStatement insertStatement = (ArelNodeInsertStatement) object;

        collector.append("INSERT INTO ");
        collector = visit(insertStatement.relation, collector);

        if (insertStatement.columns != null && !insertStatement.columns.isEmpty()) {
            List<String> quotedColumns = new ArrayList<>();
            for (String column : insertStatement.columns) {
                quotedColumns.add(quoteColumnName(column));
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

    public ArelCollector visitArelNodeIntersect(Object object, ArelCollector collector) {
        ArelNodeIntersect intersect = (ArelNodeIntersect) object;

        collector.append(GROUPING_OPEN);
        collector = infixValue(intersect, collector, INTERSECT);
        collector.append(GROUPING_CLOSE);

        return collector;
    }

    public ArelCollector visitArelNodeJoinSource(Object object, ArelCollector collector) {
        ArelNodeJoinSource joinSource = (ArelNodeJoinSource) object;

        if (joinSource.left != null) {
            collector = visit(joinSource.left, collector);
        }

        if (joinSource.right != null && ((List<Object>) joinSource.right).size() > 0) {
            if (joinSource.left != null) {
                collector.append(SPACE);
            }
            collector = injectJoin(((List<Object>) joinSource.right), collector, SPACE);
        }

        return collector;
    }

    public ArelCollector visitArelNodeLessThan(Object object, ArelCollector collector) {
        ArelNodeLessThan lessThan = (ArelNodeLessThan) object;

        collector = visit(lessThan.left, collector);
        collector.append(LESS_THAN);
        collector = visit(lessThan.right, collector);

        return collector;
    }

    public ArelCollector visitArelNodeLimit(Object object, ArelCollector collector) {
        ArelNodeLimit limit = (ArelNodeLimit) object;

        collector.append(LIMIT);
        collector = visit(limit.expr, collector);

        return collector;
    }

    public ArelCollector visitArelNodeLock(Object object, ArelCollector collector) {
        ArelNodeLock lock = (ArelNodeLock) object;

        collector = visit(lock.expr, collector);

        return collector;
    }

    public ArelCollector visitArelNodeOffset(Object object, ArelCollector collector) {
        ArelNodeOffset offset = (ArelNodeOffset) object;

        collector.append(OFFSET);

        if (offset.expr != null) {
            collector = visit(offset.expr, collector);
        }

        return collector;
    }

    public ArelCollector visitArelNodeOn(Object object, ArelCollector collector) {
        ArelNodeOn nodeOn = (ArelNodeOn) object;

        collector.append(ON);
        collector = visit(nodeOn.expr, collector);

        return collector;
    }

    public ArelCollector visitArelNodeOuterJoin(Object object, ArelCollector collector) {
        ArelNodeOuterJoin outerJoin = (ArelNodeOuterJoin) object;

        collector.append(LEFT_OUTER_JOIN);
        collector = visit(outerJoin.left, collector);
        collector.append(SPACE);
        collector = visit(outerJoin.right, collector);

        return collector;
    }

    public ArelCollector visitArelNodeQuoted(Object object, ArelCollector collector) {
        ArelNodeQuoted quoted = (ArelNodeQuoted) object;

        collector.append(quoted(quoted.expr, null));

        return collector;
    }

    public ArelCollector visitArelNodeRightOuterJoin(Object object, ArelCollector collector) {
        ArelNodeRightOuterJoin rightOuterJoin = (ArelNodeRightOuterJoin) object;

        collector.append(RIGHT_OUTER_JOIN);
        collector = visit(rightOuterJoin.left, collector);
        collector.append(SPACE);
        collector = visit(rightOuterJoin.right, collector);

        return collector;
    }

    public ArelCollector visitArelNodeSelectStatement(Object object, ArelCollector collector) {
        ArelNodeSelectStatement selectStatement = (ArelNodeSelectStatement) object;

        if (selectStatement.with != null) {
            collector = visit(selectStatement.with, collector);
            collector.append(SPACE);
        }

        for (ArelNodeSelectCore selectCore : selectStatement.cores) {
            collector = visitArelNodeSelectCore(selectCore, collector);
        }

        if (selectStatement.orders != null && selectStatement.orders.size() > 0) {
            collector.append(ORDER_BY);

            int len = selectStatement.orders.size() - 1;

            for (int i = 0; i < selectStatement.orders.size(); i++) {
                collector = visit(selectStatement.orders.get(i), collector);
                if (len != i) {
                    collector.append(COMMA);
                }
            }
        }

        collector = visitArelNodeSelectOptions(object, collector);

        return collector;
    }

    public ArelCollector visitArelNodeSelectCore(Object object, ArelCollector collector) {
        ArelNodeSelectCore selectCore = (ArelNodeSelectCore) object;

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

        if (selectCore.windows != null && selectCore.windows.length > 0) {
            collector.append(WINDOW);

            int len = selectCore.windows.length - 1;

            for (int i = 0; i < selectCore.windows.length; i++) {
                collector = visit(selectCore.windows[i], collector);
                if (len != i) {
                    collector.append(COMMA);
                }
            }
        }

        return collector;
    }

    public ArelCollector visitArelNodeSelectOptions(Object object, ArelCollector collector) {
        ArelNodeSelectStatement selectStatement = (ArelNodeSelectStatement) object;

        maybeVisit(selectStatement.limit, collector);
        maybeVisit(selectStatement.offset, collector);
        maybeVisit(selectStatement.lock, collector);

        return collector;
    }

    public ArelCollector visitArelNodeSqlLiteral(Object object, ArelCollector collector) {
        return literal(object, collector);
    }

    public ArelCollector visitArelNodeStringJoin(Object object, ArelCollector collector) {
        ArelNodeStringJoin stringJoin = (ArelNodeStringJoin) object;

        collector = visit(stringJoin.left, collector);

        return collector;
    }

    public ArelCollector visitArelNodeTableAlias(Object object, ArelCollector collector) {
        ArelNodeTableAlias tableAlias = (ArelNodeTableAlias) object;

        collector = visit(tableAlias.relation(), collector);
        collector.append(SPACE);
        collector.append(quoteTableName(tableAlias.name()));

        return collector;
    }

    public ArelCollector visitArelNodeTop(Object object, ArelCollector collector) {
        return collector;
    }

    public ArelCollector visitArelNodeUnion(Object object, ArelCollector collector) {
        ArelNodeUnion union = (ArelNodeUnion) object;

        collector.append(GROUPING_OPEN);
        collector = infixValue(union, collector, UNION);
        collector.append(GROUPING_CLOSE);

        return collector;
    }

    public ArelCollector visitArelNodeUnionAll(Object object, ArelCollector collector) {
        ArelNodeUnionAll unionAll = (ArelNodeUnionAll) object;

        collector.append(GROUPING_OPEN);
        collector = infixValue(unionAll, collector, UNION_ALL);
        collector.append(GROUPING_CLOSE);

        return collector;
    }

    public ArelCollector visitArelNodeWith(Object object, ArelCollector collector) {
        ArelNodeWith with = (ArelNodeWith) object;

        collector.append(WITH);
        collector = injectJoin(with.children(), collector, COMMA);

        return collector;
    }

    public ArelCollector visitArelNodeWithRecursive(Object object, ArelCollector collector) {
        ArelNodeWithRecursive withRecursive = (ArelNodeWithRecursive) object;

        collector.append(WITH_RECURSIVE);
        collector = injectJoin(withRecursive.children(), collector, COMMA);

        return collector;
    }

    public ArelCollector visitArelSelectManager(Object object, ArelCollector collector) {
        ArelSelectManager selectManager = (ArelSelectManager) object;

        collector.append(GROUPING_OPEN);
        collector.append(selectManager.toSQL());
        collector.append(GROUPING_CLOSE);

        return collector;
    }

    public ArelCollector visitArelTable(Object object, ArelCollector collector) {
        ArelTable table = (ArelTable) object;

        if ((table.tableAlias() != null) && (table.tableAlias().length() > 0)) {
            collector.append(String.format("%s %s", quoteTableName(table.tableName()), quoteTableName(table.tableAlias())));
        } else {
            collector.append(quoteTableName(table.tableName()));
        }
        return collector;
    }

    public ArelCollector visitArrayList(Object object, ArelCollector collector) {
        ArrayList<Object> arrayList = (ArrayList<Object>) object;

        collector = injectJoin(arrayList, collector, COMMA);

        return collector;
    }

    public ArelCollector visitInteger(Object object, ArelCollector collector) {
        return literal(object, collector);
    }

    private ArelCollector infixValue(Object object, ArelCollector collector, Object value) {
        ArelNodeBinary binary = (ArelNodeBinary) object;

        collector = visit(binary.left, collector);
        collector.append(String.valueOf(value));
        collector = visit(binary.right, collector);

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

    private ArelCollector injectJoin(List<Object> list, ArelCollector collector, String joinStr) {
        int len = list.size() - 1;

        for (int i = 0; i < list.size(); i++) {
            collector = visit(list.get(i), collector);

            if (i != len) {
                collector.append(joinStr);
            }
        }

        return collector;
    }
}

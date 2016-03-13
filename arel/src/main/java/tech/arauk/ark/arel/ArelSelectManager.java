package tech.arauk.ark.arel;

import tech.arauk.ark.arel.attributes.ArelAttribute;
import tech.arauk.ark.arel.collectors.ArelCollector;
import tech.arauk.ark.arel.nodes.*;
import tech.arauk.ark.arel.nodes.binary.ArelNodeExcept;
import tech.arauk.ark.arel.nodes.binary.ArelNodeIntersect;
import tech.arauk.ark.arel.nodes.binary.ArelNodeUnion;
import tech.arauk.ark.arel.nodes.binary.ArelNodeUnionAll;
import tech.arauk.ark.arel.nodes.function.ArelNodeExists;
import tech.arauk.ark.arel.nodes.unary.*;
import tech.arauk.ark.arel.visitors.ArelVisitor;
import tech.arauk.ark.arel.visitors.ArelVisitorWhereSql;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ArelSelectManager extends ArelTreeManager implements ArelCrudInterface {
    public ArelSelectManager() {
        super(new ArelNodeSelectStatement());

        ArelNodeSelectCore[] cores = ((ArelNodeSelectStatement) ast()).cores;

        ctx(cores[cores.length - 1]);
    }

    public ArelSelectManager(Object table) {
        super(new ArelNodeSelectStatement());

        ArelNodeSelectCore[] cores = ((ArelNodeSelectStatement) ast()).cores;

        ctx(cores[cores.length - 1]);

        from(table);
    }

    @Override
    public ArelDeleteManager compileDelete() {
        return ArelCrud.compileDelete(this);
    }

    @Override
    public ArelInsertManager compileInsert(Object values) {
        return ArelCrud.compileInsert(values);
    }

    @Override
    public ArelUpdateManager compileUpdate(Object values, ArelAttribute pk) {
        return ArelCrud.compileUpdate(this, values, pk);
    }

    @Override
    public ArelInsertManager createInsert() {
        return ArelCrud.createInsert();
    }

    @Override
    public ArelSelectManager where(Object expr) {
        return (ArelSelectManager) super.where(expr);
    }

    public ArelNodeTableAlias as(Object other) {
        return createTableAlias(ArelFactoryMethods.grouping(ast()), new ArelNodeSqlLiteral(other));
    }

    public ArelSelectManager on(Object... exprs) {
        List<Object> right = joinSources();
        List<Object> innerRight = new ArrayList<>();
        innerRight.add(new ArelNodeOn(collapse(exprs)));

        ((ArelNodeBinary) right.get(right.size() - 1)).right(innerRight);

        return this;
    }

    public ArelSelectManager from(Object object) {
        Object table;

        if (object instanceof String) {
            table = new ArelNodeSqlLiteral(object);
        } else {
            table = object;
        }

        if (table instanceof ArelNodeJoin) {
            joinSources().add(table);
        } else {
            ctx().source.left(table);
        }

        return this;
    }

    public ArelNodeOffset offset() {
        return skip();
    }

    public ArelSelectManager offset(int amount) {
        return skip(amount);
    }

    public ArelSelectManager offset(ArelNodeOffset amount) {
        return skip(amount);
    }

    public ArelNodeOffset skip() {
        return ((ArelNodeSelectStatement) ast()).offset;
    }

    public ArelSelectManager skip(int amount) {
        return skip(new ArelNodeOffset(amount));
    }

    public ArelSelectManager skip(ArelNodeOffset amount) {
        ((ArelNodeSelectStatement) ast()).offset = amount;
        return this;
    }

    public ArelSelectManager join(Object relation) {
        return join(relation, ArelNodeInnerJoin.class);
    }

    public ArelSelectManager join(Object relation, Class<? extends ArelNodeJoin> aClass) {
        if (relation == null) {
            return this;
        }

        if (relation instanceof String) {
            if (((String) relation).trim().length() <= 0) {
                throw new RuntimeException("relation is null");
            }
            aClass = ArelNodeStringJoin.class;
        } else if (relation instanceof ArelNodeSqlLiteral) {
            aClass = ArelNodeStringJoin.class;
        }

        joinSources().add(createJoin(relation, null, aClass));

        return this;
    }

    public ArelSelectManager having(Object expr) {
        ctx().havings.add(expr);
        return this;
    }

    private Object collapse(Object... exprs) {
        Object object;

        for (int i = 0; i < exprs.length; i++) {
            object = exprs[i];
            if (object instanceof String) {
                exprs[i] = Arel.sql(String.valueOf(object));
            }
        }

        if (exprs.length == 1) {
            return exprs[0];
        } else {
            return createAnd(Arrays.asList(exprs));
        }
    }

    public ArelSelectManager group(Object... columns) {
        for (Object column : columns) {
            if (column instanceof String) {
                column = new ArelNodeSqlLiteral(String.valueOf(column));
            }

            ctx().groups.add(new ArelNodeGroup(column));
        }

        return this;
    }

    public ArelSelectManager order(Object... expr) {
        ArelNodeSelectStatement selectStatement = (ArelNodeSelectStatement) ast();

        for (Object object : expr) {
            if (object instanceof String) {
                object = new ArelNodeSqlLiteral(String.valueOf(object));
            }

            selectStatement.orders.add(object);
        }

        return this;
    }

    public List<Object> orders() {
        return ((ArelNodeSelectStatement) ast()).orders;
    }

    public ArelNodeLimit take() {
        return ((ArelNodeSelectStatement) ast()).limit;
    }

    public ArelSelectManager take(int limit) {
        ((ArelNodeSelectStatement) ast()).limit = new ArelNodeLimit(limit);
        ctx().top = new ArelNodeTop(limit);

        return this;
    }

    public ArelSelectManager take(ArelNodeLimit limit) {
        ((ArelNodeSelectStatement) ast()).limit = limit;
        return this;
    }

    public ArelNodeLimit taken() {
        return take();
    }

    public ArelNodeLimit limit() {
        return take();
    }

    public ArelSelectManager limit(int limit) {
        return take(limit);
    }

    public ArelSelectManager limit(ArelNodeLimit limit) {
        return take(limit);
    }

    public ArelSelectManager project(Object... projections) {
        return project(Arrays.asList(projections));
    }

    public ArelSelectManager project(List<Object> projections) {
        for (Object projection : projections) {
            if (projection instanceof String) {
                projection = new ArelNodeSqlLiteral(String.valueOf(projection));
            }

            ctx().projections.add(projection);
        }

        return this;
    }

    public ArelNodeExcept except(ArelSelectManager other) {
        return new ArelNodeExcept(ast(), other.ast());
    }

    public ArelNodeExists exists() {
        return new ArelNodeExists(ast());
    }

    public ArelNodeIntersect intersect(ArelSelectManager other) {
        return new ArelNodeIntersect(ast(), other.ast());
    }

    public ArelNodeBinary union(ArelSelectManager other) {
        return new ArelNodeUnion(ast(), other.ast());
    }

    public ArelNodeBinary union(ArelSelectManager other, String operation) {
        if (Objects.equals("all", operation)) {
            return new ArelNodeUnionAll(ast(), other.ast());
        } else {
            return union(other);
        }
    }

    public ArelSelectManager with(Object... subqueries) {
        return with(Arrays.asList(subqueries));
    }

    public ArelSelectManager with(List<Object> subqueries) {
        ((ArelNodeSelectStatement) ast()).with = new ArelNodeWith(subqueries);

        return this;
    }

    public ArelSelectManager withRecursive(Object... subqueries) {
        return withRecursive(Arrays.asList(subqueries));
    }

    public ArelSelectManager withRecursive(List<Object> subqueries) {
        ((ArelNodeSelectStatement) ast()).with = new ArelNodeWithRecursive(subqueries);

        return this;
    }

    public List<Object> joinSources() {
        return (List<Object>) ctx().source.right();
    }

    public ArelSelectManager lock() {
        return lock(Arel.sql("FOR UPDATE"));
    }

    public ArelSelectManager lock(Object locking) {
        if (locking instanceof Boolean && (Boolean) locking) {
            locking = Arel.sql("FOR UPDATE");
        } else if (locking instanceof String) {
            locking = Arel.sql(String.valueOf(locking));
        }

        ((ArelNodeSelectStatement) ast()).lock = new ArelNodeLock(locking);

        return this;
    }

    public List<Object> froms() {
        List<Object> froms = new ArrayList<>();

        for (ArelNodeSelectCore selectCore : ((ArelNodeSelectStatement) ast()).cores) {
            Object from = selectCore.from();
            if (from != null) {
                froms.add(from);
            }
        }

        return froms;
    }

    public ArelSelectManager outerJoin(Object relation) {
        return join(relation, ArelNodeOuterJoin.class);
    }

    public ArelNodeWindow window(String name) {
        ArelNodeNamedWindow window = new ArelNodeNamedWindow(name);
        ctx().windows.add(window);

        return window;
    }

    public String whereSql() {
        ArelVisitor visitor = ArelTable.engine;
        return whereSql(visitor);
    }

    public String whereSql(ArelVisitor visitor) {
        if (ctx().wheres != null && !ctx().wheres.isEmpty()) {
            ArelVisitor whereSqlVisitor = new ArelVisitorWhereSql(visitor.connection);
            return (new ArelNodeSqlLiteral(whereSqlVisitor.accept(ctx(), new ArelCollector()).getValue())).toString();
        }

        return "";
    }
}

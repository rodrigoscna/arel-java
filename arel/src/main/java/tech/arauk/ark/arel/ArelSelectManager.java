package tech.arauk.ark.arel;

import tech.arauk.ark.arel.nodes.*;
import tech.arauk.ark.arel.nodes.binary.ArelNodeIntersect;
import tech.arauk.ark.arel.nodes.binary.ArelNodeUnion;
import tech.arauk.ark.arel.nodes.binary.ArelNodeUnionAll;
import tech.arauk.ark.arel.nodes.function.ArelNodeExists;
import tech.arauk.ark.arel.nodes.unary.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ArelSelectManager extends ArelTreeManager {
    public ArelSelectManager() {
        super(new ArelNodeSelectStatement());

        ArelNodeSelectCore[] cores = ((ArelNodeSelectStatement) this.ast).cores;

        this.ctx = cores[cores.length - 1];
    }

    public ArelSelectManager(Object table) {
        super(new ArelNodeSelectStatement());

        ArelNodeSelectCore[] cores = ((ArelNodeSelectStatement) this.ast).cores;

        this.ctx = cores[cores.length - 1];

        from(table);
    }

    public ArelNodeTableAlias as(Object other) {
        return ArelFactoryMethods.createTableAlias(ArelFactoryMethods.grouping(this.ast), new ArelNodeSqlLiteral(other));
    }

    public ArelSelectManager on(Object... exprs) {
        List<Object> right = joinSources();
        List<Object> innerRight = new ArrayList<>();
        innerRight.add(new ArelNodeOn(collapse(exprs)));

        ((ArelNodeBinary) right.get(right.size() - 1)).right = innerRight;

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
            this.ctx.source.left = table;
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
        return ((ArelNodeSelectStatement) this.ast).offset;
    }

    public ArelSelectManager skip(int amount) {
        return skip(new ArelNodeOffset(amount));
    }

    public ArelSelectManager skip(ArelNodeOffset amount) {
        ((ArelNodeSelectStatement) this.ast).offset = amount;
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

        joinSources().add(ArelFactoryMethods.createJoin(relation, null, aClass));

        return this;
    }

    public ArelSelectManager having(Object expr) {
        this.ctx.havings.add(expr);
        return this;
    }

    private Object createAnd(List<Object> object) {
        return ArelFactoryMethods.createAnd(object);
    }

    private Object createJoin(Object to, String constraint, Class<? extends ArelNodeJoin> aClass) {
        return ArelFactoryMethods.createJoin(to, constraint, aClass);
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

            this.ctx.groups.add(new ArelNodeGroup(column));
        }

        return this;
    }

    public ArelSelectManager order(Object... expr) {
        ArelNodeSelectStatement selectStatement = (ArelNodeSelectStatement) this.ast;

        for (Object object : expr) {
            if (object instanceof String) {
                object = new ArelNodeSqlLiteral(String.valueOf(object));
            }

            selectStatement.orders.add(object);
        }

        return this;
    }

    public ArelSelectManager take() {
        ((ArelNodeSelectStatement) this.ast).limit = null;
        this.ctx.top = null;

        return this;
    }

    public ArelSelectManager take(int limit) {
        ((ArelNodeSelectStatement) this.ast).limit = new ArelNodeLimit(limit);
        this.ctx.top = new ArelNodeTop(limit);

        return this;
    }

    public ArelSelectManager limit() {
        return take();
    }

    public ArelSelectManager limit(int limit) {
        return take(limit);
    }

    public ArelSelectManager project(Object... projections) {
        for (Object projection : projections) {
            if (projection instanceof String) {
                projection = new ArelNodeSqlLiteral(String.valueOf(projection));
            }

            this.ctx.projections.add(projection);
        }

        return this;
    }

    public ArelNodeExists exists() {
        return new ArelNodeExists(this.ast);
    }

    public ArelNodeIntersect intersect(ArelSelectManager other) {
        return new ArelNodeIntersect(this.ast, other.ast);
    }

    public ArelNodeBinary union(ArelSelectManager other) {
        return new ArelNodeUnion(this.ast, other.ast);
    }

    public ArelNodeBinary union(ArelSelectManager other, String operation) {
        if (Objects.equals("all", operation)) {
            return new ArelNodeUnionAll(this.ast, other.ast);
        } else {
            return union(other);
        }
    }

    public List<Object> joinSources() {
        return (List<Object>) this.ctx.source.right;
    }
}

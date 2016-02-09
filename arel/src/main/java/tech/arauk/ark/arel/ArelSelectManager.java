package tech.arauk.ark.arel;

import tech.arauk.ark.arel.nodes.*;
import tech.arauk.ark.arel.nodes.unary.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    public ArelSelectManager on(Object exprs) {
        List<Object> right = ((List<Object>) this.ctx.source.right);
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
            ((List<Object>) this.ctx.source.right).add(table);
        } else {
            this.ctx.source.left = table;
        }

        return this;
    }

    public ArelSelectManager offset(int amount) {
        return skip(amount);
    }

    public ArelSelectManager offset(ArelNodeOffset amount) {
        return skip(amount);
    }

    public ArelSelectManager skip(int amount) {
        return skip(new ArelNodeOffset(amount));
    }

    public ArelSelectManager skip(ArelNodeOffset amount) {
        ((ArelNodeSelectStatement) this.ast).offset = amount;
        return this;
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

        ((List<Object>) this.ctx.source.right).add(ArelNodeFactory.createJoin(relation, null, aClass));

        return this;
    }

    public ArelSelectManager having(Object expr) {
        this.ctx.havings.add(expr);
        return this;
    }

    private Object createAnd(List<Object> object) {
        return ArelNodeFactory.createAnd(object);
    }

    private Object createJoin(Object to, String constraint, Class<? extends ArelNodeJoin> aClass) {
        return ArelNodeFactory.createJoin(to, constraint, aClass);
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
}

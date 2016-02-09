package tech.arauk.ark.arel;

import tech.arauk.ark.arel.nodes.*;
import tech.arauk.ark.arel.nodes.unary.ArelNodeOffset;
import tech.arauk.ark.arel.nodes.unary.ArelNodeOn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArelSelectManager extends ArelTreeManager {
    private ArelNodeSelectCore mCtx;

    public ArelSelectManager() {
        super(new ArelNodeSelectStatement());

        ArelNodeSelectCore[] cores = ((ArelNodeSelectStatement) this.ast).cores;

        this.mCtx = cores[cores.length - 1];
    }

    public ArelSelectManager(Object table) {
        super(new ArelNodeSelectStatement());

        ArelNodeSelectCore[] cores = ((ArelNodeSelectStatement) this.ast).cores;

        this.mCtx = cores[cores.length - 1];

        this.from(table);
    }

    public ArelSelectManager on(Object exprs) {
        List<Object> right = ((List<Object>) this.mCtx.source.right);
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
            ((List<Object>) this.mCtx.source.right).add(table);
        } else {
            this.mCtx.source.left = table;
        }

        return this;
    }

    public ArelSelectManager offset(int amount) {
        return this.skip(amount);
    }

    public ArelSelectManager offset(ArelNodeOffset amount) {
        return this.skip(amount);
    }

    public ArelSelectManager skip(int amount) {
        return this.skip(new ArelNodeOffset(amount));
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

        ((List<Object>) this.mCtx.source.right).add(ArelNodeFactory.createJoin(relation, null, aClass));

        return this;
    }

    public ArelSelectManager having(Object expr) {
        this.mCtx.havings.add(expr);
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
}

package tech.arauk.ark.arel.nodes;

import tech.arauk.ark.arel.ArelOrderPredications;
import tech.arauk.ark.arel.ArelOrderPredicationsInterface;

import java.util.Arrays;

public class ArelNodeFunction extends ArelNode implements ArelOrderPredicationsInterface {
    public Object alias;
    public Object expressions;
    public Boolean distinct;

    public ArelNodeFunction(Object expr) {
        this(expr, null);
    }

    public ArelNodeFunction(Object expr, String alias) {
        this.expressions = expr;
        if (alias != null) {
            this.alias = new ArelNodeSqlLiteral(alias);
        }
        this.distinct = false;
    }

    @Override
    public ArelNodeAscending asc() {
        return ArelOrderPredications.asc(this);
    }

    @Override
    public ArelNodeDescending desc() {
        return ArelOrderPredications.desc(this);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(new Object[]{expressions(), alias(), distinct()});
    }

    public ArelNodeFunction as(Object alias) {
        this.alias = new ArelNodeSqlLiteral(alias);
        return this;
    }

    public Object alias() {
        return this.alias;
    }

    public ArelNodeFunction alias(Object alias) {
        this.alias = alias;
        return this;
    }

    public Boolean distinct() {
        return this.distinct;
    }

    public ArelNodeFunction distinct(Boolean distinct) {
        this.distinct = distinct;
        return this;
    }

    public Object expressions() {
        return this.expressions;
    }

    public ArelNodeFunction expressions(Object expressions) {
        this.expressions = expressions;
        return this;
    }
}

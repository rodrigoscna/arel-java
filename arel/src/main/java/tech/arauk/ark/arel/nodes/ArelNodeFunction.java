package tech.arauk.ark.arel.nodes;

import tech.arauk.ark.arel.ArelOrderPredications;
import tech.arauk.ark.arel.ArelOrderPredicationsInterface;

public class ArelNodeFunction extends ArelNode implements ArelOrderPredicationsInterface {
    public Object alias;
    public Object expressions;

    public ArelNodeFunction(Object expr) {
        this(expr, null);
    }

    public ArelNodeFunction(Object expr, String alias) {
        this.expressions = expr;
        if (alias != null) {
            this.alias = new ArelNodeSqlLiteral(alias);
        }
    }

    @Override
    public ArelNodeAscending asc() {
        return ArelOrderPredications.asc(this);
    }

    @Override
    public ArelNodeDescending desc() {
        return ArelOrderPredications.desc(this);
    }

    public ArelNodeFunction as(Object alias) {
        this.alias = new ArelNodeSqlLiteral(alias);
        return this;
    }
}

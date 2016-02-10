package tech.arauk.ark.arel.nodes;

public class ArelNodeFunction extends ArelNode {
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

    public ArelNodeFunction as(Object alias) {
        this.alias = new ArelNodeSqlLiteral(alias);
        return this;
    }
}

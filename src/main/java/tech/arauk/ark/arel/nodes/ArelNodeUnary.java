package tech.arauk.ark.arel.nodes;

import tech.arauk.ark.arel.annotations.Beta;

import java.util.Objects;

@Beta
public class ArelNodeUnary extends ArelNode {
    private Object expr;

    public ArelNodeUnary(Object expr) {
        this.expr = expr;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof ArelNodeUnary) {
            return Objects.deepEquals(this.expr, ((ArelNodeUnary) other).expr());
        }

        return super.equals(other);
    }

    @Override
    public int hashCode() {
        return expr().hashCode();
    }

    public Object expr() {
        return this.expr;
    }

    public void expr(Object expr) {
        this.expr = expr;
    }
}

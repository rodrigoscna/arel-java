package tech.arauk.ark.arel.nodes;

import java.util.Objects;

public class ArelNodeUnary extends ArelNode {
    public Object expr;

    public ArelNodeUnary(Object expr) {
        this.expr = expr;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof ArelNodeUnary) {
            return Objects.equals(this.expr, ((ArelNodeUnary) other).expr);
        } else {
            return super.equals(other);
        }
    }
}

package tech.arauk.ark.arel.nodes.unary;

import tech.arauk.ark.arel.nodes.ArelNodeUnary;

import java.util.Objects;

public class ArelNodeOffset extends ArelNodeUnary {
    public ArelNodeOffset(Object expr) {
        super(expr);
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof ArelNodeOffset) {
            return Objects.equals(this.expr, ((ArelNodeOffset) other).expr);
        } else {
            return super.equals(other);
        }
    }
}

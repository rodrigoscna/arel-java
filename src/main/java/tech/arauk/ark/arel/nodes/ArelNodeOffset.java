package tech.arauk.ark.arel.nodes;

import tech.arauk.ark.arel.annotations.Beta;

import java.util.Objects;

@Beta
public class ArelNodeOffset extends ArelNodeUnary {
    public ArelNodeOffset(Object expr) {
        super(expr);
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof ArelNodeOffset) {
            return Objects.deepEquals(this.expr(), ((ArelNodeOffset) other).expr());
        }

        return super.equals(other);
    }
}

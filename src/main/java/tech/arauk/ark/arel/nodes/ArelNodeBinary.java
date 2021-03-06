package tech.arauk.ark.arel.nodes;

import tech.arauk.ark.arel.annotations.Beta;

import java.util.Arrays;
import java.util.Objects;

@Beta
public class ArelNodeBinary extends ArelNode {
    private Object left;
    private Object right;

    public ArelNodeBinary(Object left, Object right) {
        super();
        this.left = left;
        this.right = right;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof ArelNodeBinary) {
            ArelNodeBinary binary = (ArelNodeBinary) other;

            return Objects.deepEquals(this.left(), binary.left()) && Objects.deepEquals(this.right(), binary.right());
        }

        return super.equals(other);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(new Object[]{getClass(), left(), right()});
    }

    public Object left() {
        return this.left;
    }

    public ArelNodeBinary left(Object left) {
        this.left = left;
        return this;
    }

    public Object right() {
        return this.right;
    }

    public ArelNodeBinary right(Object right) {
        this.right = right;
        return this;
    }
}

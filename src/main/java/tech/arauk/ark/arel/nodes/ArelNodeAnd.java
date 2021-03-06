package tech.arauk.ark.arel.nodes;

import tech.arauk.ark.arel.annotations.Beta;

import java.util.List;
import java.util.Objects;

@Beta
public class ArelNodeAnd extends ArelNode {
    public List<Object> children;

    public ArelNodeAnd(List<Object> children) {
        this.children = children;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof ArelNodeAnd) {
            ArelNodeAnd and = (ArelNodeAnd) other;
            return Objects.deepEquals(this.children(), and.children());
        }

        return super.equals(other);
    }

    @Override
    public int hashCode() {
        return children().hashCode();
    }

    public List<Object> children() {
        return this.children;
    }

    public ArelNodeAnd children(List<Object> children) {
        this.children = children;
        return this;
    }

    public Object left() {
        return this.children.get(0);
    }

    public Object right() {
        return this.children.get(1);
    }
}

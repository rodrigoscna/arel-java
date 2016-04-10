package tech.arauk.ark.arel.nodes;

public class ArelNodeTrue extends ArelNode {
    @Override
    public boolean equals(Object other) {
        if (other instanceof ArelNodeTrue) {
            return true;
        }

        return super.equals(other);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

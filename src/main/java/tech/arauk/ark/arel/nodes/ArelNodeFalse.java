package tech.arauk.ark.arel.nodes;

public class ArelNodeFalse extends ArelNode {
    @Override
    public boolean equals(Object other) {
        if (other instanceof ArelNodeFalse) {
            return true;
        }

        return super.equals(other);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

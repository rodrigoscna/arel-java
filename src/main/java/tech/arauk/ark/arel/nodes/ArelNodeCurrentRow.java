package tech.arauk.ark.arel.nodes;

public class ArelNodeCurrentRow extends ArelNode {
    @Override
    public boolean equals(Object other) {
        if (other instanceof ArelNodeCurrentRow) {
            return true;
        }

        return super.equals(other);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

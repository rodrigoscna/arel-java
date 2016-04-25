package tech.arauk.ark.arel.nodes;

public class ArelNodeDistinct extends ArelNode {
    @Override
    public boolean equals(Object other) {
        if (other instanceof ArelNodeDistinct) {
            return true;
        }

        return super.equals(other);
    }
}

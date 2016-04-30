package tech.arauk.ark.arel.nodes;

import tech.arauk.ark.arel.annotations.Beta;

@Beta
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

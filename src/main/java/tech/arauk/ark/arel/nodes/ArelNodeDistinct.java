package tech.arauk.ark.arel.nodes;

import tech.arauk.ark.arel.annotations.Beta;

@Beta
public class ArelNodeDistinct extends ArelNode {
    @Override
    public boolean equals(Object other) {
        if (other instanceof ArelNodeDistinct) {
            return true;
        }

        return super.equals(other);
    }
}

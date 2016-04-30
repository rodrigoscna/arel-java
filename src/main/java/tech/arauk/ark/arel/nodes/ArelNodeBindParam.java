package tech.arauk.ark.arel.nodes;

import tech.arauk.ark.arel.annotations.Beta;

@Beta
public class ArelNodeBindParam extends ArelNode {
    @Override
    public boolean equals(Object other) {
        return other instanceof ArelNodeBindParam;
    }
}

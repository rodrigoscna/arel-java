package tech.arauk.ark.arel.nodes;

public class ArelNodeBindParam extends ArelNode {
    @Override
    public boolean equals(Object other) {
        return other instanceof ArelNodeBindParam;
    }
}

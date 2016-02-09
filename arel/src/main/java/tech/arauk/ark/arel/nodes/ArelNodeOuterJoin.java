package tech.arauk.ark.arel.nodes;

public class ArelNodeOuterJoin extends ArelNodeJoin {
    public ArelNodeOuterJoin(Object left, String right, Class<? extends ArelNodeJoin> constraint) {
        super(left, right, constraint);
    }
}

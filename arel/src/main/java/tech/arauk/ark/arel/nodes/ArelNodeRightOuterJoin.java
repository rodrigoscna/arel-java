package tech.arauk.ark.arel.nodes;

public class ArelNodeRightOuterJoin extends ArelNodeJoin {
    public ArelNodeRightOuterJoin(Object left, String right, Class<? extends ArelNodeJoin> constraint) {
        super(left, right, constraint);
    }
}

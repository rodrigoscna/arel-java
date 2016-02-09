package tech.arauk.ark.arel.nodes;

public class ArelNodeInnerJoin extends ArelNodeJoin {
    public ArelNodeInnerJoin(Object left, String right, Class<? extends ArelNodeJoin> constraint) {
        super(left, right, constraint);
    }
}

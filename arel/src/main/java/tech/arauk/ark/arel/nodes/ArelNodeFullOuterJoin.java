package tech.arauk.ark.arel.nodes;

public class ArelNodeFullOuterJoin extends ArelNodeJoin {
    public ArelNodeFullOuterJoin(Object left, String right, Class<? extends ArelNodeJoin> constraint) {
        super(left, right, constraint);
    }
}

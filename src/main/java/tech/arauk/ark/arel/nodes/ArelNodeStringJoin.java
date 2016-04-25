package tech.arauk.ark.arel.nodes;

public class ArelNodeStringJoin extends ArelNodeJoin {
    public ArelNodeStringJoin(Object left) {
        super(left, null);
    }

    public ArelNodeStringJoin(Object left, Object right) {
        super(left, right);
    }
}

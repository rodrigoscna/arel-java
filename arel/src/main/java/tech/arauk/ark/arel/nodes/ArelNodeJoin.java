package tech.arauk.ark.arel.nodes;

public class ArelNodeJoin extends ArelNodeBinary {
    public Class<? extends ArelNodeJoin> constraint;

    public ArelNodeJoin(Object left, String right, Class<? extends ArelNodeJoin> constraint) {
        super(left, right);
        this.constraint = constraint;
    }
}

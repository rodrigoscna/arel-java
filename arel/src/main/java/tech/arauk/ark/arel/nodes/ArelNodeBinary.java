package tech.arauk.ark.arel.nodes;

public class ArelNodeBinary extends ArelNode {
    public Object left;
    public Object right;

    public ArelNodeBinary(Object left, Object right) {
        super();
        this.left = left;
        this.right = right;
    }
}

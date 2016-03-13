package tech.arauk.ark.arel.nodes;

public class ArelNodeBinary extends ArelNode {
    private Object left;
    private Object right;

    public ArelNodeBinary(Object left, Object right) {
        super();
        this.left = left;
        this.right = right;
    }

    public Object left() {
        return this.left;
    }

    public void left(Object left) {
        this.left = left;
    }

    public Object right() {
        return this.right;
    }

    public void right(Object right) {
        this.right = right;
    }
}

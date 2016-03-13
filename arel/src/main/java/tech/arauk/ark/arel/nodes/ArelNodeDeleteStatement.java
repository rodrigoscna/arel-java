package tech.arauk.ark.arel.nodes;

import java.util.ArrayList;

public class ArelNodeDeleteStatement extends ArelNodeBinary {
    public Object limit;

    public ArelNodeDeleteStatement() {
        this(null, new ArrayList<>());
    }

    public ArelNodeDeleteStatement(Object left, Object right) {
        super(left, right);
    }

    public Object relation() {
        return this.left();
    }

    public void relation(Object left) {
        this.left(left);
    }

    public Object wheres() {
        return this.right();
    }

    public void wheres(Object right) {
        this.right(right);
    }
}

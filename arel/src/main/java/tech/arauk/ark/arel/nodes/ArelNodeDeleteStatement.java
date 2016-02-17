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
        return this.left;
    }

    public Object relation(Object left) {
        return this.left = left;
    }

    public Object wheres() {
        return this.right;
    }

    public Object wheres(Object right) {
        return this.right = right;
    }
}

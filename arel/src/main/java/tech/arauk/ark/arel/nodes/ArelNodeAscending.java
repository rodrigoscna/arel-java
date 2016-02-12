package tech.arauk.ark.arel.nodes;

public class ArelNodeAscending extends ArelNodeUnary {
    public ArelNodeAscending(Object expr) {
        super(expr);
    }

    public ArelNodeDescending reverse() {
        return new ArelNodeDescending(this.expr);
    }

    public String direction() {
        return "asc";
    }

    public boolean isAscending() {
        return true;
    }

    public boolean isDescending() {
        return false;
    }
}

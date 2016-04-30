package tech.arauk.ark.arel.nodes;

import tech.arauk.ark.arel.annotations.Beta;

@Beta
public class ArelNodeDescending extends ArelNodeUnary {
    public ArelNodeDescending(Object expr) {
        super(expr);
    }

    public ArelNodeAscending reverse() {
        return new ArelNodeAscending(this.expr());
    }

    public String direction() {
        return "desc";
    }

    public boolean isAscending() {
        return false;
    }

    public boolean isDescending() {
        return true;
    }
}

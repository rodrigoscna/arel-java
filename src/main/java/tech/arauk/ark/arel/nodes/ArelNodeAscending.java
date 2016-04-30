package tech.arauk.ark.arel.nodes;

import tech.arauk.ark.arel.annotations.Beta;

@Beta
public class ArelNodeAscending extends ArelNodeUnary {
    public ArelNodeAscending(Object expr) {
        super(expr);
    }

    public ArelNodeDescending reverse() {
        return new ArelNodeDescending(this.expr());
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

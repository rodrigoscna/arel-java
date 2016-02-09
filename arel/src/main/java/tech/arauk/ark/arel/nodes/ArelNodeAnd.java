package tech.arauk.ark.arel.nodes;

import java.util.List;

public class ArelNodeAnd extends ArelNode {
    public List<Object> children;

    public ArelNodeAnd(List<Object> children) {
        this.children = children;
    }

    public Object left() {
        return this.children.get(0);
    }

    public Object right() {
        return this.children.get(1);
    }
}

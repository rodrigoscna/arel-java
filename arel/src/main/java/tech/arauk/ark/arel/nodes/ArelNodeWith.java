package tech.arauk.ark.arel.nodes;

import java.util.List;

public class ArelNodeWith extends ArelNodeUnary {
    public ArelNodeWith(Object expr) {
        super(expr);
    }

    public List<Object> children() {
        return (List<Object>) expr();
    }
}

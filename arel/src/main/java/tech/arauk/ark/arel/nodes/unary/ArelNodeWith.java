package tech.arauk.ark.arel.nodes.unary;

import tech.arauk.ark.arel.nodes.ArelNodeUnary;

import java.util.List;

public class ArelNodeWith extends ArelNodeUnary {
    public ArelNodeWith(Object expr) {
        super(expr);
    }

    public List<Object> children() {
        return (List<Object>) expr;
    }
}

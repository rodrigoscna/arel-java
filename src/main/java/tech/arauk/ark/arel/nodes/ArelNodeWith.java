package tech.arauk.ark.arel.nodes;

import tech.arauk.ark.arel.annotations.Beta;

import java.util.List;

@Beta
public class ArelNodeWith extends ArelNodeUnary {
    public ArelNodeWith(Object expr) {
        super(expr);
    }

    public List<Object> children() {
        return (List<Object>) expr();
    }
}

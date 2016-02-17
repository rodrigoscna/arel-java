package tech.arauk.ark.arel.nodes;

import tech.arauk.ark.arel.ArelRelation;
import tech.arauk.ark.arel.attributes.ArelAttribute;

public class ArelNodeUnqualifiedColumn extends ArelNodeUnary {
    public ArelNodeUnqualifiedColumn(Object expr) {
        super(expr);
    }

    public ArelRelation relation() {
        return ((ArelAttribute) this.expr).relation;
    }

    public String name() {
        return ((ArelAttribute) this.expr).name;
    }
}

package tech.arauk.ark.arel.nodes;

import tech.arauk.ark.arel.attributes.ArelAttribute;

public class ArelNodeCasted extends ArelNode {
    public Object val;
    public ArelAttribute attribute;

    public ArelNodeCasted(Object val, ArelAttribute attribute) {
        this.val = val;
        this.attribute = attribute;
    }
}

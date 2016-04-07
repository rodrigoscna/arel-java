package tech.arauk.ark.arel.nodes;

import tech.arauk.ark.arel.attributes.ArelAttribute;

import java.util.Objects;

public class ArelNodeCasted extends ArelNode {
    public Object val;
    public ArelAttribute attribute;

    public ArelNodeCasted(Object val, ArelAttribute attribute) {
        this.val = val;
        this.attribute = attribute;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return this.val == null;
        } else if (other instanceof ArelNodeCasted) {
            return Objects.deepEquals(this.val, ((ArelNodeCasted) other).val) && Objects.deepEquals(this.attribute, ((ArelNodeCasted) other).attribute);
        }

        return super.equals(other);
    }
}

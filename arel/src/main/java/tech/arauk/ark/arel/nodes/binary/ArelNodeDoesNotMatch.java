package tech.arauk.ark.arel.nodes.binary;

import tech.arauk.ark.arel.ArelNodes;
import tech.arauk.ark.arel.nodes.ArelNodeBinary;

public class ArelNodeDoesNotMatch extends ArelNodeBinary {
    private Object escape;

    public ArelNodeDoesNotMatch(Object left, Object right) {
        this(left, right, null);
    }

    public ArelNodeDoesNotMatch(Object left, Object right, Object escape) {
        super(left, right);

        if (escape != null) {
            this.escape = ArelNodes.buildQuoted(escape);
        }
    }

    public Object escape() {
        return this.escape;
    }
}

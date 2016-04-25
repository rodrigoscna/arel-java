package tech.arauk.ark.arel.nodes;

import tech.arauk.ark.arel.ArelNodes;

public class ArelNodeMatches extends ArelNodeBinary {
    private Object escape;

    public ArelNodeMatches(Object left, Object right) {
        this(left, right, null);
    }

    public ArelNodeMatches(Object left, Object right, Object escape) {
        super(left, right);

        if (escape != null) {
            this.escape = ArelNodes.buildQuoted(escape);
        }
    }

    public Object escape() {
        return this.escape;
    }
}

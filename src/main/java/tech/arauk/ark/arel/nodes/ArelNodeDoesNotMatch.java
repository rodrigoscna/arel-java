package tech.arauk.ark.arel.nodes;

import tech.arauk.ark.arel.ArelNodes;
import tech.arauk.ark.arel.annotations.Beta;

@Beta
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

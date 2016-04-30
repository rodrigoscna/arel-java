package tech.arauk.ark.arel.nodes;

import tech.arauk.ark.arel.annotations.Beta;

@Beta
public class ArelNodeAs extends ArelNodeBinary {
    public ArelNodeAs(Object left, Object right) {
        super(left, right);
    }
}

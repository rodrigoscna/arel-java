package tech.arauk.ark.arel.nodes;

import tech.arauk.ark.arel.annotations.Beta;

@Beta
public class ArelNodeRegexp extends ArelNodeBinary {
    public ArelNodeRegexp(Object left, Object right) {
        super(left, right);
    }
}

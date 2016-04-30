package tech.arauk.ark.arel.nodes;

import tech.arauk.ark.arel.annotations.Beta;

@Beta
public class ArelNodeNotRegexp extends ArelNodeBinary {
    public ArelNodeNotRegexp(Object left, Object right) {
        super(left, right);
    }
}

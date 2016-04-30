package tech.arauk.ark.arel.nodes;

import tech.arauk.ark.arel.ArelAliasPredications;
import tech.arauk.ark.arel.ArelAliasPredicationsInterface;
import tech.arauk.ark.arel.annotations.Beta;

@Beta
public class ArelNodeOver extends ArelNodeBinary implements ArelAliasPredicationsInterface {
    public ArelNodeOver(Object left) {
        this(left, null);
    }

    public ArelNodeOver(Object left, Object right) {
        super(left, right);
    }

    @Override
    public ArelNodeAs as(Object other) {
        return ArelAliasPredications.as(this, other);
    }

    public String operator() {
        return "OVER";
    }
}

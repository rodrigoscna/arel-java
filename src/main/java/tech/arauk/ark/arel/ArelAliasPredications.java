package tech.arauk.ark.arel;

import tech.arauk.ark.arel.annotations.Beta;
import tech.arauk.ark.arel.nodes.ArelNodeSqlLiteral;
import tech.arauk.ark.arel.nodes.ArelNodeAs;

@Beta
public abstract class ArelAliasPredications {
    public static ArelNodeAs as(Object holder, Object other) {
        return new ArelNodeAs(holder, new ArelNodeSqlLiteral(other));
    }
}

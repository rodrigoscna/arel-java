package tech.arauk.ark.arel;

import tech.arauk.ark.arel.nodes.ArelNodeSqlLiteral;
import tech.arauk.ark.arel.nodes.ArelNodeAs;

public abstract class ArelAliasPredications {
    public static ArelNodeAs as(Object holder, Object other) {
        return new ArelNodeAs(holder, new ArelNodeSqlLiteral(other));
    }
}

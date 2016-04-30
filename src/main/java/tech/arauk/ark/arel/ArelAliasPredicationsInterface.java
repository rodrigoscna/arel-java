package tech.arauk.ark.arel;

import tech.arauk.ark.arel.annotations.Beta;
import tech.arauk.ark.arel.nodes.ArelNodeAs;

@Beta
public interface ArelAliasPredicationsInterface {
    ArelNodeAs as(Object other);
}

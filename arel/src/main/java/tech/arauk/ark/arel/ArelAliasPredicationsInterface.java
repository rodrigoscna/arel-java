package tech.arauk.ark.arel;

import tech.arauk.ark.arel.nodes.binary.ArelNodeAs;

public interface ArelAliasPredicationsInterface {
    ArelNodeAs as(Object other);
}

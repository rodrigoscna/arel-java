package tech.arauk.ark.arel.interfaces;

import tech.arauk.ark.arel.annotations.Beta;

@Beta
public interface ArelDistinctInterface {
    Boolean distinct();

    ArelDistinctInterface distinct(Boolean distinct);
}

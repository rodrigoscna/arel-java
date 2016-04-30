package tech.arauk.ark.arel.interfaces;

import tech.arauk.ark.arel.annotations.Beta;

import java.util.List;

@Beta
public interface ArelHavingsInterface {
    List<Object> havings();

    ArelHavingsInterface havings(List<Object> havings);
}

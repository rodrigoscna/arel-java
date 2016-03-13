package tech.arauk.ark.arel.interfaces;

import java.util.List;

public interface ArelHavingsInterface {
    List<Object> havings();

    ArelHavingsInterface havings(List<Object> havings);
}

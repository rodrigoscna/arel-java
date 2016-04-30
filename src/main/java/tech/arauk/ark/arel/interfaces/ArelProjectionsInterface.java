package tech.arauk.ark.arel.interfaces;

import tech.arauk.ark.arel.annotations.Beta;

import java.util.List;

@Beta
public interface ArelProjectionsInterface {
    List<Object> projections();

    ArelProjectionsInterface projections(Object projections);
}

package tech.arauk.ark.arel.interfaces;

import java.util.List;

public interface ArelProjectionsInterface {
    List<Object> projections();

    ArelProjectionsInterface projections(List<Object> projections);
}

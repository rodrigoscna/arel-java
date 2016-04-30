package tech.arauk.ark.arel.interfaces;

import tech.arauk.ark.arel.annotations.Beta;
import tech.arauk.ark.arel.nodes.ArelNodeOffset;

@Beta
public interface ArelOffsetInterface {
    ArelNodeOffset offset();

    ArelOffsetInterface offset(Object offset);
}

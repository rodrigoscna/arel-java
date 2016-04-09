package tech.arauk.ark.arel.interfaces;

import tech.arauk.ark.arel.nodes.unary.ArelNodeOffset;

public interface ArelOffsetInterface {
    ArelNodeOffset offset();

    ArelOffsetInterface offset(ArelNodeOffset offset);
}

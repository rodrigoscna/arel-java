package tech.arauk.ark.arel;

import tech.arauk.ark.arel.nodes.ArelNodeOver;

public interface ArelWindowPredicationsInterface {
    ArelNodeOver over();

    ArelNodeOver over(Object expr);
}

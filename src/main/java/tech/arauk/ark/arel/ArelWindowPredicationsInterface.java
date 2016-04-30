package tech.arauk.ark.arel;

import tech.arauk.ark.arel.annotations.Beta;
import tech.arauk.ark.arel.nodes.ArelNodeOver;

@Beta
public interface ArelWindowPredicationsInterface {
    ArelNodeOver over();

    ArelNodeOver over(Object expr);
}

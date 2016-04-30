package tech.arauk.ark.arel.interfaces;

import tech.arauk.ark.arel.annotations.Beta;
import tech.arauk.ark.arel.nodes.ArelNode;

@Beta
public interface ArelSetQuantifierInterface {
    ArelNode setQuantifier();

    ArelSetQuantifierInterface setQuantifier(ArelNode setQuantifier);
}

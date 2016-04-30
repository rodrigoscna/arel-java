package tech.arauk.ark.arel.interfaces;

import tech.arauk.ark.arel.annotations.Beta;
import tech.arauk.ark.arel.nodes.ArelNodeWith;

@Beta
public interface ArelWithInterface {
    ArelNodeWith with();

    ArelWithInterface with(Object with);
}

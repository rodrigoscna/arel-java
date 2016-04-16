package tech.arauk.ark.arel.interfaces;

import tech.arauk.ark.arel.nodes.ArelNodeWith;

public interface ArelWithInterface {
    ArelNodeWith with();

    ArelWithInterface with(Object with);
}

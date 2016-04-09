package tech.arauk.ark.arel.interfaces;

import tech.arauk.ark.arel.nodes.unary.ArelNodeWith;

public interface ArelWithInterface {
    ArelNodeWith with();

    ArelWithInterface with(ArelNodeWith with);
}

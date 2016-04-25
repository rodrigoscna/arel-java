package tech.arauk.ark.arel.interfaces;

import tech.arauk.ark.arel.nodes.ArelNodeJoinSource;

public interface ArelSourceInterface {
    ArelNodeJoinSource source();

    ArelSourceInterface source(ArelNodeJoinSource source);
}

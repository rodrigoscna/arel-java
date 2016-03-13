package tech.arauk.ark.arel.interfaces;

import tech.arauk.ark.arel.nodes.binary.ArelNodeJoinSource;

public interface ArelSourceInterface {
    ArelNodeJoinSource source();

    ArelSourceInterface source(ArelNodeJoinSource source);
}

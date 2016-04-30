package tech.arauk.ark.arel.interfaces;

import tech.arauk.ark.arel.annotations.Beta;
import tech.arauk.ark.arel.nodes.ArelNodeJoinSource;

@Beta
public interface ArelSourceInterface {
    ArelNodeJoinSource source();

    ArelSourceInterface source(ArelNodeJoinSource source);
}

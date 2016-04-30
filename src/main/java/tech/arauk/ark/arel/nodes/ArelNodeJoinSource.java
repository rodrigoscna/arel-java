package tech.arauk.ark.arel.nodes;

import tech.arauk.ark.arel.annotations.Beta;

import java.util.ArrayList;
import java.util.List;

@Beta
public class ArelNodeJoinSource extends ArelNodeBinary {
    public ArelNodeJoinSource(Object singleSource) {
        super(singleSource, new ArrayList<>());
    }

    public ArelNodeJoinSource(Object singleSource, List<Object> joinOp) {
        super(singleSource, joinOp);
    }

    public boolean isEmpty() {
        return (left() == null) && (right() == null || ((List<Object>) right()).isEmpty());
    }
}

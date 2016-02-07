package tech.arauk.ark.arel.nodes;

import tech.arauk.ark.arel.nodes.binary.ArelNodeJoinSource;
import tech.arauk.ark.arel.nodes.unary.ArelNodeTop;

import java.util.ArrayList;
import java.util.List;

public class ArelNodeSelectCore extends ArelNode {
    public ArelNodeJoinSource source;
    public ArelNodeTop top;
    public ArelNode setQuantifier;
    public Object[] projections;
    public Object[] wheres;
    public Object[] groups;
    public List<Object> havings;
    public Object[] windows;

    public ArelNodeSelectCore() {
        super();
        this.havings = new ArrayList<>();
        this.source = new ArelNodeJoinSource(null);
    }
}

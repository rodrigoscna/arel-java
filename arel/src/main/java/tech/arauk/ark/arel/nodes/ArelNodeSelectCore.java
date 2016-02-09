package tech.arauk.ark.arel.nodes;

import tech.arauk.ark.arel.nodes.binary.ArelNodeJoinSource;
import tech.arauk.ark.arel.nodes.unary.ArelNodeTop;

import java.util.ArrayList;
import java.util.List;

public class ArelNodeSelectCore extends ArelNode {
    public ArelNodeJoinSource source;
    public ArelNodeTop top;
    public ArelNode setQuantifier;
    public List<Object> groups;
    public List<Object> havings;
    public List<Object> projections;
    public List<Object> wheres;
    public Object[] windows;

    public ArelNodeSelectCore() {
        super();

        this.groups = new ArrayList<>();
        this.havings = new ArrayList<>();
        this.projections = new ArrayList<>();
        this.source = new ArelNodeJoinSource(null);
        this.wheres = new ArrayList<>();
    }
}

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
    public List<Object> windows;
    public List<Object> wheres;

    public ArelNodeSelectCore() {
        super();

        this.groups = new ArrayList<>();
        this.havings = new ArrayList<>();
        this.projections = new ArrayList<>();
        this.source = new ArelNodeJoinSource(null);
        this.windows = new ArrayList<>();
        this.wheres = new ArrayList<>();
    }

    public Object from() {
        return source.left;
    }

    public ArelNodeSelectCore from(Object value) {
        this.source.left = value;

        return this;
    }
}

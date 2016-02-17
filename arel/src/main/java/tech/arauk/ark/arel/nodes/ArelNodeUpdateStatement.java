package tech.arauk.ark.arel.nodes;

import tech.arauk.ark.arel.nodes.unary.ArelNodeLimit;

import java.util.ArrayList;
import java.util.List;

public class ArelNodeUpdateStatement extends ArelNode {
    public Object relation;
    public List<Object> wheres;
    public List<Object> values;
    public List<Object> orders;
    public ArelNodeLimit limit;
    public Object key;

    public ArelNodeUpdateStatement() {
        this.key = null;
        this.limit = null;
        this.orders = new ArrayList<>();
        this.relation = null;
        this.values = new ArrayList<>();
        this.wheres = new ArrayList<>();
    }
}

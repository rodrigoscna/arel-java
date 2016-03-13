package tech.arauk.ark.arel.nodes;

import tech.arauk.ark.arel.ArelRelation;

import java.util.ArrayList;
import java.util.List;

public class ArelNodeInsertStatement extends ArelNodeStatement {
    public List<String> columns;
    public ArelRelation relation;
    public Object select;
    public Object values;

    public ArelNodeInsertStatement() {
        super();

        this.relation = null;
        this.columns = new ArrayList<>();
        this.values = null;
        this.select = null;
    }
}

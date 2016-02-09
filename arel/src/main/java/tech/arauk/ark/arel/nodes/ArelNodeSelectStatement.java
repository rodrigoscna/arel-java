package tech.arauk.ark.arel.nodes;

import tech.arauk.ark.arel.nodes.unary.ArelNodeLimit;
import tech.arauk.ark.arel.nodes.unary.ArelNodeLock;
import tech.arauk.ark.arel.nodes.unary.ArelNodeOffset;
import tech.arauk.ark.arel.nodes.unary.ArelNodeWith;

import java.util.ArrayList;
import java.util.List;

public class ArelNodeSelectStatement extends ArelNodeStatement {
    public ArelNodeSelectCore[] cores;
    public ArelNodeLimit limit;
    public ArelNodeLock lock;
    public ArelNodeOffset offset;
    public List<Object> orders;
    public ArelNodeWith with;

    public ArelNodeSelectStatement() {
        this(new ArelNodeSelectCore[]{new ArelNodeSelectCore()});
    }

    public ArelNodeSelectStatement(ArelNodeSelectCore[] cores) {
        super();

        this.cores = cores;
        this.orders = new ArrayList<>();
    }
}

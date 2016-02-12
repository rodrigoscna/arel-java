package tech.arauk.ark.arel.nodes;

import tech.arauk.ark.arel.ArelTable;
import tech.arauk.ark.arel.collectors.ArelCollector;
import tech.arauk.ark.arel.visitors.ArelVisitor;

import java.util.ArrayList;
import java.util.List;

public class ArelNode {
    public ArelNode and(Object right) {
        List<Object> and = new ArrayList<>();
        and.add(this);
        and.add(right);
        return new ArelNodeAnd(and);
    }

    public String toSQL() {
        ArelVisitor visitor = ArelTable.engine;
        return toSQL(visitor);
    }

    public String toSQL(ArelVisitor visitor) {
        ArelCollector collector = new ArelCollector();
        collector = visitor.connection.getVisitor().accept(this, collector);
        return collector.getValue();
    }
}

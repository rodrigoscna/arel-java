package tech.arauk.ark.arel;

import tech.arauk.ark.arel.collectors.ArelCollector;
import tech.arauk.ark.arel.nodes.ArelNode;
import tech.arauk.ark.arel.nodes.ArelNodeSelectCore;
import tech.arauk.ark.arel.visitors.ArelVisitor;

public class ArelTreeManager {
    public ArelNode ast;
    public ArelNodeSelectCore ctx;

    public ArelTreeManager(ArelNode ast) {
        this.ast = ast;
    }

    public String toSQL() {
        ArelVisitor visitor = ArelTable.engine;
        return toSQL(visitor);
    }

    public String toSQL(ArelVisitor visitor) {
        ArelCollector collector = new ArelCollector();
        collector = visitor.connection.getVisitor().accept(this.ast, collector);
        return collector.getValue();
    }

    public ArelTreeManager where(Object expr) {
        if (expr instanceof ArelTreeManager) {
            expr = ((ArelTreeManager) expr).ast;
        }

        this.ctx.wheres.add(expr);

        return this;
    }
}

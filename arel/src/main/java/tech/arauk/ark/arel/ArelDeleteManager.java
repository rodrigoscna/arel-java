package tech.arauk.ark.arel;

import tech.arauk.ark.arel.nodes.ArelNodeDeleteStatement;
import tech.arauk.ark.arel.nodes.unary.ArelNodeLimit;

import java.util.List;

public class ArelDeleteManager extends ArelTreeManager {
    public ArelDeleteManager() {
        super(new ArelNodeDeleteStatement());

        ctx(ast());
    }

    public ArelDeleteManager from(Object relation) {
        ArelNodeDeleteStatement ast = (ArelNodeDeleteStatement) ast();
        ast.relation(relation);

        return this;
    }

    public ArelDeleteManager take(Object limit) {
        ArelNodeDeleteStatement ast = (ArelNodeDeleteStatement) ast();
        if (limit != null) {
            ast.limit = new ArelNodeLimit(ArelNodes.buildQuoted(limit));
        }

        return this;
    }

    public ArelDeleteManager wheres(List<Object> list) {
        ArelNodeDeleteStatement ast = (ArelNodeDeleteStatement) ast();
        ast.wheres(list);

        return this;
    }
}

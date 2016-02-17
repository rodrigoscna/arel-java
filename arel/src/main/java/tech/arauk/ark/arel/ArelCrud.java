package tech.arauk.ark.arel;

import tech.arauk.ark.arel.attributes.ArelAttribute;
import tech.arauk.ark.arel.nodes.ArelNodeSelectStatement;
import tech.arauk.ark.arel.nodes.ArelNodeSqlLiteral;

import java.util.Iterator;
import java.util.Map;

public abstract class ArelCrud {
    public static ArelInsertManager createInsert() {
        return new ArelInsertManager();
    }

    public static ArelDeleteManager compileDelete(ArelSelectManager holder) {
        ArelNodeSelectStatement ast = ((ArelNodeSelectStatement) holder.ast);

        ArelDeleteManager deleteManager = new ArelDeleteManager();
        if (ast.limit != null) {
            deleteManager.take(ast.limit.expr);
        }
        deleteManager.wheres(holder.ctx.wheres);
        deleteManager.from(holder.ctx.from());

        return deleteManager;
    }

    public static ArelUpdateManager compileUpdate(ArelSelectManager holder, Object values, ArelAttribute pk) {
        ArelNodeSelectStatement ast = ((ArelNodeSelectStatement) holder.ast);

        ArelUpdateManager updateManager = new ArelUpdateManager();

        Object relation = null;
        if (values instanceof ArelNodeSqlLiteral) {
            relation = holder.ctx.from();
        } else {
            Map<Object, Object> valuesMap = (Map<Object, Object>) values;
            Iterator iterator = valuesMap.keySet().iterator();
            if (iterator.hasNext()) {
                relation = ((ArelAttribute) iterator.next()).relation;
            }
        }

        updateManager.key(pk);
        updateManager.table(relation);
        updateManager.set(values);
        if (ast.limit != null) {
            updateManager.take(ast.limit.expr);
        }
        updateManager.order(ast.orders);
        updateManager.wheres(holder.ctx.wheres);

        return updateManager;
    }
}

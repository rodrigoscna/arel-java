package tech.arauk.ark.arel;

import tech.arauk.ark.arel.attributes.ArelAttribute;
import tech.arauk.ark.arel.nodes.ArelNodeSelectStatement;
import tech.arauk.ark.arel.nodes.ArelNodeSqlLiteral;

import java.util.Iterator;
import java.util.Map;

public abstract class ArelCrud {
    public static ArelDeleteManager compileDelete(Object holder) {
        if (holder instanceof ArelSelectManager) {
            ArelSelectManager holderNode = (ArelSelectManager) holder;

            ArelNodeSelectStatement ast = ((ArelNodeSelectStatement) holderNode.ast);

            ArelDeleteManager deleteManager = new ArelDeleteManager();
            if (ast.limit != null) {
                deleteManager.take(ast.limit.expr);
            }
            deleteManager.wheres(holderNode.ctx.wheres);
            deleteManager.from(holderNode.ctx.from());

            return deleteManager;
        } else if (holder instanceof ArelTable) {
            ArelTable holderNode = (ArelTable) holder;

            ArelDeleteManager deleteManager = new ArelDeleteManager();
            deleteManager.from(holderNode.from());

            return deleteManager;
        } else {
            return null;
        }
    }

    public static ArelInsertManager compileInsert(String values) {
        ArelInsertManager insertManager = createInsert();
        insertManager.insert(values);
        return insertManager;
    }

    public static ArelUpdateManager compileUpdate(Object holder, Object values, ArelAttribute pk) {
        if (holder instanceof ArelSelectManager) {
            ArelSelectManager holderNode = (ArelSelectManager) holder;

            ArelNodeSelectStatement ast = ((ArelNodeSelectStatement) holderNode.ast);

            ArelUpdateManager updateManager = new ArelUpdateManager();

            Object relation = null;
            if (values instanceof ArelNodeSqlLiteral) {
                relation = holderNode.ctx.from();
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
            updateManager.wheres(holderNode.ctx.wheres);

            return updateManager;
        } else if (holder instanceof ArelTable) {
            ArelTable holderNode = (ArelTable) holder;

            ArelUpdateManager updateManager = new ArelUpdateManager();

            Object relation = null;
            if (values instanceof ArelNodeSqlLiteral) {
                relation = holderNode.from();
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

            return updateManager;
        } else {
            return null;
        }
    }

    public static ArelInsertManager createInsert() {
        return new ArelInsertManager();
    }
}

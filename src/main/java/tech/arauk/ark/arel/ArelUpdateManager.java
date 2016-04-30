package tech.arauk.ark.arel;

import tech.arauk.ark.arel.annotations.Beta;
import tech.arauk.ark.arel.interfaces.*;
import tech.arauk.ark.arel.nodes.ArelNodeSqlLiteral;
import tech.arauk.ark.arel.nodes.ArelNodeUnqualifiedColumn;
import tech.arauk.ark.arel.nodes.ArelNodeUpdateStatement;
import tech.arauk.ark.arel.nodes.ArelNodeAssignment;
import tech.arauk.ark.arel.nodes.ArelNodeLimit;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Beta
public class ArelUpdateManager extends ArelTreeManager {
    public ArelUpdateManager() {
        super(new ArelNodeUpdateStatement());

        ctx(ast());
    }

    public Object key() {
        return ((ArelKeyInterface) ast()).key();
    }

    public ArelUpdateManager key(Object key) {
        ((ArelKeyInterface) ast()).key(ArelNodes.buildQuoted(key));

        return this;
    }

    public ArelUpdateManager table(Object table) {
        ((ArelRelationInterface) ast()).relation(table);

        return this;
    }

    public ArelUpdateManager set(Object values) {
        List<Object> valuesList = new ArrayList<>();

        if (values instanceof String || values instanceof ArelNodeSqlLiteral) {
            valuesList.add(values);
        } else if (values instanceof Object[][]) {
            for (Object[] object : (Object[][]) values) {
                valuesList.add(new ArelNodeAssignment(new ArelNodeUnqualifiedColumn(object[0]), object[1]));
            }
        } else {
            Map<Object, Object> valuesMap = (Map<Object, Object>) values;
            for (Object column : valuesMap.keySet()) {
                valuesList.add(new ArelNodeAssignment(new ArelNodeUnqualifiedColumn(column), valuesMap.get(column)));
            }
        }

        ((ArelValuesInterface) ast()).values(valuesList);

        return this;
    }

    public ArelUpdateManager take(Object limit) {
        if (limit != null) {
            ((ArelLimitInterface) ast()).limit(new ArelNodeLimit(ArelNodes.buildQuoted(limit)));
        }

        return this;
    }

    public ArelUpdateManager order(List<Object> expr) {
        ((ArelOrdersInterface) ast()).orders(expr);

        return this;
    }

    public ArelUpdateManager wheres(List<Object> wheres) {
        ((ArelWheresInterface) ast()).wheres(wheres);

        return this;
    }
}

package tech.arauk.ark.arel;

import tech.arauk.ark.arel.nodes.ArelNodeSqlLiteral;
import tech.arauk.ark.arel.nodes.ArelNodeUnqualifiedColumn;
import tech.arauk.ark.arel.nodes.ArelNodeUpdateStatement;
import tech.arauk.ark.arel.nodes.binary.ArelNodeAssignment;
import tech.arauk.ark.arel.nodes.unary.ArelNodeLimit;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ArelUpdateManager extends ArelTreeManager {
    public ArelUpdateManager() {
        super(new ArelNodeUpdateStatement());
    }

    public ArelUpdateManager key(Object key) {
        ((ArelNodeUpdateStatement) ast()).key = ArelNodes.buildQuoted(key);

        return this;
    }

    public ArelUpdateManager table(Object table) {
        ((ArelNodeUpdateStatement) ast()).relation = table;

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

        ((ArelNodeUpdateStatement) ast()).values = valuesList;

        return this;
    }

    public ArelUpdateManager take(Object limit) {
        if (limit != null) {
            ((ArelNodeUpdateStatement) ast()).limit = new ArelNodeLimit(ArelNodes.buildQuoted(limit));
        }

        return this;
    }

    public ArelUpdateManager order(List<Object> expr) {
        ((ArelNodeUpdateStatement) ast()).orders = expr;

        return this;
    }

    public ArelUpdateManager wheres(List<Object> wheres) {
        ((ArelNodeUpdateStatement) ast()).wheres = wheres;

        return this;
    }
}

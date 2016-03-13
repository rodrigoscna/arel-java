package tech.arauk.ark.arel;

import tech.arauk.ark.arel.attributes.ArelAttribute;
import tech.arauk.ark.arel.nodes.ArelNodeInsertStatement;
import tech.arauk.ark.arel.nodes.ArelNodeSqlLiteral;
import tech.arauk.ark.arel.nodes.binary.ArelNodeValues;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ArelInsertManager extends ArelTreeManager {
    public ArelInsertManager() {
        super(new ArelNodeInsertStatement());
    }

    public ArelInsertManager into(ArelTable table) {
        ((ArelNodeInsertStatement) this.ast).relation = table;
        return this;
    }

    public void insert(Object fields) {
        if (fields == null) {
            return;
        }

        ArelNodeInsertStatement insertStatement = (ArelNodeInsertStatement) this.ast;

        if (fields instanceof String) {
            insertStatement.values = new ArelNodeSqlLiteral(fields);
        } else if (fields instanceof ArelNodeSqlLiteral) {
            insertStatement.values = fields;
        } else {
            Map<Object, Object> valuesMap;
            Iterator iterator;

            valuesMap = (Map<Object, Object>) fields;
            iterator = valuesMap.keySet().iterator();
            if (iterator.hasNext()) {
                insertStatement.relation = ((ArelAttribute) iterator.next()).relation;
            }

            List<Object> values = new ArrayList<>();

            iterator = valuesMap.keySet().iterator();
            while (iterator.hasNext()) {
                Object column = iterator.next();
                Object value = valuesMap.get(column);

                insertStatement.columns.add(String.valueOf(column));
                values.add(value);
            }

            insertStatement.values = createValues(values, insertStatement.columns);
        }
    }

    private Object createValues(List<Object> values, List<String> columns) {
        return new ArelNodeValues(values, columns);
    }
}

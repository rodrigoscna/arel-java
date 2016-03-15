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

    public List<Object> columns() {
        return ((ArelNodeInsertStatement) ast()).columns;
    }

    public ArelNodeValues createValues(List<Object> values, List<Object> columns) {
        return new ArelNodeValues(values, columns);
    }

    public void insert(Object fields) {
        if (fields == null) {
            return;
        }

        ArelNodeInsertStatement insertStatement = (ArelNodeInsertStatement) ast();

        if (fields instanceof String) {
            insertStatement.values = new ArelNodeSqlLiteral(fields);
        } else if (fields instanceof ArelNodeSqlLiteral) {
            insertStatement.values = fields;
        } else if (fields instanceof Object[][]) {
            Object[][] valuesObjects = (Object[][]) fields;

            if (valuesObjects.length == 0) {
                return;
            }

            insertStatement.relation = ((ArelAttribute) valuesObjects[0][0]).relation;

            List<Object> values = new ArrayList<>();

            for (Object[] object : valuesObjects) {
                insertStatement.columns.add(object[0]);
                values.add(object[1]);
            }

            insertStatement.values = createValues(values, insertStatement.columns);
        } else {
            Map<Object, Object> valuesMap = (Map<Object, Object>) fields;

            if (valuesMap.isEmpty()) {
                return;
            }

            Iterator iterator;

            iterator = valuesMap.keySet().iterator();
            if (iterator.hasNext()) {
                insertStatement.relation = ((ArelAttribute) iterator.next()).relation;
            } else {
                return;
            }

            List<Object> values = new ArrayList<>();

            iterator = valuesMap.keySet().iterator();
            while (iterator.hasNext()) {
                Object column = iterator.next();
                Object value = valuesMap.get(column);

                insertStatement.columns.add(column);
                values.add(value);
            }

            insertStatement.values = createValues(values, insertStatement.columns);
        }
    }

    public ArelInsertManager into(ArelTable table) {
        ((ArelNodeInsertStatement) ast()).relation = table;
        return this;
    }

    public void select(Object select) {
        ((ArelNodeInsertStatement) ast()).select = select;
    }

    public void values(ArelNodeValues values) {
        ((ArelNodeInsertStatement) ast()).values = values;
    }
}

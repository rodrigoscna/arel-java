package tech.arauk.ark.arel;

import tech.arauk.ark.arel.attributes.ArelAttribute;
import tech.arauk.ark.arel.nodes.ArelNodeNamedFunction;

public interface ArelRelation {
    ArelAttribute get(String name);

    boolean isAbleToTypeCast();

    ArelNodeNamedFunction lower(Object column);

    Object typeCastForDatabase(String name, Object value);

    Object tableAlias();

    Object tableName();
}

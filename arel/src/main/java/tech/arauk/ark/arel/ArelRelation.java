package tech.arauk.ark.arel;

import tech.arauk.ark.arel.attributes.ArelAttribute;

public interface ArelRelation {
    ArelAttribute get(String name);

    boolean isAbleToTypeCast();

    Object typeCastForDatabase(String name, Object value);

    Object tableAlias();

    Object tableName();
}

package tech.arauk.ark.arel;

import tech.arauk.ark.arel.annotations.Beta;
import tech.arauk.ark.arel.attributes.ArelAttribute;
import tech.arauk.ark.arel.nodes.ArelNodeNamedFunction;
import tech.arauk.ark.arel.nodes.ArelNodeSqlLiteral;

@Beta
public interface ArelRelation {
    ArelAttribute get(String name);

    ArelAttribute get(ArelNodeSqlLiteral name);

    boolean isAbleToTypeCast();

    ArelNodeNamedFunction lower(Object column);

    Object name();

    ArelRelation name(Object tableName);

    Object tableAlias();

    ArelRelation tableAlias(Object tableAlias);

    Object tableName();

    ArelRelation tableName(Object tableName);

    Object typeCastForDatabase(Object name, Object value);
}

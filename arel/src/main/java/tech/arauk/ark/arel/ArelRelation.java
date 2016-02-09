package tech.arauk.ark.arel;

public interface ArelRelation {
    boolean isAbleToTypeCast();

    Object typeCastForDatabase(String name, Object value);

    Object tableAlias();

    Object tableName();
}

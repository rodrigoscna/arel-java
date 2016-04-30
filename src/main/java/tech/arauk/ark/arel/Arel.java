package tech.arauk.ark.arel;

import tech.arauk.ark.arel.annotations.Beta;
import tech.arauk.ark.arel.nodes.ArelNodeSqlLiteral;

@Beta
public class Arel {
    public static ArelNodeSqlLiteral sql(String rawSql) {
        return new ArelNodeSqlLiteral(rawSql);
    }

    public static ArelNodeSqlLiteral star() {
        return sql("*");
    }
}

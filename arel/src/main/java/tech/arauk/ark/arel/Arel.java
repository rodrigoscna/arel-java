package tech.arauk.ark.arel;

import tech.arauk.ark.arel.nodes.ArelNodeSqlLiteral;

public class Arel {
    public static ArelNodeSqlLiteral sql(String rawSql) {
        return new ArelNodeSqlLiteral(rawSql);
    }

    public static String star() {
        return "*";
    }
}

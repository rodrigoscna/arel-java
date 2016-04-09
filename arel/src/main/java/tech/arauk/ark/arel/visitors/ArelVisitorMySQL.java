package tech.arauk.ark.arel.visitors;

import tech.arauk.ark.arel.collectors.ArelCollector;
import tech.arauk.ark.arel.connection.ArelConnection;
import tech.arauk.ark.arel.nodes.unary.ArelNodeBin;

public class ArelVisitorMySQL extends ArelVisitorToSql {
    public static final String BINARY = "BINARY ";

    public ArelVisitorMySQL(ArelConnection arelConnection) {
        super(arelConnection);
    }

    @Override
    public ArelCollector visitArelNodeBin(ArelNodeBin bin, ArelCollector collector) {
        collector.append(BINARY);
        collector = visit(bin.expr(), collector);

        return collector;
    }
}

package tech.arauk.ark.arel.visitors;

import tech.arauk.ark.arel.collectors.ArelCollector;
import tech.arauk.ark.arel.connection.ArelConnection;
import tech.arauk.ark.arel.nodes.ArelNodeSelectCore;

public class ArelVisitorWhereSql extends ArelVisitorToSql {
    public ArelVisitorWhereSql(ArelConnection arelConnection) {
        super(arelConnection);
    }

    public ArelCollector visitArelNodeSelectCore(Object object, ArelCollector collector) {
        ArelNodeSelectCore selectCore = (ArelNodeSelectCore) object;

        collector.append(WHERE);
        collector = injectJoin(selectCore.wheres, collector, AND);

        return collector;
    }
}

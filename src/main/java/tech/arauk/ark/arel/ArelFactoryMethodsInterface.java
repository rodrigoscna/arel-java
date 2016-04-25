package tech.arauk.ark.arel;

import tech.arauk.ark.arel.nodes.*;
import tech.arauk.ark.arel.nodes.ArelNodeOn;

import java.util.List;

public interface ArelFactoryMethodsInterface {
    ArelNodeAnd createAnd(List<Object> clauses);

    ArelNodeFalse createFalse();

    ArelNodeJoin createJoin(Object to);

    ArelNodeJoin createJoin(Object to, Object constraint);

    ArelNodeJoin createJoin(Object to, Object constraint, Class<? extends ArelNodeJoin> aClass);

    ArelNodeOn createOn(Object expr);

    ArelNodeJoin createStringJoin(String to);

    ArelNodeTableAlias createTableAlias(Object relation, Object name);

    ArelNodeTrue createTrue();

    ArelNodeGrouping grouping(Object expr);

    ArelNodeNamedFunction lower(Object column);
}

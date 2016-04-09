package tech.arauk.ark.arel;

import tech.arauk.ark.arel.nodes.*;
import tech.arauk.ark.arel.nodes.ArelNodeOn;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Class designed for creating various kinds of Arel Nodes.
 *
 * @author Rodrigo Scomazzon do Nascimento <rodrigo.sc.na@gmail.com>
 */
public abstract class ArelFactoryMethods {
    public static ArelNodeAnd createAnd(List<Object> clauses) {
        return new ArelNodeAnd(clauses);
    }

    public static ArelNodeFalse createFalse() {
        return new ArelNodeFalse();
    }

    public static ArelNodeJoin createJoin(Object to) {
        return createJoin(to, null);
    }

    public static ArelNodeJoin createJoin(Object to, Object constraint) {
        return createJoin(to, constraint, ArelNodeInnerJoin.class);
    }

    public static ArelNodeJoin createJoin(Object to, Object constraint, Class<? extends ArelNodeJoin> aClass) {
        try {
            return aClass.getConstructor(Object.class, Object.class).newInstance(to, constraint);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException exception) {
            return null;
        }
    }

    public static ArelNodeOn createOn(Object expr) {
        return new ArelNodeOn(expr);
    }

    public static ArelNodeJoin createStringJoin(String to) {
        return createJoin(to, null, ArelNodeStringJoin.class);
    }

    public static ArelNodeTableAlias createTableAlias(Object relation, Object name) {
        return new ArelNodeTableAlias(relation, name);
    }

    public static ArelNodeTrue createTrue() {
        return new ArelNodeTrue();
    }

    public static ArelNodeNamedFunction lower(Object column) {
        return new ArelNodeNamedFunction("LOWER", new Object[]{ArelNodes.buildQuoted(column)});
    }

    public static ArelNodeGrouping grouping(Object expr) {
        return new ArelNodeGrouping(expr);
    }
}

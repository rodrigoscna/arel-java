package tech.arauk.ark.arel.nodes;

import tech.arauk.ark.arel.ArelFactoryMethods;
import tech.arauk.ark.arel.ArelFactoryMethodsInterface;
import tech.arauk.ark.arel.ArelTable;
import tech.arauk.ark.arel.collectors.ArelCollector;
import tech.arauk.ark.arel.visitors.ArelVisitor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArelNode implements ArelFactoryMethodsInterface {
    static List<Object> objectToList(Object object) {
        List<Object> objects;

        if (object instanceof List) {
            objects = (List<Object>) object;
        } else if (object instanceof Object[]) {
            objects = Arrays.asList((Object[]) object);
        } else {
            objects = new ArrayList<>();
            objects.add(object);
        }

        return objects;
    }

    @Override
    public ArelNodeAnd createAnd(List<Object> clauses) {
        return ArelFactoryMethods.createAnd(clauses);
    }

    @Override
    public ArelNodeFalse createFalse() {
        return ArelFactoryMethods.createFalse();
    }

    @Override
    public ArelNodeJoin createJoin(Object to) {
        return ArelFactoryMethods.createJoin(to);
    }

    @Override
    public ArelNodeJoin createJoin(Object to, Object constraint) {
        return ArelFactoryMethods.createJoin(to, constraint);
    }

    @Override
    public ArelNodeJoin createJoin(Object to, Object constraint, Class<? extends ArelNodeJoin> aClass) {
        return ArelFactoryMethods.createJoin(to, constraint, aClass);
    }

    @Override
    public ArelNodeOn createOn(Object expr) {
        return ArelFactoryMethods.createOn(expr);
    }

    @Override
    public ArelNodeJoin createStringJoin(String to) {
        return ArelFactoryMethods.createJoin(to);
    }

    @Override
    public ArelNodeTableAlias createTableAlias(Object relation, Object name) {
        return ArelFactoryMethods.createTableAlias(relation, name);
    }

    @Override
    public ArelNodeTrue createTrue() {
        return ArelFactoryMethods.createTrue();
    }

    @Override
    public ArelNodeGrouping grouping(Object expr) {
        return ArelFactoryMethods.grouping(expr);
    }

    @Override
    public ArelNodeNamedFunction lower(Object column) {
        return ArelFactoryMethods.lower(column);
    }

    public ArelNodeAnd and(Object right) {
        List<Object> and = new ArrayList<>();
        and.add(this);
        and.add(right);
        return new ArelNodeAnd(and);
    }

    public ArelNodeNot not() {
        return new ArelNodeNot(this);
    }

    public ArelNodeGrouping or(Object right) {
        return new ArelNodeGrouping(new ArelNodeOr(this, right));
    }

    public String toSQL() {
        ArelVisitor visitor = ArelTable.engine;
        return toSQL(visitor);
    }

    public String toSQL(ArelVisitor visitor) {
        ArelCollector collector = new ArelCollector();
        collector = visitor.connection.getVisitor().accept(this, collector);
        return collector.value();
    }
}

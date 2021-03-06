package tech.arauk.ark.arel.nodes;

import tech.arauk.ark.arel.ArelFactoryMethods;
import tech.arauk.ark.arel.ArelFactoryMethodsInterface;
import tech.arauk.ark.arel.ArelRelation;
import tech.arauk.ark.arel.annotations.Beta;
import tech.arauk.ark.arel.attributes.ArelAttribute;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

@Beta
public class ArelNodeTableAlias extends ArelNodeBinary implements ArelFactoryMethodsInterface, ArelRelation {
    public ArelNodeTableAlias(Object left, Object right) {
        super(left, right);
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
        return ArelFactoryMethods.createJoin(this, to);
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
        return ArelFactoryMethods.createStringJoin(to);
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
    public ArelAttribute get(String name) {
        return new ArelAttribute(this, name);
    }

    @Override
    public ArelAttribute get(ArelNodeSqlLiteral name) {
        return new ArelAttribute(this, name);
    }

    @Override
    public ArelNodeGrouping grouping(Object expr) {
        return ArelFactoryMethods.grouping(expr);
    }

    @Override
    public boolean isAbleToTypeCast() {
        try {
            Method method = relation().getClass().getMethod("isAbleToTypeCast");
            return (boolean) method.invoke(relation());
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException exception) {
            return false;
        }
    }

    @Override
    public ArelNodeNamedFunction lower(Object column) {
        return ArelFactoryMethods.lower(column);
    }

    @Override
    public Object name() {
        return right();
    }

    @Override
    public ArelRelation name(Object name) {
        right(name);
        return this;
    }

    @Override
    public Object typeCastForDatabase(Object name, Object value) {
        try {
            Method method = relation().getClass().getMethod("typeCastForDatabase", Object.class, Object.class);
            return method.invoke(relation(), name, value);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException exception) {
            return value;
        }
    }

    @Override
    public Object tableAlias() {
        return name();
    }

    @Override
    public ArelRelation tableAlias(Object tableAlias) {
        return name(tableAlias);
    }

    @Override
    public Object tableName() {
        try {
            Method method = relation().getClass().getMethod("name");
            return method.invoke(relation());
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException exception) {
            return name();
        }
    }

    @Override
    public ArelRelation tableName(Object tableName) {
        try {
            Method method = relation().getClass().getMethod("name", Object.class);
            return (ArelRelation) method.invoke(relation(), tableName);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException exception) {
            return name(tableName);
        }
    }

    public Object relation() {
        return left();
    }

    public ArelNodeTableAlias relation(Object left) {
        return (ArelNodeTableAlias) left(left);
    }
}

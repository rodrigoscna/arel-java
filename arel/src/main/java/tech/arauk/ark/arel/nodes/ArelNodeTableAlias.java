package tech.arauk.ark.arel.nodes;

import tech.arauk.ark.arel.ArelFactoryMethods;
import tech.arauk.ark.arel.ArelRelation;
import tech.arauk.ark.arel.attributes.ArelAttribute;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ArelNodeTableAlias extends ArelNodeBinary implements ArelRelation {
    public ArelNodeTableAlias(Object left, Object right) {
        super(left, right);
    }

    @Override
    public ArelAttribute get(String name) {
        return new ArelAttribute(this, name);
    }

    @Override
    public boolean isAbleToTypeCast() {
        try {
            Method method = relation().getClass().getMethod("isAbleToTypeCast");
            return (boolean) method.invoke(this);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException exception) {
            return false;
        }
    }

    @Override
    public ArelNodeNamedFunction lower(Object column) {
        return ArelFactoryMethods.lower(column);
    }

    @Override
    public Object typeCastForDatabase(String name, Object value) {
        try {
            Method method = relation().getClass().getMethod("typeCastForDatabase", String.class, Object.class);
            return method.invoke(this, name, value);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException exception) {
            return value;
        }
    }

    @Override
    public Object tableAlias() {
        return name();
    }

    @Override
    public Object tableName() {
        try {
            Method method = relation().getClass().getMethod("name");
            return method.invoke(this);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException exception) {
            return name();
        }
    }

    public Object name() {
        return right;
    }

    public Object relation() {
        return left;
    }
}

package tech.arauk.ark.arel;

import tech.arauk.ark.arel.collectors.ArelCollector;
import tech.arauk.ark.arel.interfaces.ArelWheresInterface;
import tech.arauk.ark.arel.nodes.*;
import tech.arauk.ark.arel.nodes.unary.ArelNodeOn;
import tech.arauk.ark.arel.visitors.ArelVisitor;

import java.util.ArrayList;
import java.util.List;

public class ArelTreeManager implements ArelFactoryMethodsInterface {
    public Object engine;
    public List<Object> bindValues;
    private Object ast;
    private Object ctx;

    public ArelTreeManager(Object ast) {
        this.ast = ast;
        this.bindValues = new ArrayList<>();
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
    public ArelNodeGrouping grouping(Object expr) {
        return ArelFactoryMethods.grouping(expr);
    }

    @Override
    public ArelNodeNamedFunction lower(Object column) {
        return ArelFactoryMethods.lower(column);
    }

    public Object ast() {
        return this.ast;
    }

    public void ast(Object ast) {
        this.ast = ast;
    }

    public Object ctx() {
        return this.ctx;
    }

    public void ctx(Object ctx) {
        this.ctx = ctx;
    }

    public String toSQL() {
        ArelVisitor visitor = ArelTable.engine;
        return toSQL(visitor);
    }

    public String toSQL(ArelVisitor visitor) {
        ArelCollector collector = new ArelCollector();
        collector = visitor.connection.getVisitor().accept(this.ast, collector);
        return collector.getValue();
    }

    public ArelTreeManager where(Object expr) {
        if (expr instanceof ArelTreeManager) {
            expr = ((ArelTreeManager) expr).ast;
        }

        ((ArelWheresInterface) this.ctx).wheres().add(expr);

        return this;
    }
}

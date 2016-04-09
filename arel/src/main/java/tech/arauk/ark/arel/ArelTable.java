package tech.arauk.ark.arel;

import tech.arauk.ark.arel.attributes.ArelAttribute;
import tech.arauk.ark.arel.connection.ArelTypeCaster;
import tech.arauk.ark.arel.nodes.*;
import tech.arauk.ark.arel.nodes.unary.ArelNodeOn;
import tech.arauk.ark.arel.visitors.ArelVisitor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ArelTable implements ArelCrudInterface, ArelFactoryMethodsInterface, ArelRelation {
    public static ArelVisitor engine;
    private List<Object> mAliases;
    private String mName;
    private String mTableAlias;
    private ArelTypeCaster mTypeCaster;

    private ArelTable() {
        this.mAliases = new ArrayList<>();
    }

    public ArelTable(String name) {
        this();

        this.mName = name;
    }

    public ArelTable(String name, String as) {
        this(name);

        if (Objects.equals(as, this.mName)) {
            as = null;
        }

        this.mTableAlias = as;
    }

    public ArelTable(String name, ArelTypeCaster typeCaster) {
        this(name);

        this.mTypeCaster = typeCaster;
    }

    public ArelTable(String name, String as, ArelTypeCaster typeCaster) {
        this(name);

        if (Objects.equals(as, this.mName)) {
            as = null;
        }

        this.mTableAlias = as;

        this.mTypeCaster = typeCaster;
    }

    @Override
    public ArelDeleteManager compileDelete() {
        return ArelCrud.compileDelete(this);
    }

    @Override
    public ArelInsertManager compileInsert(Object values) {
        return ArelCrud.compileInsert(values);
    }

    @Override
    public ArelUpdateManager compileUpdate(Object values, ArelAttribute pk) {
        return ArelCrud.compileUpdate(this, values, pk);
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
    public ArelInsertManager createInsert() {
        return ArelCrud.createInsert();
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
    public boolean equals(Object other) {
        if (other instanceof ArelTable) {
            ArelTable table = (ArelTable) other;

            return Objects.deepEquals(this.tableName(), table.tableName()) && Objects.deepEquals(this.mAliases, table.mAliases) && Objects.deepEquals(this.tableAlias(), table.tableAlias());
        }

        return super.equals(other);
    }

    @Override
    public ArelAttribute get(String name) {
        return new ArelAttribute(this, name);
    }

    @Override
    public ArelNodeGrouping grouping(Object expr) {
        return ArelFactoryMethods.grouping(expr);
    }

    @Override
    public int hashCode() {
        return name().hashCode();
    }

    @Override
    public boolean isAbleToTypeCast() {
        return this.mTypeCaster != null;
    }

    @Override
    public ArelNodeNamedFunction lower(Object column) {
        return ArelFactoryMethods.lower(column);
    }

    @Override
    public String name() {
        return this.mName;
    }

    @Override
    public ArelTable name(Object tableName) {
        this.mName = String.valueOf(tableName);
        return this;
    }

    @Override
    public Object typeCastForDatabase(String attributeName, Object value) {
        return this.mTypeCaster.typeCastForDatabase(attributeName, value);
    }

    @Override
    public String tableAlias() {
        return this.mTableAlias;
    }

    @Override
    public ArelTable tableAlias(Object tableAlias) {
        this.mTableAlias = String.valueOf(tableAlias);
        return this;
    }

    @Override
    public String tableName() {
        return name();
    }

    @Override
    public ArelTable tableName(Object tableName) {
        return name(tableName);
    }

    public ArelNodeTableAlias alias() {
        return alias(String.format("%s_2", this.mName));
    }

    public ArelNodeTableAlias alias(String name) {
        ArelNodeTableAlias alias = new ArelNodeTableAlias(this, name);

        this.mAliases.add(alias);

        return alias;
    }

    public List<Object> aliases() {
        return this.mAliases;
    }

    public ArelTable aliases(List<Object> aliases) {
        this.mAliases = aliases;
        return this;
    }

    public ArelSelectManager from() {
        return new ArelSelectManager(this);
    }

    public ArelSelectManager having(Object expr) {
        return from().having(expr);
    }

    public ArelSelectManager join(Object relation) {
        return join(relation, ArelNodeInnerJoin.class);
    }

    public ArelSelectManager join(Object relation, Class<? extends ArelNodeJoin> aClass) {
        return from().join(relation, aClass);
    }

    public ArelSelectManager skip(int amount) {
        return from().skip(amount);
    }

    public ArelSelectManager outerJoin(Object relation) {
        return join(relation, ArelNodeOuterJoin.class);
    }

    public ArelSelectManager group(Object... columns) {
        return from().group(columns);
    }

    public ArelSelectManager order(Object... expr) {
        return from().order(expr);
    }

    public ArelSelectManager take(int amount) {
        return from().take(amount);
    }

    public ArelSelectManager project(Object... things) {
        return from().project(things);
    }

    public ArelSelectManager where(Object condition) {
        return (ArelSelectManager) from().where(condition);
    }
}

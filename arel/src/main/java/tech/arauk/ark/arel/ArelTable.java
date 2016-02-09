package tech.arauk.ark.arel;

import tech.arauk.ark.arel.attributes.ArelAttribute;
import tech.arauk.ark.arel.connection.ArelTypeCaster;
import tech.arauk.ark.arel.nodes.ArelNodeInnerJoin;
import tech.arauk.ark.arel.nodes.ArelNodeJoin;
import tech.arauk.ark.arel.nodes.ArelNodeTableAlias;
import tech.arauk.ark.arel.visitors.ArelVisitor;

import java.util.ArrayList;
import java.util.List;

public class ArelTable implements ArelRelation {
    public static ArelVisitor engine;
    public String tableAlias;
    public List<ArelNodeTableAlias> aliases;
    private String name;
    private ArelTypeCaster mTypeCaster;

    private ArelTable() {
        this.aliases = new ArrayList<>();
    }

    public ArelTable(String name) {
        this();

        this.name = name;
    }

    public ArelTable(String name, String as) {
        this(name);

        if (as == this.name) {
            as = null;
        }

        this.tableAlias = as;
    }

    public ArelTable(String name, ArelTypeCaster typeCaster) {
        this(name);

        this.mTypeCaster = typeCaster;
    }

    public ArelTable(String name, String as, ArelTypeCaster typeCaster) {
        this(name);

        if (as == this.name) {
            as = null;
        }

        this.tableAlias = as;

        this.mTypeCaster = typeCaster;
    }

    @Override
    public boolean isAbleToTypeCast() {
        return this.mTypeCaster != null;
    }

    @Override
    public Object typeCastForDatabase(String attributeName, Object value) {
        return this.mTypeCaster.typeCastForDatabase(attributeName, value);
    }

    @Override
    public String tableAlias() {
        return this.tableAlias;
    }

    @Override
    public String tableName() {
        return this.name;
    }

    public ArelNodeTableAlias alias() {
        return alias(String.format("%s_2", this.name));
    }

    public ArelNodeTableAlias alias(String name) {
        ArelNodeTableAlias alias = new ArelNodeTableAlias(this, name);

        this.aliases.add(alias);

        return alias;
    }

    public ArelNodeJoin createJoin(String to) {
        return ArelNodeFactory.createJoin(to);
    }

    public ArelNodeJoin createJoin(String to, String constraint) {
        return ArelNodeFactory.createJoin(to, constraint);
    }

    public ArelNodeJoin createJoin(String to, String constraint, Class<? extends ArelNodeJoin> kclass) {
        return ArelNodeFactory.createJoin(to, constraint, kclass);
    }

    public ArelNodeJoin createStringJoin(String to) {
        return ArelNodeFactory.createStringJoin(to);
    }

    public ArelInsertManager createInsert() {
        return new ArelInsertManager();
    }

    public ArelInsertManager compileInsert(String values) {
        ArelInsertManager insertManager = createInsert();
        insertManager.insert(values);
        return insertManager;
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

    public ArelAttribute get(String name) {
        return new ArelAttribute(this, name);
    }
}

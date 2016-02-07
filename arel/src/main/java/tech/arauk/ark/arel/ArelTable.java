package tech.arauk.ark.arel;

import tech.arauk.ark.arel.attributes.ArelAttribute;
import tech.arauk.ark.arel.connection.ArelTypeCaster;
import tech.arauk.ark.arel.nodes.ArelNodeJoin;
import tech.arauk.ark.arel.visitors.ArelVisitor;

public class ArelTable {
    public static ArelVisitor engine;
    public String name;
    public String alias;
    private ArelTypeCaster mTypeCaster;

    public ArelTable(String name) {
        this.name = name;
    }

    public ArelTable(String name, String as) {
        this.name = name;

        if (as == this.name) {
            as = null;
        }

        this.alias = as;
    }

    public ArelTable(String name, ArelTypeCaster typeCaster) {
        this.name = name;
        this.mTypeCaster = typeCaster;
    }

    public ArelTable(String name, String as, ArelTypeCaster typeCaster) {
        this.name = name;

        if (as == this.name) {
            as = null;
        }

        this.alias = as;

        this.mTypeCaster = typeCaster;
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

    public ArelSelectManager skip(int amount) {
        return from().skip(amount);
    }

    public ArelAttribute get(String name) {
        return new ArelAttribute(this, name);
    }

    public boolean isAbleToTypeCast() {
        return this.mTypeCaster != null;
    }

    public Object typeCastForDatabase(String attributeName, Object value) {
        return this.mTypeCaster.typeCastForDatabase(attributeName, value);
    }
}

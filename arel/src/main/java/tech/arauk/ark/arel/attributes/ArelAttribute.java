package tech.arauk.ark.arel.attributes;

import tech.arauk.ark.arel.ArelPredications;
import tech.arauk.ark.arel.ArelRelation;
import tech.arauk.ark.arel.nodes.ArelNode;
import tech.arauk.ark.arel.nodes.ArelNodeEquality;
import tech.arauk.ark.arel.nodes.ArelNodeNamedFunction;
import tech.arauk.ark.arel.nodes.binary.ArelNodeGreaterThan;
import tech.arauk.ark.arel.nodes.binary.ArelNodeIn;
import tech.arauk.ark.arel.nodes.binary.ArelNodeLessThan;

public class ArelAttribute {
    public ArelRelation relation;
    public String name;

    private ArelPredications mPredications;

    public ArelAttribute(ArelRelation relation, String name) {
        this.relation = relation;
        this.name = name;
    }

    public ArelNode between(Object begin, Object end) {
        return predications().between(begin, end);
    }

    public ArelNodeEquality eq(Object other) {
        return predications().eq(other);
    }

    public ArelNodeGreaterThan gt(Object right) {
        return predications().gt(right);
    }

    public ArelNodeLessThan lt(Object right) {
        return predications().lt(right);
    }

    public ArelNodeIn in(Object other) {
        return predications().in(other);
    }

    private ArelPredications predications() {
        if (this.mPredications == null) {
            this.mPredications = new ArelPredications(this);
        }
        return this.mPredications;
    }

    public boolean isAbleToTypeCast() {
        return this.relation.isAbleToTypeCast();
    }

    public Object typeCastForDatabase(Object value) {
        return this.relation.typeCastForDatabase(this.name, value);
    }

    public ArelNodeNamedFunction lower() {
        return this.relation.lower(this);
    }

    public interface Type {
        String type();
    }
}

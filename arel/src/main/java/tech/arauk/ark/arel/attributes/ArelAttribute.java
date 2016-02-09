package tech.arauk.ark.arel.attributes;

import tech.arauk.ark.arel.ArelPredications;
import tech.arauk.ark.arel.ArelRelation;

public class ArelAttribute {
    public ArelRelation relation;
    public String name;

    private ArelPredications mPredications;

    public ArelAttribute(ArelRelation relation, String name) {
        this.relation = relation;
        this.name = name;
    }

    public Object eq(Object other) {
        return predications().eq(other);
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
}

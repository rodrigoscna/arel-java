package tech.arauk.ark.arel.attributes;

import tech.arauk.ark.arel.ArelRelation;
import tech.arauk.ark.arel.annotations.Beta;

@Beta
public class ArelAttributeBoolean extends ArelAttribute {
    public ArelAttributeBoolean(ArelRelation relation, Object name) {
        super(relation, name);
    }
}

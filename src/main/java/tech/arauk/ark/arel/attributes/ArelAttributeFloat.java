package tech.arauk.ark.arel.attributes;

import tech.arauk.ark.arel.ArelRelation;
import tech.arauk.ark.arel.annotations.Beta;

@Beta
public class ArelAttributeFloat extends ArelAttribute {
    public ArelAttributeFloat(ArelRelation relation, Object name) {
        super(relation, name);
    }
}

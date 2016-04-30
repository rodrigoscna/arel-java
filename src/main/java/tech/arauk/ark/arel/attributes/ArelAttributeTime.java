package tech.arauk.ark.arel.attributes;

import tech.arauk.ark.arel.ArelRelation;
import tech.arauk.ark.arel.annotations.Beta;

@Beta
public class ArelAttributeTime extends ArelAttribute {
    public ArelAttributeTime(ArelRelation relation, Object name) {
        super(relation, name);
    }
}

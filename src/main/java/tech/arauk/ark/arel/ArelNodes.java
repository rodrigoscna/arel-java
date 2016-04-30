package tech.arauk.ark.arel;

import tech.arauk.ark.arel.annotations.Beta;
import tech.arauk.ark.arel.attributes.ArelAttribute;
import tech.arauk.ark.arel.nodes.ArelNode;
import tech.arauk.ark.arel.nodes.ArelNodeCasted;
import tech.arauk.ark.arel.nodes.ArelNodeQuoted;

@Beta
public class ArelNodes {
    public static Object buildQuoted(Object other) {
        return buildQuoted(other, null);
    }

    public static Object buildQuoted(Object other, Object attribute) {
        if (other instanceof ArelNode || other instanceof ArelAttribute || other instanceof ArelTable || other instanceof ArelSelectManager) {
            return other;
        } else if (attribute instanceof ArelAttribute) {
            return new ArelNodeCasted(other, (ArelAttribute) attribute);
        } else {
            return new ArelNodeQuoted(other);
        }
    }
}

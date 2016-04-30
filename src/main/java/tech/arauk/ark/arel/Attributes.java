package tech.arauk.ark.arel;

import tech.arauk.ark.arel.annotations.Beta;
import tech.arauk.ark.arel.attributes.*;

@Beta
public class Attributes {
    public static Class<? extends ArelAttribute> forColumn(ArelAttribute.Type column) {
        if (column.type() != null) {
            switch (column.type()) {
                case "binary":
                case "string":
                case "text":
                    return ArelAttributeString.class;
                case "boolean":
                    return ArelAttributeBoolean.class;
                case "date":
                case "datetime":
                case "time":
                case "timestamp":
                    return ArelAttributeTime.class;
                case "decimal":
                    return ArelAttributeDecimal.class;
                case "float":
                    return ArelAttributeFloat.class;
                case "integer":
                    return ArelAttributeInteger.class;
            }
        }

        return ArelAttributeUndefined.class;
    }
}

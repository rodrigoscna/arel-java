package tech.arauk.ark.arel;

import tech.arauk.ark.arel.annotations.Beta;
import tech.arauk.ark.arel.nodes.*;

@Beta
public abstract class ArelMath {
    public static ArelNodeGrouping add(Object holder, Object other) {
        return new ArelNodeGrouping(new ArelNodeAddition(holder, other));
    }

    public static ArelNodeDivision divide(Object holder, Object other) {
        return new ArelNodeDivision(holder, other);
    }

    public static ArelNodeMultiplication multiply(Object holder, Object other) {
        return new ArelNodeMultiplication(holder, other);
    }

    public static ArelNodeGrouping subtract(Object holder, Object other) {
        return new ArelNodeGrouping(new ArelNodeSubtraction(holder, other));
    }
}

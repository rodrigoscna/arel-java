package tech.arauk.ark.arel;

import tech.arauk.ark.arel.nodes.ArelNodeDivision;
import tech.arauk.ark.arel.nodes.ArelNodeGrouping;
import tech.arauk.ark.arel.nodes.ArelNodeMultiplication;

public interface ArelMathInterface {
    ArelNodeGrouping add(Object other);

    ArelNodeDivision divide(Object other);

    ArelNodeMultiplication multiply(Object other);

    ArelNodeGrouping subtract(Object other);
}

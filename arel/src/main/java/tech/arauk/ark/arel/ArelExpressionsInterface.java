package tech.arauk.ark.arel;

import tech.arauk.ark.arel.nodes.ArelNodeCount;
import tech.arauk.ark.arel.nodes.function.ArelNodeAvg;
import tech.arauk.ark.arel.nodes.function.ArelNodeMax;
import tech.arauk.ark.arel.nodes.function.ArelNodeMin;
import tech.arauk.ark.arel.nodes.function.ArelNodeSum;

public interface ArelExpressionsInterface {
    ArelNodeAvg average();

    ArelNodeCount count();

    ArelNodeCount count(Boolean distinct);

    ArelNodeMax maximum();

    ArelNodeMin minimum();

    ArelNodeSum sum();
}

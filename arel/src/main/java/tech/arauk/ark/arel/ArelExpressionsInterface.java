package tech.arauk.ark.arel;

import tech.arauk.ark.arel.nodes.ArelNodeCount;
import tech.arauk.ark.arel.nodes.ArelNodeAvg;
import tech.arauk.ark.arel.nodes.ArelNodeMax;
import tech.arauk.ark.arel.nodes.ArelNodeMin;
import tech.arauk.ark.arel.nodes.ArelNodeSum;

public interface ArelExpressionsInterface {
    ArelNodeAvg average();

    ArelNodeCount count();

    ArelNodeCount count(Boolean distinct);

    ArelNodeMax maximum();

    ArelNodeMin minimum();

    ArelNodeSum sum();
}

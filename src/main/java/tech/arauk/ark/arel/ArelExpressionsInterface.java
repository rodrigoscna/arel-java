package tech.arauk.ark.arel;

import tech.arauk.ark.arel.annotations.Beta;
import tech.arauk.ark.arel.nodes.*;

@Beta
public interface ArelExpressionsInterface {
    ArelNodeAvg average();

    ArelNodeCount count();

    ArelNodeCount count(Boolean distinct);

    ArelNodeExtract extract(Object field);

    ArelNodeMax maximum();

    ArelNodeMin minimum();

    ArelNodeSum sum();
}

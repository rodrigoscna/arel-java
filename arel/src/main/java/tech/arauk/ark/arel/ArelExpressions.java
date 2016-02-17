package tech.arauk.ark.arel;

import tech.arauk.ark.arel.nodes.ArelNodeCount;

import java.util.ArrayList;
import java.util.List;

public abstract class ArelExpressions {
    public static ArelNodeCount count(Object holder) {
        return count(holder, false);
    }

    public static ArelNodeCount count(Object holder, Boolean distinct) {
        List<Object> holders = new ArrayList<>();
        holders.add(holder);

        return new ArelNodeCount(holders, distinct);
    }
}

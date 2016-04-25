package tech.arauk.ark.arel;

import tech.arauk.ark.arel.nodes.*;

import java.util.ArrayList;
import java.util.List;

public abstract class ArelExpressions {
    public static ArelNodeAvg average(Object holder) {
        return new ArelNodeAvg(objectToArray(holder));
    }

    public static ArelNodeCount count(Object holder) {
        return count(holder, false);
    }

    public static ArelNodeCount count(Object holder, Boolean distinct) {
        return new ArelNodeCount(objectToArray(holder), distinct);
    }

    public static ArelNodeExtract extract(Object holder, Object field) {
        return new ArelNodeExtract(objectToArray(holder), field);
    }

    public static ArelNodeMax maximum(Object holder) {
        return new ArelNodeMax(objectToArray(holder));
    }

    public static ArelNodeMin minimum(Object holder) {
        return new ArelNodeMin(objectToArray(holder));
    }

    public static ArelNodeSum sum(Object holder) {
        return new ArelNodeSum(objectToArray(holder));
    }

    private static List<Object> objectToArray(Object object) {
        List<Object> objects = new ArrayList<>();
        objects.add(object);

        return objects;
    }
}

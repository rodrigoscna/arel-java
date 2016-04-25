package tech.arauk.ark.arel.support;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestUtils {
    public static List<Object> objectsToList(Object... objects) {
        List<Object> list = new ArrayList<>();

        Collections.addAll(list, objects);

        return list;
    }
}

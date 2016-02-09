package tech.arauk.ark.arel;

import tech.arauk.ark.arel.nodes.ArelNodeAnd;
import tech.arauk.ark.arel.nodes.ArelNodeInnerJoin;
import tech.arauk.ark.arel.nodes.ArelNodeJoin;
import tech.arauk.ark.arel.nodes.ArelNodeStringJoin;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Class designed for creating various kinds of Arel Nodes.
 *
 * @author Rodrigo Scomazzon do Nascimento <rodrigo.sc.na@gmail.com>
 */
public class ArelNodeFactory {
    public static Object createAnd(List<Object> object) {
        return new ArelNodeAnd(object);
    }

    public static ArelNodeJoin createJoin(Object to) {
        return createJoin(to, null);
    }

    public static ArelNodeJoin createJoin(Object to, String constraint) {
        return createJoin(to, constraint, ArelNodeInnerJoin.class);
    }

    public static ArelNodeJoin createJoin(Object to, String constraint, Class<? extends ArelNodeJoin> kclass) {
        try {
            return kclass.getConstructor(Object.class, String.class, Class.class).newInstance(to, constraint, kclass);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException exception) {
            return null;
        }
    }

    public static ArelNodeJoin createStringJoin(String to) {
        return createJoin(to, null, ArelNodeStringJoin.class);
    }
}

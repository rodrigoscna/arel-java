package tech.arauk.ark.arel.nodes;

import java.util.ArrayList;

public class ArelNodeValues extends ArelNodeBinary {
    public ArelNodeValues(Object left) {
        super(left, new ArrayList<>());
    }

    public ArelNodeValues(Object left, Object right) {
        super(left, right);
    }

    public Object expressions() {
        return left();
    }

    public void expressions(Object expressions) {
        left(expressions);
    }

    public Object columns() {
        return right();
    }

    public void columns(Object columns) {
        right(columns);
    }
}

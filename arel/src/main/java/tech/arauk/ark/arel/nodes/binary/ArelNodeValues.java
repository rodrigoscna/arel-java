package tech.arauk.ark.arel.nodes.binary;

import tech.arauk.ark.arel.nodes.ArelNodeBinary;

public class ArelNodeValues extends ArelNodeBinary {
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

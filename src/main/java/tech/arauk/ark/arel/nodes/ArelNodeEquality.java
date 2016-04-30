package tech.arauk.ark.arel.nodes;

import tech.arauk.ark.arel.annotations.Beta;

@Beta
public class ArelNodeEquality extends ArelNodeBinary {
    public ArelNodeEquality(Object left, Object right) {
        super(left, right);
    }

    public String operator() {
        return "==";
    }

    public Object operant1() {
        return left();
    }

    public Object operant2() {
        return right();
    }
}

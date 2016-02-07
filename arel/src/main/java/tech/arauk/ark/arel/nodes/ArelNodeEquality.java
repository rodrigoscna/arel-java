package tech.arauk.ark.arel.nodes;

public class ArelNodeEquality extends ArelNodeBinary {
    public ArelNodeEquality(Object left, Object right) {
        super(left, right);
    }

    public String operator() {
        return "==";
    }

    public Object operant1() {
        return this.left;
    }

    public Object operant2() {
        return this.right;
    }
}

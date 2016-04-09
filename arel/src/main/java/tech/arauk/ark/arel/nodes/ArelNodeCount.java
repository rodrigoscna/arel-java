package tech.arauk.ark.arel.nodes;

import tech.arauk.ark.arel.interfaces.ArelDistinctInterface;

public class ArelNodeCount extends ArelNodeFunction implements ArelDistinctInterface {
    private Boolean distinct;

    public ArelNodeCount(Object expr) {
        super(expr);
    }

    public ArelNodeCount(Object expr, Boolean distinct) {
        super(expr);

        this.distinct = distinct;
    }

    public ArelNodeCount(Object expr, String alias) {
        super(expr, alias);
    }

    public ArelNodeCount(Object expr, Boolean distinct, String alias) {
        super(expr, alias);

        this.distinct = distinct;
    }

    @Override
    public Boolean distinct() {
        return this.distinct;
    }

    @Override
    public ArelNodeCount distinct(Boolean distinct) {
        this.distinct = distinct;
        return this;
    }
}

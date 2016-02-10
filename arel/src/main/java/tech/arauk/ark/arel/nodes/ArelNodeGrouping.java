package tech.arauk.ark.arel.nodes;

import tech.arauk.ark.arel.ArelPredications;

public class ArelNodeGrouping extends ArelNodeUnary {
    private ArelPredications mPredications;

    public ArelNodeGrouping(Object expr) {
        super(expr);
    }

    private ArelPredications predications() {
        if (this.mPredications == null) {
            this.mPredications = new ArelPredications(this);
        }
        return this.mPredications;
    }
}

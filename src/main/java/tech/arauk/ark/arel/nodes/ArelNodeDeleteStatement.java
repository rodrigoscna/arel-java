package tech.arauk.ark.arel.nodes;

import tech.arauk.ark.arel.annotations.Beta;
import tech.arauk.ark.arel.interfaces.ArelWheresInterface;

import java.util.ArrayList;
import java.util.List;

@Beta
public class ArelNodeDeleteStatement extends ArelNodeBinary implements ArelWheresInterface {
    public Object limit;

    public ArelNodeDeleteStatement() {
        this(null, new ArrayList<>());
    }

    public ArelNodeDeleteStatement(Object left, Object right) {
        super(left, right);
    }

    @Override
    public List<Object> wheres() {
        return (List<Object>) this.right();
    }

    @Override
    public ArelNodeDeleteStatement wheres(Object wheres) {
        this.right(objectToList(wheres));
        return this;
    }

    public Object relation() {
        return this.left();
    }

    public void relation(Object left) {
        this.left(left);
    }
}

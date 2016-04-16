package tech.arauk.ark.arel.nodes;

import tech.arauk.ark.arel.interfaces.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ArelNodeUpdateStatement extends ArelNodeStatement implements ArelKeyInterface, ArelLimitInterface, ArelOrdersInterface, ArelRelationInterface, ArelValuesInterface, ArelWheresInterface {
    private Object relation;
    private List<Object> wheres;
    private List<Object> values;
    private List<Object> orders;
    private ArelNodeLimit limit;
    private Object key;

    public ArelNodeUpdateStatement() {
        super();

        this.key = null;
        this.limit = null;
        this.orders = new ArrayList<>();
        this.relation = null;
        this.values = new ArrayList<>();
        this.wheres = new ArrayList<>();
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof ArelNodeUpdateStatement) {
            ArelNodeUpdateStatement updateStatement = (ArelNodeUpdateStatement) other;

            return Objects.equals(this.relation(), updateStatement.relation())
                    && Objects.equals(this.wheres(), updateStatement.wheres())
                    && Objects.equals(this.values(), updateStatement.values())
                    && Objects.equals(this.orders(), updateStatement.orders())
                    && Objects.equals(this.limit(), updateStatement.limit())
                    && Objects.equals(this.key(), updateStatement.key());
        }

        return super.equals(other);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(new Object[]{relation(), wheres(), values(), orders(), limit(), key()});
    }

    @Override
    public Object key() {
        return this.key;
    }

    @Override
    public ArelNodeUpdateStatement key(Object key) {
        this.key = key;
        return this;
    }

    @Override
    public ArelNodeLimit limit() {
        return this.limit;
    }

    @Override
    public ArelLimitInterface limit(Object limit) {
        if (limit instanceof ArelNodeLimit) {
            this.limit = (ArelNodeLimit) limit;
        } else {
            this.limit = new ArelNodeLimit(limit);
        }

        return this;
    }

    @Override
    public List<Object> orders() {
        return this.orders;
    }

    @Override
    public ArelNodeUpdateStatement orders(Object orders) {
        this.orders = objectToList(orders);
        return this;
    }

    @Override
    public Object relation() {
        return this.relation;
    }

    @Override
    public ArelNodeUpdateStatement relation(Object relation) {
        this.relation = relation;
        return this;
    }

    @Override
    public List<Object> values() {
        return this.values;
    }

    @Override
    public ArelNodeUpdateStatement values(Object values) {
        this.values = objectToList(values);
        return this;
    }

    @Override
    public List<Object> wheres() {
        return this.wheres;
    }

    @Override
    public ArelNodeUpdateStatement wheres(Object wheres) {
        this.wheres = objectToList(wheres);
        return this;
    }
}

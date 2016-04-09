package tech.arauk.ark.arel.nodes;

import tech.arauk.ark.arel.interfaces.*;
import tech.arauk.ark.arel.nodes.unary.ArelNodeLimit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    public ArelNodeUpdateStatement limit(ArelNodeLimit limit) {
        this.limit = limit;
        return this;
    }

    @Override
    public List<Object> orders() {
        return this.orders;
    }

    @Override
    public ArelNodeUpdateStatement orders(List<Object> orders) {
        this.orders = orders;
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
    public ArelNodeUpdateStatement values(List<Object> values) {
        this.values = values;
        return this;
    }

    @Override
    public List<Object> wheres() {
        return this.wheres;
    }

    @Override
    public ArelNodeUpdateStatement wheres(List<Object> wheres) {
        this.wheres = wheres;
        return this;
    }
}

package tech.arauk.ark.arel.nodes;

import tech.arauk.ark.arel.interfaces.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArelNodeSelectStatement extends ArelNodeStatement implements ArelCoresInterface, ArelLimitInterface, ArelLockInterface, ArelOffsetInterface, ArelOrdersInterface, ArelWithInterface {
    public ArelNodeSelectCore[] cores;
    public ArelNodeLimit limit;
    public ArelNodeLock lock;
    public ArelNodeOffset offset;
    public List<Object> orders;
    public ArelNodeWith with;

    public ArelNodeSelectStatement() {
        this(new ArelNodeSelectCore[]{new ArelNodeSelectCore()});
    }

    public ArelNodeSelectStatement(ArelNodeSelectCore[] cores) {
        super();

        this.cores = cores;
        this.orders = new ArrayList<>();
    }

    @Override
    public ArelNodeSelectCore[] cores() {
        return this.cores;
    }

    @Override
    public ArelNodeSelectStatement cores(ArelNodeSelectCore[] cores) {
        this.cores = cores;
        return this;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(new Object[]{cores(), orders(), limit(), lock(), offset(), with()});
    }

    @Override
    public ArelNodeLock lock() {
        return this.lock;
    }

    @Override
    public ArelNodeSelectStatement lock(ArelNodeLock lock) {
        this.lock = lock;
        return this;
    }

    @Override
    public ArelNodeLimit limit() {
        return this.limit;
    }

    @Override
    public ArelNodeSelectStatement limit(ArelNodeLimit limit) {
        this.limit = limit;
        return this;
    }

    @Override
    public ArelNodeOffset offset() {
        return this.offset;
    }

    @Override
    public ArelNodeSelectStatement offset(ArelNodeOffset offset) {
        this.offset = offset;
        return this;
    }

    @Override
    public List<Object> orders() {
        return this.orders;
    }

    @Override
    public ArelNodeSelectStatement orders(List<Object> orders) {
        this.orders = orders;
        return this;
    }

    @Override
    public ArelNodeWith with() {
        return this.with;
    }

    @Override
    public ArelNodeSelectStatement with(ArelNodeWith with) {
        this.with = with;
        return this;
    }
}

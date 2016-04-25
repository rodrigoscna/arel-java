package tech.arauk.ark.arel.nodes;

import tech.arauk.ark.arel.interfaces.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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
    public boolean equals(Object other) {
        if (other instanceof ArelNodeSelectStatement) {
            ArelNodeSelectStatement selectStatement = (ArelNodeSelectStatement) other;

            return Arrays.equals(this.cores(), selectStatement.cores())
                    && Objects.equals(this.orders(), selectStatement.orders())
                    && Objects.equals(this.limit(), selectStatement.limit())
                    && Objects.equals(this.lock(), selectStatement.lock())
                    && Objects.equals(this.offset(), selectStatement.offset())
                    && Objects.equals(this.with(), selectStatement.with());
        }

        return super.equals(other);
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
    public ArelNodeSelectStatement lock(Object lock) {
        if (lock instanceof ArelNodeLock) {
            this.lock = (ArelNodeLock) lock;
        } else {
            this.lock = new ArelNodeLock(lock);
        }

        return this;
    }

    @Override
    public ArelNodeLimit limit() {
        return this.limit;
    }

    @Override
    public ArelNodeSelectStatement limit(Object limit) {
        if (limit instanceof ArelNodeLimit) {
            this.limit = (ArelNodeLimit) limit;
        } else {
            this.limit = new ArelNodeLimit(limit);
        }

        return this;
    }

    @Override
    public ArelNodeOffset offset() {
        return this.offset;
    }

    @Override
    public ArelNodeSelectStatement offset(Object offset) {
        if (offset instanceof ArelNodeOffset) {
            this.offset = (ArelNodeOffset) offset;
        } else {
            this.offset = new ArelNodeOffset(offset);
        }

        return this;
    }

    @Override
    public List<Object> orders() {
        return this.orders;
    }

    @Override
    public ArelNodeSelectStatement orders(Object orders) {
        this.orders = objectToList(orders);
        return this;
    }

    @Override
    public ArelNodeWith with() {
        return this.with;
    }

    @Override
    public ArelNodeSelectStatement with(Object with) {
        if (with instanceof ArelNodeWith) {
            this.with = (ArelNodeWith) with;
        } else {
            this.with = new ArelNodeWith(with);
        }

        return this;
    }
}

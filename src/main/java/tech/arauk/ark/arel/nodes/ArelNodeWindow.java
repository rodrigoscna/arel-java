package tech.arauk.ark.arel.nodes;

import tech.arauk.ark.arel.interfaces.ArelFramingInterface;
import tech.arauk.ark.arel.interfaces.ArelOrdersInterface;
import tech.arauk.ark.arel.interfaces.ArelPartitionsInterface;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ArelNodeWindow extends ArelNode implements ArelFramingInterface, ArelOrdersInterface, ArelPartitionsInterface {
    public List<Object> orders;
    public List<Object> partitions;
    public Object framing;

    public ArelNodeWindow() {
        this.orders = new ArrayList<>();
        this.partitions = new ArrayList<>();
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof ArelNodeWindow) {
            ArelNodeWindow window = (ArelNodeWindow) other;

            return Objects.equals(this.orders(), window.orders())
                    && Objects.equals(this.framing(), window.framing())
                    && Objects.equals(this.partitions(), window.partitions());
        }

        return super.equals(other);
    }

    @Override
    public Object framing() {
        return this.framing;
    }

    @Override
    public ArelNodeWindow framing(Object framing) {
        this.framing = framing;
        return this;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(new Object[]{orders(), framing()});
    }

    @Override
    public List<Object> orders() {
        return this.orders;
    }

    @Override
    public ArelNodeWindow orders(Object orders) {
        this.orders = objectToList(orders);
        return this;
    }

    @Override
    public List<Object> partitions() {
        return this.partitions;
    }

    @Override
    public ArelNodeWindow partitions(Object partitions) {
        this.partitions = objectToList(partitions);
        return this;
    }

    public ArelNodeWindow order(Object... expr) {
        for (Object object : expr) {
            if (object instanceof String) {
                object = new ArelNodeSqlLiteral(String.valueOf(object));
            }

            this.orders.add(object);
        }

        return this;
    }

    public ArelNodeWindow partition(Object... expr) {
        for (Object object : expr) {
            if (object instanceof String) {
                object = new ArelNodeSqlLiteral(String.valueOf(object));
            }

            this.partitions.add(object);
        }

        return this;
    }

    public Object frame(Object expr) {
        this.framing = expr;
        return this.framing;
    }

    public Object range() {
        return range(null);
    }

    public Object range(Object expr) {
        if (this.framing != null) {
            return new ArelNodeRange(expr);
        } else {
            return frame(new ArelNodeRange(expr));
        }
    }

    public Object rows() {
        return rows(null);
    }

    public Object rows(Object expr) {
        if (this.framing != null) {
            return new ArelNodeRows(expr);
        } else {
            return frame(new ArelNodeRows(expr));
        }
    }
}

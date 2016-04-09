package tech.arauk.ark.arel.nodes;

import tech.arauk.ark.arel.interfaces.ArelFramingInterface;
import tech.arauk.ark.arel.interfaces.ArelOrdersInterface;
import tech.arauk.ark.arel.nodes.unary.ArelNodeRange;
import tech.arauk.ark.arel.nodes.unary.ArelNodeRows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArelNodeWindow extends ArelNode implements ArelFramingInterface, ArelOrdersInterface {
    public List<Object> orders;
    public List<Object> partitions;
    public Object framing;

    public ArelNodeWindow() {
        this.orders = new ArrayList<>();
        this.partitions = new ArrayList<>();
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
    public ArelNodeWindow orders(List<Object> orders) {
        this.orders = orders;
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

package tech.arauk.ark.arel.nodes;

import tech.arauk.ark.arel.nodes.unary.ArelNodeRange;
import tech.arauk.ark.arel.nodes.unary.ArelNodeRows;

import java.util.ArrayList;
import java.util.List;

public class ArelNodeWindow extends ArelNode {
    public List<Object> orders;
    public List<Object> partitions;
    public Object framing;

    public ArelNodeWindow() {
        this.orders = new ArrayList<>();
        this.partitions = new ArrayList<>();
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

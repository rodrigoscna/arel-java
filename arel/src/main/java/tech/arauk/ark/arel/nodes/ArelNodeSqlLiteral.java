package tech.arauk.ark.arel.nodes;

import java.util.Objects;

public class ArelNodeSqlLiteral {
    private String mValue;

    public ArelNodeSqlLiteral(Object value) {
        this.mValue = String.valueOf(value);
    }

    public String getValue() {
        return toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof ArelNodeSqlLiteral) {
            return Objects.equals(this.mValue, ((ArelNodeSqlLiteral) other).getValue());
        } else if (other instanceof String) {
            return Objects.equals(this.mValue, String.valueOf(other));
        } else {
            return super.equals(other);
        }
    }

    @Override
    public String toString() {
        return this.mValue;
    }
}

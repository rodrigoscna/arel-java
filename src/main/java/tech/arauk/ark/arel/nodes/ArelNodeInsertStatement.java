package tech.arauk.ark.arel.nodes;

import tech.arauk.ark.arel.ArelRelation;
import tech.arauk.ark.arel.annotations.Beta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Beta
public class ArelNodeInsertStatement extends ArelNodeStatement {
    public List<Object> columns;
    public ArelRelation relation;
    public Object select;
    public Object values;

    public ArelNodeInsertStatement() {
        super();

        this.relation = null;
        this.columns = new ArrayList<>();
        this.values = null;
        this.select = null;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof ArelNodeInsertStatement) {
            ArelNodeInsertStatement insertStatement = (ArelNodeInsertStatement) other;

            return Objects.equals(this.relation(), insertStatement.relation()) && Objects.equals(this.columns(), insertStatement.columns()) && Objects.equals(this.select(), insertStatement.select()) && Objects.equals(this.values(), insertStatement.values());
        }

        return super.equals(other);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(new Object[]{relation(), columns(), values(), select()});
    }

    public List<Object> columns() {
        return this.columns;
    }

    public ArelNodeInsertStatement columns(List<Object> columns) {
        this.columns = columns;
        return this;
    }

    public ArelRelation relation() {
        return this.relation;
    }

    public ArelNodeInsertStatement relation(ArelRelation relation) {
        this.relation = relation;
        return this;
    }

    public Object select() {
        return this.select;
    }

    public ArelNodeInsertStatement select(Object select) {
        this.select = select;
        return this;
    }

    public Object values() {
        return this.values;
    }

    public ArelNodeInsertStatement values(Object values) {
        this.values = values;
        return this;
    }

}

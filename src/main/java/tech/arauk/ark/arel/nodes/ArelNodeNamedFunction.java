package tech.arauk.ark.arel.nodes;

import tech.arauk.ark.arel.annotations.Beta;
import tech.arauk.ark.arel.interfaces.ArelNameInterface;

import java.util.Objects;

@Beta
public class ArelNodeNamedFunction extends ArelNodeFunction implements ArelNameInterface {
    public String name;

    private ArelNodeNamedFunction(Object expr) {
        super(expr);
    }

    private ArelNodeNamedFunction(Object expr, String alias) {
        super(expr, alias);
    }

    public ArelNodeNamedFunction(String name, Object expr) {
        this(expr);

        this.name = name;
    }

    public ArelNodeNamedFunction(String name, Object expr, String alias) {
        this(expr, alias);

        this.name = name;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof ArelNodeNamedFunction) {
            ArelNodeNamedFunction namedFunction = (ArelNodeNamedFunction) other;

            return super.equals(other) && Objects.equals(this.name(), namedFunction.name());
        }

        return super.equals(other);
    }

    @Override
    public int hashCode() {
        return super.hashCode() ^ name().hashCode();
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public ArelNodeNamedFunction name(String name) {
        this.name = name;
        return this;
    }
}

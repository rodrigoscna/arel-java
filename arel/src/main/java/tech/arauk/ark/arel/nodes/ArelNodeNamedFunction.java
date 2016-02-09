package tech.arauk.ark.arel.nodes;

public class ArelNodeNamedFunction extends ArelNodeFunction {
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
}

package tech.arauk.ark.arel.nodes;

import tech.arauk.ark.arel.interfaces.ArelNameInterface;

public class ArelNodeNamedWindow extends ArelNodeWindow implements ArelNameInterface {
    public String name;

    public ArelNodeNamedWindow(String name) {
        this.name = name;
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
    public ArelNodeNamedWindow name(String name) {
        this.name = name;
        return this;
    }
}

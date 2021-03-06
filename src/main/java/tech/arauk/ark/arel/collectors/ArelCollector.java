package tech.arauk.ark.arel.collectors;

import tech.arauk.ark.arel.annotations.Beta;
import tech.arauk.ark.arel.nodes.ArelNodeBindParam;

@Beta
public class ArelCollector {
    private StringBuilder mBuilder;
    private int mBindIndex;

    public ArelCollector() {
        this.mBuilder = new StringBuilder();
        this.mBindIndex = 1;
    }

    public ArelCollector addBind(ArelNodeBindParam bindParam, ArelCollector.Bindable bindable) {
        append(bindable.bind(this.mBindIndex));
        this.mBindIndex++;
        return this;
    }

    public ArelCollector append(String string) {
        this.mBuilder.append(string);
        return this;
    }

    public String value() {
        return this.mBuilder.toString();
    }

    public interface Bindable {
        String bind(int bindIndex);
    }
}

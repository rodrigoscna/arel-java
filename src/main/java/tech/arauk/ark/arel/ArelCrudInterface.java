package tech.arauk.ark.arel;

import tech.arauk.ark.arel.annotations.Beta;
import tech.arauk.ark.arel.attributes.ArelAttribute;

@Beta
public interface ArelCrudInterface {
    ArelDeleteManager compileDelete();

    ArelInsertManager compileInsert(Object values);

    ArelUpdateManager compileUpdate(Object values, ArelAttribute pk);

    ArelInsertManager createInsert();
}

package tech.arauk.ark.arel;

import tech.arauk.ark.arel.attributes.ArelAttribute;

public interface ArelCrudInterface {
    ArelDeleteManager compileDelete();

    ArelInsertManager compileInsert(String values);

    ArelUpdateManager compileUpdate(Object values, ArelAttribute pk);

    ArelInsertManager createInsert();
}

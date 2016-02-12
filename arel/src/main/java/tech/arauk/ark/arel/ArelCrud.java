package tech.arauk.ark.arel;

public abstract class ArelCrud {
    public static ArelInsertManager createInsert() {
        return new ArelInsertManager();
    }
}

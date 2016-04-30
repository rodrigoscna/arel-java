package tech.arauk.ark.arel.interfaces;

import tech.arauk.ark.arel.annotations.Beta;

import java.util.List;

@Beta
public interface ArelPartitionsInterface {
    List<Object> partitions();

    ArelPartitionsInterface partitions(Object partitions);
}

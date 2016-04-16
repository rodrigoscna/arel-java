package tech.arauk.ark.arel.interfaces;

import java.util.List;

public interface ArelPartitionsInterface {
    List<Object> partitions();

    ArelPartitionsInterface partitions(Object partitions);
}

package tech.arauk.ark.arel.interfaces;

import tech.arauk.ark.arel.annotations.Beta;

import java.util.List;

@Beta
public interface ArelWindowsInterface {
    List<Object> windows();

    ArelWindowsInterface windows(List<Object> windows);
}

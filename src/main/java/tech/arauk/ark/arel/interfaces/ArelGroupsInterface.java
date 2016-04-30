package tech.arauk.ark.arel.interfaces;

import tech.arauk.ark.arel.annotations.Beta;

import java.util.List;

@Beta
public interface ArelGroupsInterface {
    List<Object> groups();

    ArelGroupsInterface groups(List<Object> groups);
}

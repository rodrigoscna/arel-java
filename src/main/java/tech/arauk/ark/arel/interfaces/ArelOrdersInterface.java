package tech.arauk.ark.arel.interfaces;

import tech.arauk.ark.arel.annotations.Beta;

import java.util.List;

@Beta
public interface ArelOrdersInterface {
    List<Object> orders();

    ArelOrdersInterface orders(Object orders);
}

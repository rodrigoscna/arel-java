package tech.arauk.ark.arel.interfaces;

import tech.arauk.ark.arel.annotations.Beta;

@Beta
public interface ArelFramingInterface {
    Object framing();

    ArelFramingInterface framing(Object framing);
}

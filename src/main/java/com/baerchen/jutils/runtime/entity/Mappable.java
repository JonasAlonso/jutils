package com.baerchen.jutils.runtime.entity;

import java.util.function.Function;

public interface Mappable {

    /**
     * Converts this object into a safe string using the provided mapper function.
     * If mapping fails, a fallback string is returned.
     */
    default String toSafeString(Function<Object, String> mapper) {
        try {
            return mapper.apply(this);
        } catch (Exception e) {
            return String.format("[Unserializable %s: %s]", this.getClass().getSimpleName(), e.getMessage());
        }
    }

    /**
     * Indicates whether this object is in a state that can be serialized meaningfully.
     * Example of use
     * public boolean isValidInput() {
     *     return name != null && !name.isBlank();
     * }
     */
     default boolean isValidInput() {
        return true;
    }

    /**
     * Throws if the object is not valid for mapping.
     * Can be used before calling `toSafeString`.
     */
    default void throwIfInvalidInput() {
        if (!isValidInput()) {
            throw new IllegalStateException(
                    String.format("Mappable input is not valid for serialization: [%s]", this.getClass().getSimpleName())
            );
        }
    }
}

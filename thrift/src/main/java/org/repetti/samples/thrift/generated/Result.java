/**
 * Autogenerated by Thrift Compiler (0.9.1)
 * <p/>
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *
 * @generated
 */
package org.repetti.samples.thrift.generated;

import org.apache.thrift.TEnum;

public enum Result implements TEnum {
    SUCCESS(1),
    FAIL(2);

    private final int value;

    private Result(int value) {
        this.value = value;
    }

    /**
     * Find a the enum type by its integer value, as defined in the Thrift IDL.
     * @return null if the value is not found.
     */
    public static Result findByValue(int value) {
        switch (value) {
            case 1:
                return SUCCESS;
            case 2:
                return FAIL;
            default:
                return null;
        }
    }

    /**
     * Get the integer value of this enum value, as defined in the Thrift IDL.
     */
    public int getValue() {
        return value;
    }
}
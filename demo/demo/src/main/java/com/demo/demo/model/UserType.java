package com.demo.demo.model;


public enum UserType {
    ENGINEER(0),
    MANAGER(1);

    private final int value;

    UserType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

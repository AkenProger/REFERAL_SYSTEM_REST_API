package com.example.referal_system.enums;

public enum Statuses {
    ACTIVE("Активный"),NOT_ACTIVE("Не активный!"),ACCEPTED("Принято");

    private String message;

    Statuses(String message) {
        this.message = message;
    }

    public static Statuses of(int ordinal) {
        return values()[ordinal];
    }

    public String getMessage() {
        return message;
    }
}

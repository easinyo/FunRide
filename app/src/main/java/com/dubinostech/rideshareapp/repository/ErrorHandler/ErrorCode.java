package com.dubinostech.rideshareapp.repository.ErrorHandler;

public enum ErrorCode {
    ENTER_EMAIL(0),
    EMAIL_INVALID(1),
    ENTER_PASSWORD(2),
    PASSWORD_INVALID(3),
    LOGIN_FAILED(4),
    SIGNUP_FAILED(5),
    PASSWORD_DONT_MATCH(6),
    SEARCH_FAILED(8),
    POST_FAILED(7);

    private final int id;

    ErrorCode(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }
}

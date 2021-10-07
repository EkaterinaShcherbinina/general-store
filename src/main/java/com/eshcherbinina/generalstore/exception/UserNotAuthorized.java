package com.eshcherbinina.generalstore.exception;

public class UserNotAuthorized extends CustomException{
    public UserNotAuthorized(String message, String details) {
        super(message);
    }

    public UserNotAuthorized(String message) {
        super(message);
    }
}

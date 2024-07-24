package com.nimbleways.springboilerplate.exceptions;

public class OrderNotFoundException extends Throwable {
    public OrderNotFoundException(String message) {
        super(message);
    }
}

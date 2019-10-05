package com.example.dialer;

class WrongCallException extends Exception {
    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    WrongCallException(String message){
        super();
        this.message = message;
    }
}
package com.example.demo.support.exception;

public class ResponseMessage {
    private String message;

    public ResponseMessage(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
}

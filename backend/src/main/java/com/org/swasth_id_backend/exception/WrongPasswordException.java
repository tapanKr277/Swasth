package com.org.swasth_id_backend.exception;

public class WrongPasswordException extends Exception{
    public WrongPasswordException(String msg){
        super(msg);
    }
}

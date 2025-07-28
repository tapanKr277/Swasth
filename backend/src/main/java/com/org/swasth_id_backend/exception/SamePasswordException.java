package com.org.swasth_id_backend.exception;

public class SamePasswordException extends Exception{
    public SamePasswordException(String msg){
        super(msg);
    }
}

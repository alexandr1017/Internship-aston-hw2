package com.alexandr1017.edtechschool.exception;

public class ItemNotFoundException extends RuntimeException{
    public ItemNotFoundException (String message){
        super(message);
    }
}

package com.alex.miaomiao.exception;

/**
 * Created by Feng on 2020/10/20.
 */
public class NotFoundException extends RuntimeException{
    public NotFoundException(String regionCode) {
        super("cannot found city with code: " + regionCode);
    }
}

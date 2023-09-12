package com.aptech.group.exception;

import java.io.Serial;

public class InvalidPermissionException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1L;

    public InvalidPermissionException(String msg) {
        super(msg);
    }
}

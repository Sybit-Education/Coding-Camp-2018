/*
 * Copyright (c) 2018 Sybit GmbH. All rights reserved.
 */
package edu.sybit.codingcamp.battleship.exception;

/**
 *
 * @author ssr
 */
public class PlayerException extends Exception {

    public PlayerException(String message) {
        super(message);
    }

    public PlayerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
    
}

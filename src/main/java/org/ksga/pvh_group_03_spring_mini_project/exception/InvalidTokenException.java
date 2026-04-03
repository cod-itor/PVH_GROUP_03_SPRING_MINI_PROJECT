package org.ksga.pvh_group_03_spring_mini_project.exception;

public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException(String message) {
        super(message);
    }
}

package org.ksga.pvh_group_03_spring_mini_project.exception;

public class UserAlreadyExistException extends RuntimeException {
    public UserAlreadyExistException(String message) {
        super(message );
    }
}

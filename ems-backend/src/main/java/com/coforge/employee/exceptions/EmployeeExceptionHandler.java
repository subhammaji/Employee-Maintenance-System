package com.coforge.employee.exceptions;

public class EmployeeExceptionHandler extends RuntimeException {
    public EmployeeExceptionHandler() {
        super();
    }

    public EmployeeExceptionHandler(String message) {
        super(message);
    }

    public EmployeeExceptionHandler(Throwable cause) {
        super(cause);
    }

    public EmployeeExceptionHandler(String message, Throwable cause) {
        super(message, cause);
    }

    @java.io.Serial
    private static final long serialVersionUID = 1L;
}

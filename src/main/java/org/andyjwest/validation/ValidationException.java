package org.andyjwest.validation;

import java.util.List;

class ValidationException extends Exception {
    private final List<ValidationError> errors;

    ValidationException(List<ValidationError> errors) {
        this.errors = errors;
    }

    public List<ValidationError> getErrors() {
        return errors;
    }
}

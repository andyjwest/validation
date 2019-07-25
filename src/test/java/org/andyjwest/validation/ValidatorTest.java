package org.andyjwest.validation;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ValidatorTest {

    private Validator validator = new Validator();

    @Test
    public void runIfValid() {
        TestValidationError errorOne = new TestValidationError();
        TestValidationError errorTwo = new TestValidationError();
        try {
            validator.runIfValid(Object::new,
                    () -> errorOne,
                    () -> null,
                    () -> errorTwo);
        }catch(ValidationException exception){
            List<ValidationError> errors = exception.getErrors();
            assertEquals(2, errors.size());
            assertTrue(errors.contains(errorOne));
            assertTrue(errors.contains(errorTwo));
        }
    }

    @Test
    public void runIfValidClean() {
        try {
            Object result = validator.runIfValid(Object::new,
                    () -> null,
                    () -> null,
                    () -> null);
            assertNotNull(result);
        }catch(ValidationException exception){
            fail();
        }
    }

    @Test
    public void validate() {
        try {
            validator.validate(
                    () -> null,
                    () -> null,
                    () -> null);
        }catch(ValidationException exception){
            fail();
        }
    }

    private class TestValidationError implements ValidationError{ }
}

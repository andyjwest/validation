package org.andyjwest.validation;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 *
 */
public class Validator {

    /**
     *
     * @param validators an array of Suppliers that return a ValidationError (or hopefully null)
     * @return a list of ValidationErrors provided by the validators
     */
    @SafeVarargs
    private final List<ValidationError> getErrors(Supplier<ValidationError>... validators){
        return Arrays.stream(validators).parallel()
                .map(Supplier::get)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    /**
     *
     * @param functionToCallIfValidationPasses the function that will be executed if none of the suppliers return a ValidationError
     * @param validators an array of Suppliers that return a ValidationError (or hopefully null)
     * @param <T> The return type of the function that will be executed if there are no validation errors
     * @return the result of the function passed in as the first parameter
     * @throws ValidationException if any of the validators return a ValidationError
     */
    @SafeVarargs
    public final <T> T runIfValid(Supplier<T> functionToCallIfValidationPasses, Supplier<ValidationError>... validators) throws ValidationException {
        validate(validators);
        return functionToCallIfValidationPasses.get();
    }

    /**
     *
     * @param validators an array of Suppliers that return a ValidationError (or hopefully null)
     * @throws ValidationException if any of the validators return a ValidationError
     */
    @SafeVarargs
    public final void validate(Supplier<ValidationError>... validators) throws ValidationException {
        List<ValidationError> errors = getErrors(validators);
        if(!errors.isEmpty()){
            throw new ValidationException(errors);
        }
    }


}

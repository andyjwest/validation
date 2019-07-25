package org.andyjwest.validation;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public abstract class Validator {

    public static List<ValidationError> getErrors(Supplier<ValidationError>[] validators){
        return Arrays.stream(validators)
                .map(Supplier::get)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @SafeVarargs
    public static <T> T runIfValid(Supplier<T> functionToCallIfValidationPasses, Supplier<ValidationError>... validators) throws ValidationException {
        validate(validators);
        return functionToCallIfValidationPasses.get();
    }

    @SafeVarargs
    public static void validate(Supplier<ValidationError>... validators) throws ValidationException {
        List<ValidationError> errors = getErrors(validators);
        if(!errors.isEmpty()){
            throw new ValidationException(errors);
        }
    }


}

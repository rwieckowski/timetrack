package pl.rawie.timetrack.domain.validator;

import org.springframework.validation.*;

public class ValidatorUtils {
    public static void invoke(Validator validator, Object target, String objectName) throws ValidationError {
        Errors errors = new BeanPropertyBindingResult(target, objectName);
        ValidationUtils.invokeValidator(validator, target, errors);
        if (errors.hasErrors())
            throw new ValidationError(errors);
    }
}

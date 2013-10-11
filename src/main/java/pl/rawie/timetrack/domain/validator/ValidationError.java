package pl.rawie.timetrack.domain.validator;

import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import pl.rawie.timetrack.domain.model.DomainError;

public class ValidationError extends DomainError {
    private Errors errors;

    protected ValidationError() {
        setErrors(new BeanPropertyBindingResult(null, ""));
    }

    public ValidationError(Errors errors) {
        setErrors(errors);
    }

    public Errors getErrors() {
        return errors;
    }

    private void setErrors(Errors errors) {
        this.errors = errors;
    }
}

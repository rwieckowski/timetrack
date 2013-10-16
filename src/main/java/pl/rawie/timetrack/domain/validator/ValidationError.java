package pl.rawie.timetrack.domain.validator;

import com.google.common.base.Preconditions;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

public class ValidationError extends RuntimeException {
    private Errors errors;

    public ValidationError() {
        this.errors = new BeanPropertyBindingResult("", "");
    }

    public ValidationError(Errors errors) {
        Preconditions.checkNotNull(errors, "errors");
        this.errors = errors;
    }

    public Errors getErrors() {
        return errors;
    }
}

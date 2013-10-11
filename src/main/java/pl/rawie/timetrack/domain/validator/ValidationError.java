package pl.rawie.timetrack.domain.validator;

import org.springframework.validation.Errors;
import pl.rawie.timetrack.domain.model.DomainError;

public class ValidationError extends DomainError {
    private Errors errors;

    public ValidationError(Errors errors) {
        this.errors = errors;
    }

    public Errors getErrors() {
        return errors;
    }
}

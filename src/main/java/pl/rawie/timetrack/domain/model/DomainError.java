package pl.rawie.timetrack.domain.model;

import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import java.util.Locale;
import java.util.ResourceBundle;

public class DomainError extends RuntimeException {
    private DomainErrorCode code;
    private Errors errors = new BeanPropertyBindingResult(null, "");

    public DomainError(DomainErrorCode code) {
        this.code = code;
    }

    public DomainError(Errors errors) {
        this.code = DomainErrorCode.VALIDATION_FAILED;
        this.errors = errors;
    }

    public DomainErrorCode getCode() {
        return code;
    }

    public Errors getErrors() {
        return errors;
    }

    @Override
    public String getMessage() {
        return getResourceMessage(Locale.getDefault());
    }

    @Override
    public String getLocalizedMessage() {
        return getResourceMessage(Locale.getDefault());
    }

    private String getResourceMessage(Locale locale) {
        ResourceBundle bundle = ResourceBundle.getBundle("DomainError", locale);
        return (bundle.containsKey(code.name())) ? bundle.getString(code.name()) : "Unknown error";
    }
}

package pl.rawie.timetrack.domain.shared;

import pl.rawie.timetrack.domain.model.DomainErrorCode;

import java.util.Locale;
import java.util.ResourceBundle;

public class DomainError extends RuntimeException {
    private String rejected;
    private DomainErrorCode code;

    public DomainError(DomainErrorCode code) {
        this.code = code;
        this.rejected = null;
    }

    public DomainError(DomainErrorCode code, String rejected) {
        this.code = code;
        this.rejected = rejected;
    }

    public String getRejected() {
        return rejected;
    }

    public DomainErrorCode getCode() {
        return code;
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
        return (bundle.containsKey(code.name())) ? bundle.getString(code.name()) : "Missing message: " + code.name();
    }
}

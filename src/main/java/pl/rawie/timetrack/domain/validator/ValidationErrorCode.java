package pl.rawie.timetrack.domain.validator;

public enum ValidationErrorCode {
    REQUIRED;

    public String code() {
        return name().toLowerCase().replace('_', '.');
    }
}

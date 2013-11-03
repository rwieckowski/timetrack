package pl.rawie.timetrack.utils.validation;

public class Violation {
    private String field;
    private String message;

    public Violation(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "Violation{" +
                "field='" + field + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}

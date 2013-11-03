package pl.rawie.timetrack.utils.validation;

public class ValidationException extends RuntimeException {
    private Violations violations;

    public ValidationException(Violations violations) {
        this.violations = violations;
    }

    public Violations getViolations() {
        return violations;
    }
}

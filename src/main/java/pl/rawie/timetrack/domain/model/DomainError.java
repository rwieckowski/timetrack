package pl.rawie.timetrack.domain.model;

public class DomainError extends RuntimeException {
    public DomainError() {
    }

    public DomainError(String message) {
        super(message);
    }
}

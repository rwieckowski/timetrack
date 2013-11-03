package pl.rawie.timetrack.utils.validation;

public interface Validator<T> {
    void validate(T object, Violations violations);
}

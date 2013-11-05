package pl.rawie.validation;

public interface BusinessRule<T> {
    void check(T object, Errors errors);
}

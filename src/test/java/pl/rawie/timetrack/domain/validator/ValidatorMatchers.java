package pl.rawie.timetrack.domain.validator;

public class ValidatorMatchers {
    public static HasFieldErrorMatcher hasFieldError(String field, String errorCode) {
        return new HasFieldErrorMatcher(field, errorCode);
    }
}

package pl.rawie.timetrack.domain.validator;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.springframework.validation.FieldError;
import pl.rawie.timetrack.domain.model.DomainError;

public class HasFieldErrorMatcher extends BaseMatcher<DomainError> {
    private String field;
    private String errorCode;

    public HasFieldErrorMatcher(String field, String errorCode) {
        this.field = field;
        this.errorCode = errorCode;
    }

    @Override
    public boolean matches(Object o) {
        DomainError ex = (DomainError)o;

        for (FieldError error : ex.getErrors().getFieldErrors(field)) {
            if (error.getCode().equals(errorCode))
                return true;
        }

        return false;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("Field ");
        description.appendValue(field);
        description.appendText(" has no error ");
        description.appendValue(errorCode);
    }
}

package pl.rawie.timetrack.domain.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import pl.rawie.timetrack.domain.model.Entry;

public class AddEntryValidator implements Validator {
    @Override
    public boolean supports(Class clazz) {
        return Entry.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Entry entry = (Entry)target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "summary", "required");
    }
}

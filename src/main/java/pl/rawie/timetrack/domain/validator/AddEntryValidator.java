package pl.rawie.timetrack.domain.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import pl.rawie.timetrack.domain.model.Entry;

import static org.springframework.validation.ValidationUtils.rejectIfEmpty;
import static org.springframework.validation.ValidationUtils.rejectIfEmptyOrWhitespace;

public class AddEntryValidator implements Validator {
    @Override
    public boolean supports(Class clazz) {
        return Entry.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Entry entry = (Entry)target;
        rejectIfEmptyOrWhitespace(errors, "summary", "required");
        rejectIfEmpty(errors, "start", "required");
        rejectIfEmpty(errors, "end", "required");
    }
}

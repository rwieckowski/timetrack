package pl.rawie.timetrack.domain.model;

import pl.rawie.timetrack.utils.validation.Validator;
import pl.rawie.timetrack.utils.validation.Violations;

public class EntryAddingValidator implements Validator<Entry> {
    @Override
    public void validate(Entry object, Violations violations) {
        violations.rejectIfEmpty("summary");
        if (object.getStart().isAfter(object.getEnd()))
            violations.reject("invalid.time.range");
    }
}

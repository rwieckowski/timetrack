package pl.rawie.timetrack.domain.model;

import pl.rawie.validation.BusinessRule;
import pl.rawie.validation.Errors;

public class EntryRule implements BusinessRule<Entry> {
    @Override
    public void check(Entry object, Errors errors) {
        // TODO: put messages to resource bundle
        if (object.getStart().isAfter(object.getEnd()))
            errors.reject("end", "must be after start");
    }
}

package pl.rawie.timetrack.application.impl;

import pl.rawie.timetrack.application.TimeTrackService;
import pl.rawie.timetrack.domain.model.Entry;
import pl.rawie.timetrack.domain.validator.AddEntryValidator;
import pl.rawie.timetrack.domain.validator.ValidatorUtils;

public class TimeTrackServiceImpl implements TimeTrackService {
    private AddEntryValidator addEntryValidator;

    public TimeTrackServiceImpl() {
        setAddEntryValidator(new AddEntryValidator());
    }

    @Override
    public void addEntry(Entry entry) {
        ValidatorUtils.invoke(addEntryValidator, entry, "entry");
    }

    protected void setAddEntryValidator(AddEntryValidator addEntryValidator) {
        this.addEntryValidator = addEntryValidator;
    }
}

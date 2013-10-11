package pl.rawie.timetrack.application.impl;

import com.google.common.base.Preconditions;
import com.google.common.collect.Range;
import pl.rawie.timetrack.application.TimeTrackService;
import pl.rawie.timetrack.domain.model.Entry;
import pl.rawie.timetrack.domain.model.EntryRepository;
import pl.rawie.timetrack.domain.validator.AddEntryValidator;
import pl.rawie.timetrack.domain.validator.ValidatorUtils;

import java.util.List;

public class TimeTrackServiceImpl implements TimeTrackService {
    private EntryRepository entryRepository;
    private AddEntryValidator addEntryValidator;

    public TimeTrackServiceImpl() {
        setAddEntryValidator(new AddEntryValidator());
    }

    @Override
    public void addEntry(Entry entry) {
        ValidatorUtils.invoke(getAddEntryValidator(), entry, "entry");
        List<Entry> entries = getEntryRepository().findAllByDateRange(Range.closed(entry.getStart(), entry.getEnd()));
    }

    private EntryRepository getEntryRepository() {
        Preconditions.checkState(entryRepository != null, "entryRepository");
        return entryRepository;
    }

    public void setEntryRepository(EntryRepository entryRepository) {
        this.entryRepository = entryRepository;
    }

    private AddEntryValidator getAddEntryValidator() {
        Preconditions.checkState(addEntryValidator != null, "addEntryValidator");
        return addEntryValidator;
    }

    public void setAddEntryValidator(AddEntryValidator addEntryValidator) {
        this.addEntryValidator = addEntryValidator;
    }
}

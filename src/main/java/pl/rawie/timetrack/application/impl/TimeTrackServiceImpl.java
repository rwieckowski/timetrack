package pl.rawie.timetrack.application.impl;

import com.google.common.base.Preconditions;
import com.google.common.collect.Range;
import pl.rawie.timetrack.application.TimeTrackService;
import pl.rawie.timetrack.domain.model.DomainError;
import pl.rawie.timetrack.domain.model.Entry;
import pl.rawie.timetrack.domain.model.EntryRepository;
import pl.rawie.timetrack.domain.service.OverlapService;
import pl.rawie.timetrack.domain.validator.AddEntryValidator;
import pl.rawie.timetrack.domain.validator.ValidationError;
import pl.rawie.timetrack.domain.validator.ValidatorUtils;

import java.util.Date;
import java.util.List;

public class TimeTrackServiceImpl implements TimeTrackService {
    private EntryRepository entryRepository;
    private AddEntryValidator addEntryValidator;
    private OverlapService overlapService;

    public TimeTrackServiceImpl() {
        setAddEntryValidator(new AddEntryValidator());
    }

    @Override
    public void addEntry(Entry entry) {
        Preconditions.checkArgument(entry != null, "entry");
        ValidatorUtils.invoke(getAddEntryValidator(), entry, "entry");
        Range<Date> range = Range.closed(entry.getStart(), entry.getEnd());
        List<Entry> entries = getEntryRepository().findAllByDateRange(range);
        if (getOverlapService().overlaps(entry, entries))
            throw new DomainError("overlapped.entry");
        getEntryRepository().store(entry);
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

    private OverlapService getOverlapService() {
        Preconditions.checkState(overlapService != null, "overlapService");
        return overlapService;
    }

    public void setOverlapService(OverlapService overlapService) {
        this.overlapService = overlapService;
    }
}

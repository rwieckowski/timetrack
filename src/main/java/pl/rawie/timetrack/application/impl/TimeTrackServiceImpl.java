package pl.rawie.timetrack.application.impl;

import com.google.common.base.Preconditions;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.rawie.timetrack.application.TimeTrackService;
import pl.rawie.timetrack.domain.model.AggregateEntry;
import pl.rawie.timetrack.domain.model.DomainError;
import pl.rawie.timetrack.domain.model.Entry;
import pl.rawie.timetrack.domain.model.EntryRepository;
import pl.rawie.timetrack.domain.service.OverlapService;
import pl.rawie.timetrack.domain.service.impl.OverlapServiceImpl;
import pl.rawie.timetrack.domain.validator.AddEntryValidator;
import pl.rawie.timetrack.domain.validator.ValidatorUtils;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class TimeTrackServiceImpl implements TimeTrackService {
    @Autowired
    private EntryRepository entryRepository;
    private AddEntryValidator addEntryValidator;
    private OverlapService overlapService;

    public TimeTrackServiceImpl() {
        setAddEntryValidator(new AddEntryValidator());
        setOverlapService(new OverlapServiceImpl());
    }

    @Override
    public void addEntry(Entry entry) {
        Preconditions.checkArgument(entry != null, "entry");
        ValidatorUtils.invoke(getAddEntryValidator(), entry, "entry");
        List<Entry> entries = getEntryRepository().findAllByDate(entry.getStart());
        if (getOverlapService().overlaps(entry, entries))
            throw new DomainError("overlapped.entry");
        getEntryRepository().store(entry);
    }

    @Override
    public List<AggregateEntry> getWeekSummary(DateTime date) {
        return Collections.emptyList();
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

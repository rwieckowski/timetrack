package pl.rawie.timetrack.application.impl;

import com.google.common.base.Preconditions;
import com.google.common.collect.Range;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.rawie.timetrack.application.TimeTrackService;
import pl.rawie.timetrack.domain.model.*;
import pl.rawie.timetrack.domain.service.AggregateService;
import pl.rawie.timetrack.domain.service.OverlapService;
import pl.rawie.timetrack.domain.service.impl.AggregateServiceImpl;
import pl.rawie.timetrack.domain.service.impl.OverlapServiceImpl;
import pl.rawie.timetrack.domain.validator.AddEntryValidator;
import pl.rawie.timetrack.domain.validator.ValidatorUtils;

import java.util.List;

@Service
public class TimeTrackServiceImpl implements TimeTrackService {
    @Autowired
    private EntryRepository entryRepository;
    private AddEntryValidator addEntryValidator;
    private OverlapService overlapService;
    private AggregateService aggregateService;

    public TimeTrackServiceImpl() {
        setAddEntryValidator(new AddEntryValidator());
        setOverlapService(new OverlapServiceImpl());
        setAggregateService(new AggregateServiceImpl());
    }

    @Override
    public void addEntry(Entry entry) {
        Preconditions.checkArgument(entry != null, "entry");
        ValidatorUtils.invoke(getAddEntryValidator(), entry, "entry");
        List<Entry> entries = getEntryRepository().findAllByDate(entry.getStart());
        if (getOverlapService().overlaps(entry, entries))
            throw new DomainError(DomainErrorCode.OVERLAPPED_ENTRY);
        getEntryRepository().store(entry);
    }

    @Override
    public List<AggregateEntry> getWeekSummary(DateTime date) {
        DateTime start = date
                .withTimeAtStartOfDay()
                .minus(date.getDayOfWeek() - DateTimeConstants.MONDAY);
        Range<DateTime> range = Range.closedOpen(start, start.plusWeeks(1));
        List<Entry> entries = getEntryRepository().findAllByDateRange(range);
        List<AggregateEntry> aggregates = getAggregateService().aggregate(entries);
        aggregates = getAggregateService().normalize(aggregates);
        return aggregates;
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

    private AggregateService getAggregateService() {
        Preconditions.checkState(overlapService != null, "aggregateService");
        return aggregateService;
    }

    public void setAggregateService(AggregateService aggregateService) {
        this.aggregateService = aggregateService;
    }
}

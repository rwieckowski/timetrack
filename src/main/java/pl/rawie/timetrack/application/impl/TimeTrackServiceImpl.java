package pl.rawie.timetrack.application.impl;

import com.google.common.base.Preconditions;
import com.google.common.collect.Range;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.rawie.timetrack.application.TimeTrackService;
import pl.rawie.timetrack.domain.model.AggregateEntry;
import pl.rawie.timetrack.domain.model.DailyEntries;
import pl.rawie.timetrack.domain.model.Entry;
import pl.rawie.timetrack.domain.model.EntryRepository;
import pl.rawie.timetrack.domain.service.AggregateService;
import pl.rawie.timetrack.domain.service.impl.AggregateServiceImpl;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

@Service
public class TimeTrackServiceImpl implements TimeTrackService {
    @Autowired
    private EntryRepository entryRepository;
    private AggregateService aggregateService;

    public TimeTrackServiceImpl() {
        setAggregateService(new AggregateServiceImpl());
    }

    @Override
    public void addEntry(Entry entry) {
        checkNotNull(entry, "entry");

        DailyEntries dailyEntries = getEntryRepository().getDailyEntries(entry.getStart());
        dailyEntries.add(entry);
        getEntryRepository().storeDailyEntries(dailyEntries);
    }

    @Override
    public List<AggregateEntry> getWeekSummary(DateTime date) {
        List<Entry> entries = getEntryRepository().getWeeklyEntries(date).getEntries();
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

    private AggregateService getAggregateService() {
        Preconditions.checkState(aggregateService != null, "aggregateService");
        return aggregateService;
    }

    public void setAggregateService(AggregateService aggregateService) {
        this.aggregateService = aggregateService;
    }
}

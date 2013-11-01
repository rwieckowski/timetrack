package pl.rawie.timetrack.application.impl;

import com.google.common.base.Preconditions;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.rawie.timetrack.application.TimeTrackService;
import pl.rawie.timetrack.domain.model.*;
import pl.rawie.timetrack.domain.service.AggregateService;
import pl.rawie.timetrack.domain.service.impl.AggregateServiceImpl;

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
    public WeekSummary getWeekSummary(DateTime date) {
        WeeklyEntries weeklyEntries = getEntryRepository().getWeeklyEntries(date);
        return weeklyEntries.generateWeekSummary(getAggregateService());
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

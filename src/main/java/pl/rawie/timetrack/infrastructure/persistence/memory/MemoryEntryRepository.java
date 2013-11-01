package pl.rawie.timetrack.infrastructure.persistence.memory;

import com.google.common.collect.Range;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.springframework.stereotype.Repository;
import pl.rawie.timetrack.domain.model.DailyEntries;
import pl.rawie.timetrack.domain.model.Entry;
import pl.rawie.timetrack.domain.model.EntryRepository;
import pl.rawie.timetrack.domain.model.WeeklyEntries;
import pl.rawie.timetrack.utils.Today;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MemoryEntryRepository implements EntryRepository {
    private List<Entry> entries = new ArrayList<Entry>();

    public MemoryEntryRepository() {
        this.entries.add(new Entry("start", Today.withTime(8), Today.withTime(9)));
    }

    @Override
    public WeeklyEntries getWeeklyEntries(DateTime date) {
        DateTime start = date
                .withTimeAtStartOfDay()
                .minus(date.getDayOfWeek() - DateTimeConstants.MONDAY);
        Range<DateTime> week = Range.closedOpen(start, start.plusWeeks(1));
        return new WeeklyEntries(week, findEntriesFor(week));
    }

    @Override
    public DailyEntries getDailyEntries(DateTime date) {
        Range<DateTime> day = Range.openClosed(date.withTimeAtStartOfDay(), date.withTimeAtStartOfDay().plusDays(1));
        return new DailyEntries(day.lowerEndpoint(), findEntriesFor(day));
    }

    private List<Entry> findEntriesFor(Range<DateTime> range) {
        List<Entry> found = new ArrayList<Entry>();
        for (Entry entry : entries)
            if (range.contains(entry.getStart()))
                found.add(entry);
        return found;
    }

    @Override
    public void storeDailyEntries(DailyEntries dailyEntries) {
        for (Entry entry : dailyEntries.getEntries())
            storeEntry(entry);
    }

    private void storeEntry(Entry entry) {
        if (!entries.contains(entry))
            entries.add(entry);
    }
}

package pl.rawie.timetrack.infrastructure.persistence.memory;

import com.google.common.collect.Range;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;
import pl.rawie.timetrack.domain.model.DailyEntries;
import pl.rawie.timetrack.domain.model.Entry;
import pl.rawie.timetrack.domain.model.EntryRepository;
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
    public List<Entry> findAllByDateRange(Range<DateTime> range) {
        List<Entry> result = new ArrayList<Entry>();
        for (Entry entry : entries) {
            try {
                if (!range.intersection(entry.getDateTimeRange()).isEmpty())
                    result.add(entry);
            } catch (IllegalArgumentException e) {
                // no intersection
            }
        }
        return result;
    }

    @Override
    public DailyEntries getDailyEntries(DateTime start) {
        List<Entry> entries = new ArrayList<Entry>();
        Range<DateTime> day = Range.openClosed(start.withTimeAtStartOfDay(), start.withTimeAtStartOfDay().plusDays(1));
        for (Entry entry : this.entries) {
            if (day.contains(entry.getStart()))
                entries.add(entry);
        }
        return new DailyEntries(entries);
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

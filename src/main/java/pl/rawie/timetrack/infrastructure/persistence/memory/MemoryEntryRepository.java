package pl.rawie.timetrack.infrastructure.persistence.memory;

import com.google.common.collect.Range;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;
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
    public List<Entry> findAllByDate(DateTime date) {
        date = date.withTimeAtStartOfDay();
        return findAllByDateRange(Range.closedOpen(date, date.plusDays(1)));
    }

    @Override
    public void store(Entry entry) {
        entries.add(entry);
    }
}

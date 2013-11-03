package pl.rawie.timetrack.domain.model;

import org.joda.time.DateTime;
import pl.rawie.timetrack.domain.shared.Aggregate;
import pl.rawie.timetrack.domain.shared.DomainError;

import java.util.Collections;
import java.util.List;

public class DailyEntries implements Aggregate {
    private DateTime day;
    private List<Entry> entries;

    public DailyEntries(DateTime day, List<Entry> entries) {
        this.day = day;
        this.entries = entries;
    }

    public void add(Entry entry) {
        rejectOverlapped(entry);
        entries.add(entry);
    }

    private void rejectOverlapped(Entry entry) {
        for (Entry existing : entries) {
            try {
                if (!entry.getDateTimeRange().intersection(existing.getDateTimeRange()).isEmpty())
                    throw new DomainError(DomainErrorCode.OVERLAPPED_ENTRY);
            } catch (IllegalArgumentException e) {
                // no intersection
            }
        }
    }

    public DateTime getDay() {
        return day;
    }

    public List<Entry> getEntries() {
        return Collections.unmodifiableList(entries);
    }
}

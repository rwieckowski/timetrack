package pl.rawie.timetrack.domain.model;

import java.util.Collections;
import java.util.List;

public class DailyEntries implements Aggregate {
    private List<Entry> entries;

    public DailyEntries(List<Entry> entries) {
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

    public List<Entry> getEntries() {
        return Collections.unmodifiableList(entries);
    }
}

package pl.rawie.timetrack.domain.model;

import com.google.common.base.Preconditions;
import org.joda.time.Duration;

import java.util.List;

public class AggregateEntry {
    private List<Entry> entries;
    private Duration delta = Duration.ZERO;

    public AggregateEntry(List<Entry> entries) {
        Preconditions.checkNotNull(entries);
        this.entries = entries;
    }

    public List<Entry> getEntries() {
        return entries;
    }

    public String getSummary() {
        return entries.get(0).getSummary();
    }

    public Duration getDuration() {
        Duration duration = Duration.standardMinutes(0);
        for (Entry entry : entries)
            duration = duration.plus(entry.getDuration());
        return duration;
    }

    public Duration getDelta() {
        return delta;
    }

    public void setDelta(Duration delta) {
        this.delta = delta;
    }

    public Duration getNormalizedDuration() {
        long minutes = getDuration().getStandardMinutes();
        long units = (minutes + 15) / 30;
        long normalizedMinutes = ((units >= 1) ? units : 1) * 30;
        return Duration.standardMinutes(normalizedMinutes);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AggregateEntry that = (AggregateEntry) o;

        if (!entries.equals(that.entries)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return entries.hashCode();
    }

    @Override
    public String toString() {
        return "AggregateEntry{" +
                "entries=" + entries +
                '}';
    }
}

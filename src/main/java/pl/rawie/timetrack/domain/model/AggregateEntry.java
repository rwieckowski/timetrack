package pl.rawie.timetrack.domain.model;

import com.google.common.base.Preconditions;
import org.joda.time.Duration;
import pl.rawie.timetrack.utils.Normalization;

import java.util.List;

public class AggregateEntry {
    private List<Entry> entries;
    private Duration duration;
    private Duration delta = Duration.ZERO;

    public AggregateEntry(List<Entry> entries) {
        Preconditions.checkNotNull(entries);
        this.entries = entries;
        this.duration = duration();
    }

    private Duration duration() {
        Duration duration = Duration.standardMinutes(0);
        for (Entry entry : entries)
            duration = duration.plus(entry.getDuration());
        return duration;
    }

    public List<Entry> getEntries() {
        return entries;
    }

    public String getSummary() {
        return entries.get(0).getSummary();
    }

    public Duration getDuration() {
        return duration;
    }

    public Duration getDelta() {
        return delta;
    }

    public void resetDelta() {
        delta = Normalization.deltaFor(getDuration());
    }

    public void incDelta() {
        delta = delta.plus(Duration.standardMinutes(30));
    }

    public void decDelta() {
        delta = delta.minus(Duration.standardMinutes(30));
    }

    public Duration getNormalizedDuration() {
        return getDuration().plus(delta);
    }

    public double getRelativeError() {
        return 1.0 * delta.getStandardMinutes() / duration.getStandardMinutes();
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

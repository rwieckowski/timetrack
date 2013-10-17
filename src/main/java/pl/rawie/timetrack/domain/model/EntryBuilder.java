package pl.rawie.timetrack.domain.model;

import org.joda.time.DateTime;
import org.joda.time.Duration;

public class EntryBuilder {
    private String summary;
    private DateTime start;
    private DateTime end;

    private EntryBuilder() {
    }

    public static EntryBuilder anEntry() {
        return new EntryBuilder();
    }

    public EntryBuilder withSummary(String summary) {
        this.summary = summary;
        return this;
    }

    public EntryBuilder withStart(DateTime start) {
        this.start = start;
        return this;
    }

    public EntryBuilder withEnd(DateTime end) {
        this.end = end;
        return this;
    }

    public EntryBuilder withDurationInHours(int hours) {
        this.end = start.plus(Duration.standardHours(hours));
        return this;
    }

    public EntryBuilder withDurationInMinutes(int minutes) {
        this.end = start.plus(Duration.standardMinutes(minutes));
        return this;
    }

    public Entry build() {
        return new Entry(summary, start, end);
    }
}

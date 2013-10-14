package pl.rawie.timetrack.domain.model;

import org.joda.time.DateTime;

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

    public Entry build() {
        return new Entry(summary, start, end);
    }
}

package pl.rawie.timetrack.domain.model;

import java.util.Date;

public class EntryBuilder {
    private String summary;
    private Date start;
    private Date end;

    private EntryBuilder() {
    }

    public static EntryBuilder anEntry() {
        return new EntryBuilder();
    }

    public EntryBuilder withSummary(String summary) {
        this.summary = summary;
        return this;
    }

    public EntryBuilder withStart(Date start) {
        this.start = start;
        return this;
    }

    public EntryBuilder withEnd(Date end) {
        this.end = end;
        return this;
    }

    public Entry build() {
        Entry entry = new Entry(summary, start, end);
        return entry;
    }
}

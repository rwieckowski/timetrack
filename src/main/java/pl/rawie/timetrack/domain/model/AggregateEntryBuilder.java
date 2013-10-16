package pl.rawie.timetrack.domain.model;

import java.util.List;

public class AggregateEntryBuilder {
    private List<Entry> entries;

    private AggregateEntryBuilder() {
    }

    public static AggregateEntryBuilder anAggregateEntry() {
        return new AggregateEntryBuilder();
    }

    public AggregateEntryBuilder withEntries(List<Entry> entries) {
        this.entries = entries;
        return this;
    }

    public AggregateEntry build() {
        AggregateEntry aggregateEntry = new AggregateEntry(entries);
        return aggregateEntry;
    }
}

package pl.rawie.timetrack.domain.model;

import java.util.List;

public class AggregateEntry {
    private List<Entry> entries;

    public AggregateEntry(List<Entry> entries) {
        this.entries = entries;
    }

    public List<Entry> getEntries() {
        return entries;
    }

    public String getSummary() {
        return entries.get(0).getSummary();
    }
}

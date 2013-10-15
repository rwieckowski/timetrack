package pl.rawie.timetrack.domain.model;

import com.google.common.base.Preconditions;

import java.util.List;

public class AggregateEntry {
    private List<Entry> entries;

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

    public boolean isAggregateFor(Entry entry) {
        return entries.isEmpty() || entry.sameAs(entries.get(0));
    }

    public void addEntry(Entry entry) {
        Preconditions.checkArgument(isAggregateFor(entry), "Aggregate must contain entries of the same type");
        entries.add(entry);
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

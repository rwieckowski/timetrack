package pl.rawie.timetrack.domain.model;

import java.util.List;

public class WeeklyEntries {
    private List<Entry> entries;

    public WeeklyEntries(List<Entry> entries) {
        this.entries = entries;
    }

    public List<Entry> getEntries() {
        return entries;
    }
}

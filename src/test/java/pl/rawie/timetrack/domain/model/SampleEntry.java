package pl.rawie.timetrack.domain.model;

public class SampleEntry {
    public static EntryBuilder builder() {
        return EntryBuilder
                .anEntry()
                .withSummary("summary")
                .withStart(null)
                .withEnd(null);
    }

    public static Entry entry() {
        return builder().build();
    }
}

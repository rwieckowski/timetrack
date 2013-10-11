package pl.rawie.timetrack.domain.model;

import pl.rawie.timetrack.utils.Today;

public class SampleEntry {
    public static EntryBuilder builder() {
        return EntryBuilder
                .anEntry()
                .withSummary("summary")
                .withStart(Today.withTime(12))
                .withEnd(Today.withTime(14));
    }

    public static Entry entry() {
        return builder().build();
    }

    public static Entry entryWithHours(int start, int end) {
        return builder()
                .withStart(Today.withTime(start))
                .withEnd(Today.withTime(end))
                .build();
    }
}

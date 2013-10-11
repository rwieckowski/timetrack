package pl.rawie.timetrack.domain.model;

import org.joda.time.DateTime;

public class SampleEntry {
    public static EntryBuilder builder() {
        DateTime today = DateTime.now().withTimeAtStartOfDay();
        return EntryBuilder
                .anEntry()
                .withSummary("summary")
                .withStart(today.withTime(12, 0, 0, 0).toDate())
                .withEnd(today.withTime(14, 0, 0, 0).toDate());
    }

    public static Entry entry() {
        return builder().build();
    }
}

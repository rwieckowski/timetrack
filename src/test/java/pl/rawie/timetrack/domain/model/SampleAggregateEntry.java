package pl.rawie.timetrack.domain.model;

import java.util.Arrays;

public class SampleAggregateEntry {
    public static AggregateEntry withDurationInHours(int hours) {
        Entry entry = SampleEntry.builder().withDurationInHours(hours).build();
        return AggregateEntryBuilder
                .anAggregateEntry()
                .withEntries(Arrays.asList(entry))
                .build();
    }

    public static AggregateEntry withDurationInMinutes(int minutes) {
        Entry entry = SampleEntry.builder().withDurationInMinutes(minutes).build();
        return AggregateEntryBuilder
                .anAggregateEntry()
                .withEntries(Arrays.asList(entry))
                .build();
    }
}
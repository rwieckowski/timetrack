package pl.rawie.timetrack.domain.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    public static List<AggregateEntry> withDurationInMinutesAsList(Integer... minutes) {
        return withDurationInMinutesAsList(Arrays.asList(minutes));
    }

    public static List<AggregateEntry> withDurationInMinutesAsList(List<Integer> minutes) {
        List<AggregateEntry> aggregates = new ArrayList<AggregateEntry>();
        for (int m : minutes)
            aggregates.add(withDurationInMinutes(m));
        return aggregates;
    }
}
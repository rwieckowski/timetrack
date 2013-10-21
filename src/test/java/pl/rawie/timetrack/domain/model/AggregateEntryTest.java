package pl.rawie.timetrack.domain.model;

import org.joda.time.Duration;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class AggregateEntryTest {
    private AggregateEntry makeAggregate(List<Entry> entries) {
        return AggregateEntryBuilder.anAggregateEntry()
                .withEntries(entries)
                .build();
    }

    @Test
    public void duration() {
        List<Entry> entries = Arrays.asList(
                SampleEntry.builder().withDurationInHours(1).build(),
                SampleEntry.builder().withDurationInHours(2).build(),
                SampleEntry.builder().withDurationInHours(3).build());
        assertThat(makeAggregate(entries).getDuration(),
                equalTo(Duration.standardHours(6)));
    }
}

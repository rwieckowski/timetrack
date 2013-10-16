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

    private void normalizeDuration(int duration, int normalizedDuration) {
        List<Entry> entries = Arrays.asList(SampleEntry.builder().withDurationInMinutes(duration).build());
        assertThat(makeAggregate(entries).getNormalizedDuration(),
                equalTo(Duration.standardMinutes(normalizedDuration)));
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

    @Test
    public void normalizedDuration_0() {
        normalizeDuration(0, 30);
    }

    @Test
    public void normalizedDuration_44() {
        normalizeDuration(44, 30);
    }

    @Test
    public void normalizedDuration_45() {
        normalizeDuration(45, 60);
    }

    @Test
    public void normalizedDuration_60() {
        normalizeDuration(74, 60);
    }

    @Test
    public void normalizedDuration_75() {
        normalizeDuration(75, 90);
    }
}

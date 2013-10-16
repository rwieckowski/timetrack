package pl.rawie.timetrack.domain.service.impl;

import org.hamcrest.Matcher;
import org.joda.time.Duration;
import org.junit.Before;
import org.junit.Test;
import pl.rawie.timetrack.domain.model.AggregateEntry;
import pl.rawie.timetrack.domain.model.Entry;
import pl.rawie.timetrack.domain.model.SampleEntry;
import pl.rawie.timetrack.domain.service.AggregateService;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.junit.Assert.assertThat;

public class AggregateServiceImplTest {
    private AggregateService service;

    @Before
    public void setUp() {
        service = new AggregateServiceImpl();
    }

    private void aggregate(List<Entry> entries, Matcher... matchers) {
        List<AggregateEntry> aggregates = service.aggregate(entries);
        assertThat(matchers.length, equalTo(aggregates.size()));
        Iterator<AggregateEntry> iterator = aggregates.iterator();
        for (Matcher matcher : matchers)
            assertThat(iterator.next(), matcher);
    }

    private void normalizeDuration(int duration, int normalizedDuration) {
        List<Entry> entries = Arrays.asList(SampleEntry.builder().withDurationInMinutes(duration).build());
        aggregate(entries, hasProperty("normalizedDuration", equalTo(Duration.standardMinutes(normalizedDuration))));
    }

    @Test
    public void entries_empty() {
        aggregate(Arrays.<Entry>asList());
    }

    @Test
    public void entries_single() {
        List<Entry> entries = Arrays.asList(SampleEntry.entry());
        aggregate(entries, hasProperty("entries", equalTo(entries)));
    }

    @Test
    public void entries_sameType() {
        List<Entry> entries = Arrays.asList(SampleEntry.entry(), SampleEntry.entry());
        aggregate(entries, hasProperty("entries", equalTo(entries)));
    }

    @Test
    public void entries_differentType() {
        Entry entry1 = SampleEntry.builder().withSummary("summary-1").build();
        Entry entry2 = SampleEntry.builder().withSummary("summary-2").build();
        List<Entry> entries = Arrays.asList(entry1, entry2);
        aggregate(entries,
                hasProperty("entries", equalTo(Arrays.asList(entry1))),
                hasProperty("entries", equalTo(Arrays.asList(entry2))));
    }

    @Test
    public void duration() {
        List<Entry> entries = Arrays.asList(
                SampleEntry.builder().withDurationInHours(1).build(),
                SampleEntry.builder().withDurationInHours(2).build(),
                SampleEntry.builder().withDurationInHours(3).build());
        aggregate(entries, hasProperty("duration", equalTo(Duration.standardHours(6))));
    }

    @Test
    public void normalizedDuration() {
        normalizeDuration(0, 30);
        normalizeDuration(44, 30);
        normalizeDuration(45, 60);
        normalizeDuration(74, 60);
        normalizeDuration(75, 90);
    }
}

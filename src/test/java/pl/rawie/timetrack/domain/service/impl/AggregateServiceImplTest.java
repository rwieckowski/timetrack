package pl.rawie.timetrack.domain.service.impl;

import org.hamcrest.Matcher;
import org.joda.time.Duration;
import org.junit.Before;
import org.junit.Test;
import pl.rawie.timetrack.domain.model.AggregateEntry;
import pl.rawie.timetrack.domain.model.Entry;
import pl.rawie.timetrack.domain.model.SampleAggregateEntry;
import pl.rawie.timetrack.domain.model.SampleEntry;
import pl.rawie.timetrack.domain.service.AggregateService;

import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static pl.rawie.hamcrest.Matchers.containsProperty;

public class AggregateServiceImplTest {
    private AggregateService service;

    @Before
    public void setUp() {
        service = new AggregateServiceImpl();
    }

    private void assertAggregate(List<Entry> entries, Matcher matcher) {
        List<AggregateEntry> aggregates = service.aggregate(entries);
        assertThat(aggregates, matcher);
    }

    private void assertNormalize(List<AggregateEntry> aggregates, Matcher matcher) {
        List<AggregateEntry> normalized = service.normalize(aggregates);
        assertThat(normalized, matcher);
    }

    private void assertNormalizeDurationThatDelta(List<Integer> durations, List<Integer> deltas) {
        assertNormalize(toAggregateEntryList(durations),
                containsProperty("delta", toDurationList(deltas)));
    }

    private List<AggregateEntry> toAggregateEntryList(List<Integer> durations) {
        List<AggregateEntry> result = new ArrayList<AggregateEntry>();
        for (int duration : durations)
            result.add(SampleAggregateEntry.withDurationInMinutes(duration));
        return result;
    }

    private List<Duration> toDurationList(List<Integer> durations) {
        List<Duration> result = new ArrayList<Duration>();
        for (int duration : durations)
            result.add(Duration.standardMinutes(duration));
        return result;
    }

    @Test
    public void normalize_noAggregates() {
        assertNormalize(Collections.<AggregateEntry>emptyList(), is(empty()));
    }

    @Test
    public void aggregate_noEntries() {
        assertAggregate(Arrays.<Entry>asList(), is(empty()));
    }

    @Test
    public void aggregate_singleEntries() {
        List<Entry> entries = Arrays.asList(SampleEntry.entry());
        assertAggregate(entries, containsProperty("entries", Arrays.asList(entries)));
    }

    @Test
    public void aggregate_sameTypeEntries() {
        List<Entry> entries = Arrays.asList(SampleEntry.entry(), SampleEntry.entry());
        assertAggregate(entries, containsProperty("entries", Arrays.asList(entries)));
    }

    @Test
    public void aggregate_differentTypeEntries() {
        Entry entry1 = SampleEntry.builder().withSummary("summary-1").build();
        Entry entry2 = SampleEntry.builder().withSummary("summary-2").build();
        List<Entry> entries = Arrays.asList(entry1, entry2);
        assertAggregate(entries,
                containsProperty("entries", Arrays.asList(entry1), Arrays.asList(entry2)));
    }

    @Test
    public void normalize_singleAggregate() {
        assertNormalizeDurationThatDelta(Arrays.asList(10), Arrays.asList(20));
        assertNormalizeDurationThatDelta(Arrays.asList(15), Arrays.asList(15));
        assertNormalizeDurationThatDelta(Arrays.asList(20), Arrays.asList(10));
        assertNormalizeDurationThatDelta(Arrays.asList(30), Arrays.asList(0));
        assertNormalizeDurationThatDelta(Arrays.asList(40), Arrays.asList(-10));
        assertNormalizeDurationThatDelta(Arrays.asList(45), Arrays.asList(-15));
        assertNormalizeDurationThatDelta(Arrays.asList(50), Arrays.asList(10));
        assertNormalizeDurationThatDelta(Arrays.asList(60), Arrays.asList(0));
    }

    @Test
    public void normalize_multipleAggregate() {
        assertNormalizeDurationThatDelta(Arrays.asList(60, 60), Arrays.asList(0, 0));
        assertNormalizeDurationThatDelta(Arrays.asList(60, 50), Arrays.asList(0, 10));
        assertNormalizeDurationThatDelta(Arrays.asList(50, 60), Arrays.asList(10, 0));
        assertNormalizeDurationThatDelta(Arrays.asList(70, 60), Arrays.asList(-10, 0));
        assertNormalizeDurationThatDelta(Arrays.asList(60, 70), Arrays.asList(0, -10));
        //assertNormalizeDurationThatDelta(Arrays.asList(70, 70), Arrays.asList(20, -10));
        //assertNormalizeDurationThatDelta(Arrays.asList(50, 50), Arrays.asList(-20, 10));
    }
}

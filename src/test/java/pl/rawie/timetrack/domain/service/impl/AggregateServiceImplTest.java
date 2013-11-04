package pl.rawie.timetrack.domain.service.impl;

import org.hamcrest.Matcher;
import org.joda.time.Duration;
import org.junit.Before;
import org.junit.Test;
import pl.rawie.timetrack.domain.model.AggregateEntry;
import pl.rawie.timetrack.domain.model.Entry;
import pl.rawie.timetrack.domain.model.SampleEntry;
import pl.rawie.timetrack.domain.service.AggregateService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static pl.rawie.hamcrest.Matchers.containsProperty;
import static pl.rawie.timetrack.domain.model.SampleAggregateEntry.withDurationInMinutesAsList;

public class AggregateServiceImplTest {
    private AggregateService service;

    @Before
    public void setUp() {
        service = new AggregateServiceImpl();
    }

    private void assertAggregate(List<Entry> entries, Matcher matcher) {
        assertThat(service.aggregate(entries), matcher);
    }

    private void assertNormalize(List<AggregateEntry> aggregates, Matcher matcher) {
        assertThat(service.normalize(aggregates), matcher);
    }

    private Matcher containsDelta(int... deltas) {
        List<Duration> result = new ArrayList<Duration>();
        for (int delta : deltas)
            result.add(Duration.standardMinutes(delta));
        return containsProperty("delta", result);
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
        List<Entry> entries = asList(SampleEntry.entry());
        assertAggregate(entries, containsProperty("entries", asList(entries)));
    }

    @Test
    public void aggregate_sameTypeEntries() {
        List<Entry> entries = asList(SampleEntry.entry(), SampleEntry.entry());
        assertAggregate(entries, containsProperty("entries", asList(entries)));
    }

    @Test
    public void aggregate_differentTypeEntries() {
        Entry entry1 = SampleEntry.builder().withSummary("summary-1").build();
        Entry entry2 = SampleEntry.builder().withSummary("summary-2").build();
        List<Entry> entries = asList(entry1, entry2);
        assertAggregate(entries, containsProperty("entries", asList(entry1), asList(entry2)));
    }

    @Test
    public void normalize_singleAggregate() {
        assertNormalize(withDurationInMinutesAsList(10), containsDelta(20));
        assertNormalize(withDurationInMinutesAsList(15), containsDelta(15));
        assertNormalize(withDurationInMinutesAsList(20), containsDelta(10));
        assertNormalize(withDurationInMinutesAsList(30), containsDelta(0));
        assertNormalize(withDurationInMinutesAsList(40), containsDelta(-10));
        assertNormalize(withDurationInMinutesAsList(45), containsDelta(-15));
        assertNormalize(withDurationInMinutesAsList(50), containsDelta(10));
        assertNormalize(withDurationInMinutesAsList(60), containsDelta(0));
    }

    @Test
    public void normalize_multipleAggregate() {
        assertNormalize(withDurationInMinutesAsList(60, 60), containsDelta(0, 0));
        assertNormalize(withDurationInMinutesAsList(60, 50), containsDelta(0, 10));
        assertNormalize(withDurationInMinutesAsList(50, 60), containsDelta(10, 0));
        assertNormalize(withDurationInMinutesAsList(70, 60), containsDelta(-10, 0));
        assertNormalize(withDurationInMinutesAsList(60, 70), containsDelta(0, -10));
        assertNormalize(withDurationInMinutesAsList(70, 70), containsDelta(20, -10));
        assertNormalize(withDurationInMinutesAsList(70, 75), containsDelta(-10, 15));
        assertNormalize(withDurationInMinutesAsList(50, 50), containsDelta(-20, 10));
        assertNormalize(withDurationInMinutesAsList(50, 46), containsDelta(10, -16));
    }
}

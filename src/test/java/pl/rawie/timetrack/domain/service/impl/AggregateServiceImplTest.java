package pl.rawie.timetrack.domain.service.impl;

import org.junit.Before;
import org.junit.Test;
import pl.rawie.timetrack.domain.model.AggregateEntry;
import pl.rawie.timetrack.domain.model.Entry;
import pl.rawie.timetrack.domain.model.SampleEntry;
import pl.rawie.timetrack.domain.service.AggregateService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class AggregateServiceImplTest {
    private AggregateService service;

    @Before
    public void setUp() {
        service = new AggregateServiceImpl();
    }

    private void aggregate(List<Entry> entries, List<AggregateEntry> aggregates) {
        List<AggregateEntry> actual = service.aggregate(entries);
        assertThat(aggregates, is(equalTo(actual)));
    }

    @Test
    public void noEntries() {
        aggregate(Collections.<Entry>emptyList(), Collections.<AggregateEntry>emptyList());
    }

    @Test
    public void singleEntry() {
        Entry entry = SampleEntry.entry();
        List<Entry> entries = Arrays.asList(entry);
        aggregate(entries, Arrays.asList(new AggregateEntry(entries)));
    }
}

package pl.rawie.timetrack.domain.service.impl;

import org.hamcrest.core.IsEqual;
import org.junit.Test;
import pl.rawie.timetrack.domain.model.Entry;
import pl.rawie.timetrack.domain.model.SampleEntry;
import pl.rawie.timetrack.utils.Today;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertThat;

public class OverlapServiceImplTest {
    private OverlapServiceImpl service = new OverlapServiceImpl();

    private void overlaps(Entry entry, List<Entry> entries, boolean expected) {
        boolean actual = service.overlaps(entry, entries);
        assertThat(actual, IsEqual.equalTo(expected));
    }

    @Test
    public void noEntries() {
        overlaps(SampleEntry.entry(), Collections.<Entry>emptyList(), false);
    }

    @Test
    public void unconnected() {
        Entry entry = SampleEntry.entryWithHours(12, 14);
        List<Entry> entries = Arrays.asList(SampleEntry.entryWithHours(8, 10));
        overlaps(entry, entries, false);
    }

    @Test
    public void before() {
        Entry entry = SampleEntry.entryWithHours(12, 14);
        List<Entry> entries = Arrays.asList(SampleEntry.entryWithHours(10, 12));
        overlaps(entry, entries, false);
    }

    @Test
    public void after() {
        Entry entry = SampleEntry.entryWithHours(12, 14);
        List<Entry> entries = Arrays.asList(SampleEntry.entryWithHours(14, 16));
        overlaps(entry, entries, false);
    }

    @Test
    public void overlapped() {
        Entry entry = SampleEntry.entryWithHours(12, 14);
        List<Entry> entries = Arrays.asList(SampleEntry.entryWithHours(13, 15));
        overlaps(entry, entries, true);
    }
}

package pl.rawie.timetrack.domain.model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;

public class DailyEntriesTest {
        private void assertThatAdd(List<Entry> existing, Entry added) {
            DailyEntries dailyEntries = new DailyEntries(new ArrayList<Entry>(existing));
            dailyEntries.add(added);
            List<Entry> expected = new ArrayList<Entry>(existing);
            expected.add(added);
            assertThat(dailyEntries.getEntries(), contains(expected.toArray()));
        }

        @Test
        public void add_noEntries() {
            assertThatAdd(Collections.<Entry>emptyList(), SampleEntry.entry());
        }

        @Test
        public void add_unconnected() {
            assertThatAdd(asList(SampleEntry.entryWithHours(8, 10)), SampleEntry.entryWithHours(12, 14));
        }

        @Test
        public void add_before() {
            assertThatAdd(asList(SampleEntry.entryWithHours(10, 12)), SampleEntry.entryWithHours(12, 14));
        }

        @Test
        public void add_after() {
            assertThatAdd(asList(SampleEntry.entryWithHours(14, 16)), SampleEntry.entryWithHours(12, 14));
        }

        @Test(expected = DomainError.class)
        public void add_overlapped() {
            assertThatAdd(asList(SampleEntry.entryWithHours(13, 15)), SampleEntry.entryWithHours(12, 14));
        }
}

package pl.rawie.timetrack.application.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import pl.rawie.timetrack.domain.model.DailyEntries;
import pl.rawie.timetrack.domain.model.Entry;
import pl.rawie.timetrack.domain.model.EntryRepository;
import pl.rawie.timetrack.domain.model.SampleEntry;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TimeTrackServiceImplTest {
    @Mock
    private EntryRepository entryRepository;
    private TimeTrackServiceImpl service;

    @Before
    public void setUp() {
        service = new TimeTrackServiceImpl();
        service.setEntryRepository(entryRepository);
    }

    @Test(expected = NullPointerException.class)
    public void addEntry_nullEntry() {
        service.addEntry(null);
    }

    @Test
    public void addEntry_validEntry() {
        Entry entry = SampleEntry.entry();
        DailyEntries dailyEntries = mock(DailyEntries.class);
        when(entryRepository.getDailyEntries(entry.getStart())).thenReturn(dailyEntries);

        service.addEntry(entry);

        verify(dailyEntries).add(entry);
        verify(entryRepository).getDailyEntries(entry.getStart());
        verify(entryRepository).storeDailyEntries(dailyEntries);
        verifyNoMoreInteractions(dailyEntries, entryRepository);
    }
}

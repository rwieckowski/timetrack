package pl.rawie.timetrack.application.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.validation.Errors;
import pl.rawie.timetrack.domain.model.DomainError;
import pl.rawie.timetrack.domain.model.Entry;
import pl.rawie.timetrack.domain.model.EntryRepository;
import pl.rawie.timetrack.domain.model.SampleEntry;
import pl.rawie.timetrack.domain.service.OverlapService;
import pl.rawie.timetrack.domain.validator.AddEntryValidator;
import pl.rawie.timetrack.domain.validator.ValidationError;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyCollection;
import static org.mockito.Matchers.anyCollectionOf;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TimeTrackServiceImplTest {
    @Mock
    private AddEntryValidator addEntryValidator;
    @Mock
    private OverlapService overlapService;
    @Mock
    private EntryRepository entryRepository;
    private TimeTrackServiceImpl service;

    @Before
    public void setUp() {
        service = new TimeTrackServiceImpl();
        service.setAddEntryValidator(addEntryValidator);
        when(addEntryValidator.supports(Entry.class)).thenReturn(true);
        service.setOverlapService(overlapService);
        service.setEntryRepository(entryRepository);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addEntry_nullEntry() {
        service.addEntry(null);
    }

    @Test(expected = ValidationError.class)
    public void addEntry_invalidEntry() {
        doThrow(new ValidationError(null)).when(addEntryValidator).validate(any(Entry.class), any(Errors.class));
        service.addEntry(SampleEntry.entry());
    }

    @Test(expected = DomainError.class)
    public void addEntry_overlappedEntry() {
        when(overlapService.overlaps(any(Entry.class), anyCollectionOf(Entry.class))).thenReturn(true);
        service.addEntry(SampleEntry.entry());
    }

    @Test
    public void addEntry_validEntry() {
        Entry entry = SampleEntry.entry();
        service.addEntry(entry);
        verify(entryRepository).store(entry);
    }
}

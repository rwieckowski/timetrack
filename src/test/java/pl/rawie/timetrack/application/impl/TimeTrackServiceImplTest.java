package pl.rawie.timetrack.application.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.validation.Errors;
import pl.rawie.timetrack.domain.model.Entry;
import pl.rawie.timetrack.domain.validator.AddEntryValidator;
import pl.rawie.timetrack.domain.validator.ValidationError;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TimeTrackServiceImplTest {
    @Mock
    private AddEntryValidator validator;
    private TimeTrackServiceImpl service;

    @Before
    public void setUp() {
        service = new TimeTrackServiceImpl();
        service.setAddEntryValidator(validator);
    }

    @Test(expected = ValidationError.class)
    public void addEntry_invalidEntry() {
        when(validator.supports(Entry.class)).thenReturn(true);
        doThrow(new ValidationError(null)).when(validator).validate(any(Entry.class), any(Errors.class));
        service.addEntry(new Entry(null));
    }
}

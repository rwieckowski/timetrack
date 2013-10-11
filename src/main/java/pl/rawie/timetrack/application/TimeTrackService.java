package pl.rawie.timetrack.application;

import pl.rawie.timetrack.domain.model.Entry;
import pl.rawie.timetrack.domain.validator.AddEntryValidator;

public interface TimeTrackService {
    void addEntry(Entry entry);
}

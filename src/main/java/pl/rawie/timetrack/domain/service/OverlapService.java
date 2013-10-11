package pl.rawie.timetrack.domain.service;

import pl.rawie.timetrack.domain.model.Entry;

import java.util.Collection;

public interface OverlapService {
    boolean overlaps(Entry entry, Collection<Entry> entries);
}

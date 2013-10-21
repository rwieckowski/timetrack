package pl.rawie.timetrack.domain.service;

import pl.rawie.timetrack.domain.model.AggregateEntry;
import pl.rawie.timetrack.domain.model.Entry;

import java.util.List;

public interface AggregateService {
    List<AggregateEntry> aggregate(List<Entry> entries);

    List<AggregateEntry> normalize(List<AggregateEntry> aggregates);
}

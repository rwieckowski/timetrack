package pl.rawie.timetrack.domain.service.impl;

import pl.rawie.timetrack.domain.model.AggregateEntry;
import pl.rawie.timetrack.domain.model.Entry;
import pl.rawie.timetrack.domain.service.AggregateService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AggregateServiceImpl implements AggregateService {
    @Override
    public List<AggregateEntry> aggregate(List<Entry> entries) {
        List<AggregateEntry> result = new ArrayList<AggregateEntry>();
        for (Entry entry : entries)
            result.add(new AggregateEntry(Arrays.asList(entry)));
        return result;
    }
}

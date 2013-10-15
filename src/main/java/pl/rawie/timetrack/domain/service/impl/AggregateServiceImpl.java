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
        return new Aggregator().aggregate(entries);
    }
}

class Aggregator {
    private List<AggregateEntry> aggregates = new ArrayList<AggregateEntry>();

    public List<AggregateEntry> aggregate(List<Entry> entries) {
        List<AggregateEntry> result = new ArrayList<AggregateEntry>();
        for (Entry entry : entries)
            result.add(getOrCreateAggregateFor(entry));
        return result;
    }

    private AggregateEntry getOrCreateAggregateFor(Entry entry) {
        for (AggregateEntry aggregate : aggregates)
            if (aggregate.isAggregateFor(entry)) {
                aggregate.addEntry(entry);
                return aggregate;
            }
        return new AggregateEntry(new ArrayList<Entry>(Arrays.asList(entry)));
    }
}
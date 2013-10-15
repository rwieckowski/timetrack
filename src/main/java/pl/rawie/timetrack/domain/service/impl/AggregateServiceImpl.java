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
        for (Entry entry : entries) {
            AggregateEntry aggregate = getOrCreateAggregateFor(entry);
            if (!aggregates.contains(aggregate))
                aggregates.add(aggregate);
        }
        return aggregates;
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
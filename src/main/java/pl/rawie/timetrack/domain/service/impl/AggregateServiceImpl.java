package pl.rawie.timetrack.domain.service.impl;

import com.google.common.collect.Ordering;
import org.joda.time.Duration;
import pl.rawie.timetrack.domain.model.AggregateEntry;
import pl.rawie.timetrack.domain.model.Entry;
import pl.rawie.timetrack.domain.service.AggregateService;
import pl.rawie.timetrack.utils.Normalization;

import java.util.ArrayList;
import java.util.List;

public class AggregateServiceImpl implements AggregateService {
    @Override
    public List<AggregateEntry> aggregate(List<Entry> entries) {
        return new Aggregator().aggregate(entries);
    }

    @Override
    public List<AggregateEntry> normalize(List<AggregateEntry> aggregates) {
        return new Normalizer(aggregates).normalize();
    }
}

class Aggregator {
    private List<List<Entry>> aggregates = new ArrayList<List<Entry>>();

    public List<AggregateEntry> aggregate(List<Entry> entries) {
        for (Entry entry : entries) {
            List<Entry> aggregate = getOrCreateAggregateFor(entry);
            aggregate.add(entry);
            if (!aggregates.contains(aggregate))
                aggregates.add(aggregate);
        }
        return makeAggregates();
    }

    private List<Entry> getOrCreateAggregateFor(Entry entry) {
        for (List<Entry> aggregate : aggregates)
            if (entry.sameAs(aggregate.get(0)))
                return aggregate;
        return new ArrayList<Entry>();
    }

    private List<AggregateEntry> makeAggregates() {
        List<AggregateEntry> result = new ArrayList<AggregateEntry>();
        for (List<Entry> aggregate : aggregates)
            result.add(new AggregateEntry(aggregate));
        return result;
    }
}

class Normalizer {
    private static RelativeErrorOrdering ORDER = new RelativeErrorOrdering();
    private List<AggregateEntry> aggregates;
    private Duration total;

    public Normalizer(List<AggregateEntry> aggregates) {
        this.aggregates = aggregates;
        total = totalDuration();
    }

    public List<AggregateEntry> normalize() {
        if (aggregates.isEmpty())
            return aggregates;

        for (AggregateEntry aggregate : aggregates)
            aggregate.resetDelta();

        while (total.isLongerThan(normalizedDuration()))
            ORDER.min(aggregates).incDelta();

        while (total.isShorterThan(normalizedDuration()))
            ORDER.max(aggregates).decDelta();

        return aggregates;
    }

    private Duration totalDuration() {
        Duration total = Duration.ZERO;
        for (AggregateEntry aggregate : aggregates)
            total = total.plus(aggregate.getDuration());
        return Normalization.normalize(total);
    }

    private Duration normalizedDuration() {
        Duration total = Duration.ZERO;
        for (AggregateEntry aggregate : aggregates)
            total = total.plus(aggregate.getNormalizedDuration());
        return total;
    }
}

class RelativeErrorOrdering extends Ordering<AggregateEntry> {
    @Override
    public int compare(AggregateEntry first, AggregateEntry second) {
        return new Double(first.getRelativeError()).compareTo(second.getRelativeError());
    }
}
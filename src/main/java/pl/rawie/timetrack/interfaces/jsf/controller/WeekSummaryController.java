package pl.rawie.timetrack.interfaces.jsf.controller;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.rawie.timetrack.application.TimeTrackService;
import pl.rawie.timetrack.domain.model.AggregateEntry;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import java.util.ArrayList;
import java.util.List;

@Component
@ManagedBean
public class WeekSummaryController {
    @Autowired
    private TimeTrackSession session;
    @Autowired
    private TimeTrackService service;
    private List<AggregateEntry> summary = new ArrayList<AggregateEntry>();

    public void getWeekSummary() {
        loadSummary();
    }

    public void loadSummary() {
        summary = service.getWeekSummary(new DateTime(session.getSummaryDate()));
    }

    public List<AggregateEntry> getSummary() {
        return summary;
    }
}

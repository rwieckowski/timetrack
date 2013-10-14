package pl.rawie.timetrack.interfaces.jsf.controller;

import org.joda.time.DateTime;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.rawie.timetrack.application.TimeTrackService;
import pl.rawie.timetrack.domain.model.AggregateEntry;
import pl.rawie.timetrack.domain.model.Entry;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
import java.util.ArrayList;
import java.util.List;

@Component
@ManagedBean
@ViewScoped
public class WeekSummaryController {
    @Autowired
    private TimeTrackSession session;
    @Autowired
    private TimeTrackService service;
    private List<AggregateEntry> summary = new ArrayList<AggregateEntry>();

    @PostConstruct
    public void init() {
        loadSummary();
    }

    public void getWeekSummary() {
        loadSummary();
    }

    private void loadSummary() {
        summary = service.getWeekSummary(new DateTime(session.getSummaryDate()));
    }

    public List<AggregateEntry> getSummary() {
        return summary;
    }
}

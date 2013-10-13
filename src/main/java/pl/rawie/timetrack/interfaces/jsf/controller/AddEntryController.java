package pl.rawie.timetrack.interfaces.jsf.controller;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.rawie.timetrack.application.TimeTrackService;
import pl.rawie.timetrack.domain.model.DomainError;
import pl.rawie.timetrack.domain.model.Entry;
import pl.rawie.timetrack.domain.model.EntryBuilder;
import pl.rawie.timetrack.interfaces.jsf.utils.Message;
import pl.rawie.timetrack.utils.Today;

import javax.faces.bean.ManagedBean;
import java.util.Date;

@Component
@ManagedBean
public class AddEntryController {
    private static String SUCCESS = "filter.xhtml";
    private static String ERROR = null;

    @Autowired
    private TimeTrackService service;
    private String summary;
    private Date date = Today.toDate();
    private String start = dateToString(new Date());
    private String end = dateToString(new Date());

    private String dateToString(Date date) {
        DateTime dt = new DateTime(date);
        int hours = dt.getHourOfDay();
        int minutes = dt.getMinuteOfHour();
        return String.format("%02d:%02d", hours, minutes);
    }

    public String addEntry() {
        try {
            Entry entry = EntryBuilder
                    .anEntry()
                    .withSummary(summary)
                    .withStart(makeDate(date, start))
                    .withEnd(makeDate(date, end))
                    .build();
            service.addEntry(entry);
        } catch (DomainError e) {
            Message.error(e);
            return ERROR;
        }
        return SUCCESS;
    }

    private Date makeDate(Date date, String time) {
        DateTime dt = new DateTime(date);
        String[] tokens = time.split(":");
        int hours = (tokens.length >= 1) ? Integer.parseInt(tokens[0]) : 0;
        int minutes = (tokens.length >= 2) ? Integer.parseInt(tokens[1]) : 0;
        System.out.println(hours + ":" + minutes);
        Date d = dt.withTimeAtStartOfDay()
                .withHourOfDay(hours)
                .withMinuteOfHour(minutes)
                .toDate();
        System.out.println(d);
        return d;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}

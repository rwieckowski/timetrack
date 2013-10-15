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
import javax.faces.context.FacesContext;
import java.util.Date;

@Component
@ManagedBean
public class AddEntryController {
    private static String SUCCESS = "filter.xhtml?faces-redirect=true";
    private static String ERROR = null;

    @Autowired
    private TimeTrackService service;
    private String summary;
    private Date date;
    private DateTime start;
    private DateTime end;

    public void populateForm() {
        date = Today.withStartOfTheDay().toDate();
        start = new DateTime().minusHours(1);
        end = new DateTime();
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
            Message.domainError(e);
            FacesContext.getCurrentInstance().validationFailed();
            return ERROR;
        }
        return SUCCESS;
    }

    private DateTime makeDate(Date date, DateTime time) {
        return new DateTime(date)
                .withTimeAtStartOfDay()
                .withHourOfDay(time.getHourOfDay())
                .withMinuteOfHour(time.getMinuteOfHour());
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

    public DateTime getStart() {
        return start;
    }

    public void setStart(DateTime start) {
        this.start = start;
    }

    public DateTime getEnd() {
        return end;
    }

    public void setEnd(DateTime end) {
        this.end = end;
    }
}

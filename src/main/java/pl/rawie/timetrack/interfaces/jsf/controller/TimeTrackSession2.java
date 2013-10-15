package pl.rawie.timetrack.interfaces.jsf.controller;

import org.springframework.stereotype.Component;
import pl.rawie.timetrack.utils.Today;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.Date;

@Component
@ManagedBean
@SessionScoped
public class TimeTrackSession2 implements Serializable {
    private Date filterDate = Today.withStartOfTheDay().toDate();
    private Date summaryDate = Today.withStartOfTheDay().toDate();


    public Date getFilterDate() {
        return filterDate;
    }

    public void setFilterDate(Date filterDate) {
        this.filterDate = filterDate;
    }

    public Date getSummaryDate() {
        return summaryDate;
    }

    public void setSummaryDate(Date summaryDate) {
        this.summaryDate = summaryDate;
    }
}

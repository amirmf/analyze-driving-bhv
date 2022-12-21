package com.taraan.dum.model.hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Date;

@Embeddable
public class DateRange {
    @Column(name = "FromDate")
    private Date from;
    @Column(name = "ToDate")
    private Date to;

    public DateRange() {
    }

    public DateRange(Date from) {
        this.from = from;
    }

    public DateRange(Date from, Date to) {
        this.from = from;
        this.to = to;
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }
}

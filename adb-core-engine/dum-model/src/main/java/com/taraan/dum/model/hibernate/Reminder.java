package com.taraan.dum.model.hibernate;


import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "Reminder")
@NamedQueries(@NamedQuery(name = "reminder-by-userid", query = "select reminder from Reminder reminder where reminder.user.id=:userId"))
public class Reminder implements BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reminder_id_seq")
    @SequenceGenerator(sequenceName = "reminder_id_seq", allocationSize = 1, name = "reminder_id_seq")
    @Column(name = "Id", nullable = false)
    private Long id;
    @Column(name = "remindDayNotification")
    private Integer remindDayNotification;
    @OneToOne(fetch = FetchType.EAGER)
    private User user;
    @Column(name = "insuranceExpirationDate")
    private Date insuranceExpirationDate;
    @Column(name = "insuranceRegisterOn")
    private Date insuranceRegisterOn;
    @Column(name = "lastServiceDate")
    private Date lastServiceDate;
    @Column(name = "nextServiceDate")
    private Date nextServiceDate;
    @Column(name = "serviceAfter")
    private Integer serviceAfter;
    @Column(name = "isActive")
    private boolean active;
    @Version
    private Timestamp version;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRemindDayNotification() {
        return remindDayNotification;
    }

    public void setRemindDayNotification(Integer remindDayNotification) {
        this.remindDayNotification = remindDayNotification;
    }

    public Date getInsuranceExpirationDate() {
        return insuranceExpirationDate;
    }

    public void setInsuranceExpirationDate(Date insuranceExpirationDate) {
        this.insuranceExpirationDate = insuranceExpirationDate;
    }

    public Date getLastServiceDate() {
        return lastServiceDate;
    }

    public void setLastServiceDate(Date lastServiceDate) {
        this.lastServiceDate = lastServiceDate;
    }

    public Integer getServiceAfter() {
        return serviceAfter;
    }

    public void setServiceAfter(Integer serviceAfter) {
        this.serviceAfter = serviceAfter;
    }

    public Timestamp getVersion() {
        return version;
    }

    public void setVersion(Timestamp version) {
        this.version = version;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getInsuranceRegisterOn() {
        return insuranceRegisterOn;
    }

    public void setInsuranceRegisterOn(Date insuranceRegisterOn) {
        this.insuranceRegisterOn = insuranceRegisterOn;
    }

    public Date getNextServiceDate() {
        return nextServiceDate;
    }

    public void setNextServiceDate(Date nextServiceDate) {
        this.nextServiceDate = nextServiceDate;
    }
}

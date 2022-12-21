package com.taraan.dum.model.hibernate;


import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "SosRequest")
public class SosRequest implements BaseEntity, Effectivate {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sosRequest_id_seq")
    @SequenceGenerator(sequenceName = "sosRequest_id_seq", allocationSize = 1, name = "sosRequest_id_seq")
    @Column(name = "Id", nullable = false)
    private Long id;
    @Column(name = "latitude", nullable = false)
    private double latitude;
    @Column(name = "longitude", nullable = false)
    private double longitude;
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;
    @Enumerated(EnumType.STRING)
    @Column(name = "sosRequestState")
    private SosRequestState sosRequestState;
    @Embedded
    private DateRange dateRange;
    @Column(name = "requestText")
    private String text;
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

    public Timestamp getVersion() {
        return version;
    }

    public void setVersion(Timestamp version) {
        this.version = version;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public DateRange getDateRange() {
        return dateRange;
    }

    public void setDateRange(DateRange dateRange) {
        this.dateRange = dateRange;
    }


    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public SosRequestState getSosRequestState() {
        return sosRequestState;
    }

    public void setSosRequestState(SosRequestState sosRequestState) {
        this.sosRequestState = sosRequestState;
    }


}

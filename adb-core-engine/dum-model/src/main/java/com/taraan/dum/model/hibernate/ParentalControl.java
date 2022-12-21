package com.taraan.dum.model.hibernate;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created on 7/6/2018 9:43 AM.
 * User: Reza
 * Project : driving-usage-miner
 */
@Entity
@Table(name = "ParentalControl")
public class ParentalControl implements BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ParentalControl_id_seq")
    @SequenceGenerator(sequenceName = "ParentalControl_id_seq", allocationSize = 1, name = "ParentalControl_id_seq")
    @Column(name = "Id", nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "FromUser", nullable = false)
    private User fromUser;
    @ManyToOne
    @JoinColumn(name = "ToUser", nullable = false)
    private User toUser;
    @Embedded
    private DateRange range;
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

    public User getFromUser() {
        return fromUser;
    }

    public void setFromUser(User fromUser) {
        this.fromUser = fromUser;
    }

    public User getToUser() {
        return toUser;
    }

    public void setToUser(User toUser) {
        this.toUser = toUser;
    }

    public DateRange getRange() {
        return range;
    }

    public void setRange(DateRange range) {
        this.range = range;
    }

    public Timestamp getVersion() {
        return version;
    }

    public void setVersion(Timestamp version) {
        this.version = version;
    }
}

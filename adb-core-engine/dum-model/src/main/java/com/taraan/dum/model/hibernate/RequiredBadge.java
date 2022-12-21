package com.taraan.dum.model.hibernate;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "RequiredBadge")
public class RequiredBadge implements BaseEntity,Effectivate{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Reward_id_seq")
    @SequenceGenerator(sequenceName = "Reward_id_seq", allocationSize = 1, name = "Reward_id_seq")
    @Column(name = "Id", nullable = false)
    private Long id;
    @ManyToOne
    private Badge badge;
    @Embedded
    private DateRange dateRange;
    @Column(name = "requiredCount")
    private Long count;
    @Version
    private Timestamp version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Badge getBadge() {
        return badge;
    }

    public void setBadge(Badge badge) {
        this.badge = badge;
    }

    public DateRange getDateRange() {
        return dateRange;
    }

    public void setDateRange(DateRange dateRange) {
        this.dateRange = dateRange;
    }

    public Timestamp getVersion() {
        return version;
    }

    public void setVersion(Timestamp version) {
        this.version = version;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}

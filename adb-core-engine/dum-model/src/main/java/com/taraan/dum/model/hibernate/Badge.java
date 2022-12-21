package com.taraan.dum.model.hibernate;

import javax.persistence.*;
import java.sql.Timestamp;


@Entity
@Table(name = "Badge")
@NamedQueries(
        {
                @NamedQuery(name = "badge-by-code", query = "select badge from Badge badge where badge.code=:CODE"),
                @NamedQuery(name = "badge-by-notIds", query = "select badge from Badge badge where badge.id not in (:IDS) and badge.dateRange.to is null")
        }
)
public class Badge implements BaseEntity, Effectivate {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "badge_id_seq")
    @SequenceGenerator(sequenceName = "badge_id_seq", allocationSize = 1, name = "badge_id_seq")
    @Column(name = "Id", nullable = false)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "code", unique = true, nullable = false)
    private String code;
    @Column(name = "icon")
    private String icon;
    @Column(name = "description")
    private String description;
    @Embedded
    private DateRange dateRange;
    @Version
    private Timestamp version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getVersion() {
        return version;
    }

    public void setVersion(Timestamp version) {
        this.version = version;
    }

    @Override
    public DateRange getDateRange() {
        return dateRange;
    }

    @Override
    public void setDateRange(DateRange dateRange) {
        this.dateRange = dateRange;
    }
}

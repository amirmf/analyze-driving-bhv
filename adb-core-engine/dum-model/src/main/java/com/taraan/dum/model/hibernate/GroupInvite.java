package com.taraan.dum.model.hibernate;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "GroupInvite")
public class GroupInvite implements BaseEntity, Effectivate {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GroupInvite_id_seq")
    @SequenceGenerator(sequenceName = "GroupInvite_id_seq", allocationSize = 1, name = "GroupInvite_id_seq")
    @Column(name = "Id", nullable = false)
    private Long id;
    @ManyToOne
    private User user;
    @ManyToOne
    private DrivingGroup group;
    @Column
    @Enumerated(EnumType.STRING)
    private GroupInviteState state;

    @Embedded
    private DateRange dateRange;
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

    @Override
    public DateRange getDateRange() {
        return dateRange;
    }

    @Override
    public void setDateRange(DateRange dateRange) {
        this.dateRange = dateRange;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public DrivingGroup getGroup() {
        return group;
    }

    public void setGroup(DrivingGroup group) {
        this.group = group;
    }

    public Timestamp getVersion() {
        return version;
    }

    public void setVersion(Timestamp version) {
        this.version = version;
    }

    public GroupInviteState getState() {
        return state;
    }

    public void setState(GroupInviteState state) {
        this.state = state;
    }
}

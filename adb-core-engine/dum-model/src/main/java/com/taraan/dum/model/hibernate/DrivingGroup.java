package com.taraan.dum.model.hibernate;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "DrivingGroup")
public class DrivingGroup implements BaseEntity, Effectivate {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "drivingGroup_id_seq")
    @SequenceGenerator(sequenceName = "drivingGroup_id_seq", allocationSize = 1, name = "drivingGroup_id_seq")
    @Column(name = "Id", nullable = false)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @ManyToOne
    private User owner;
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "GROUP_USERS",
            joinColumns = {@JoinColumn(name = "group_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    private List<User> users = new ArrayList<>();
    @Embedded
    private DateRange dateRange = new DateRange();
    @Version
    private Timestamp version;
    @Column(name = "description")
    private String description;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
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

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}

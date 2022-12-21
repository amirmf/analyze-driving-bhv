package com.taraan.dum.model.hibernate;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "AccountGroup")
public class AccountGroup implements BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AccountGroup_id_seq")
    @SequenceGenerator(sequenceName = "AccountGroup_id_seq", allocationSize = 1, name = "AccountGroup_id_seq")
    @Column(name = "Id", nullable = false)
    private Long id;
    @Column(name = "code",unique = true)
    private String code;
    @Column(name = "name")
    private String name;
    @ManyToMany
    @JoinTable(
            name = "ACCOUNTROLE_ACCOUNTGROUP",
            joinColumns = {@JoinColumn(name = "group_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")}
    )
    private List<AccountRole> accountRoles;
    @Version
    private Timestamp version;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AccountRole> getAccountRoles() {
        return accountRoles;
    }

    public void setAccountRoles(List<AccountRole> accountRoles) {
        this.accountRoles = accountRoles;
    }

    public Timestamp getVersion() {
        return version;
    }

    public void setVersion(Timestamp version) {
        this.version = version;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}

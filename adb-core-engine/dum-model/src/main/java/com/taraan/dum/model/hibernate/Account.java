package com.taraan.dum.model.hibernate;

import com.taraan.dum.common.TrippleDes;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "Account")
public class Account implements BaseEntity, Effectivate {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Account_id_seq")
    @SequenceGenerator(sequenceName = "Account_id_seq", allocationSize = 1, name = "Account_id_seq")
    @Column(name = "Id", nullable = false)
    private Long id;
    @Column(name = "username", unique = true, updatable = false)
    private String username;
    @Column(name = "password", nullable = false)
    private String encryptedPassword;
    @Transient
    private String password;
    @Column(name = "firstName")
    private String firstName;
    @Column(name = "lastName")
    private String lastName;
    @Column(name = "enabled")
    private boolean enabled;
    @ManyToMany
    @JoinTable(
            name = "ACCOUNT_ACCOUNTROLE",
            joinColumns = {@JoinColumn(name = "account_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")}
    )
    private List<AccountRole> accountRoles;
    @ManyToMany
    @JoinTable(
            name = "ACCOUNT_ACCOUNTGROUP",
            joinColumns = {@JoinColumn(name = "account_id")},
            inverseJoinColumns = {@JoinColumn(name = "group_id")}
    )
    private List<AccountGroup> accountGroups;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public List<AccountRole> getAccountRoles() {
        return accountRoles;
    }

    public void setAccountRoles(List<AccountRole> accountRoles) {
        this.accountRoles = accountRoles;
    }

    public List<AccountGroup> getAccountGroups() {
        return accountGroups;
    }

    public void setAccountGroups(List<AccountGroup> accountGroups) {
        this.accountGroups = accountGroups;
    }


    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    @PostLoad
    public void decryptCardNumber() {
        // decrypts card number during DATABASE READ
        this.password = TrippleDes.getInstance().decrypt(encryptedPassword);
    }

    @PrePersist
    @PreUpdate
    public void encryptCardNumber() {
        // encrypts card number during INSERT/UPDATE
        this.encryptedPassword = TrippleDes.getInstance().encrypt(password);
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}

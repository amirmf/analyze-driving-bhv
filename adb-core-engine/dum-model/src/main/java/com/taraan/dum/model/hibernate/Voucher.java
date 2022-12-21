package com.taraan.dum.model.hibernate;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Voucher" ,uniqueConstraints= {
        @UniqueConstraint(columnNames = {"reward_id", "code"})
})

public class Voucher implements BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Voucher_id_seq")
    @SequenceGenerator(sequenceName = "Voucher_id_seq", allocationSize = 1, name = "Voucher_id_seq")
    @Column(name = "Id", nullable = false)
    private Long id;
    @ManyToOne
    private Reward reward;
    @ManyToOne
    private User user;
    @OneToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name = "Voucher_Id")
    @OrderBy("Id")
    private List<UserBadge> userBadges;
    @Column(name = "VoucherDate")
    private Date date;
    @Column(name = "RegisterDate")
    private Date registerDate;
    @Enumerated(EnumType.STRING)
    @Column(name = "VoucherState")
    private VoucherState voucherState;
    @Version
    private Timestamp version;
    @Column(name = "code")
    private String code;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Reward getReward() {
        return reward;
    }

    public void setReward(Reward reward) {
        this.reward = reward;
    }

    public List<UserBadge> getUserBadges() {
        return userBadges;
    }

    public void setUserBadges(List<UserBadge> userBadges) {
        this.userBadges = userBadges;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public VoucherState getVoucherState() {
        return voucherState;
    }

    public void setVoucherState(VoucherState voucherState) {
        this.voucherState = voucherState;
    }

    public Timestamp getVersion() {
        return version;
    }

    public void setVersion(Timestamp version) {
        this.version = version;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}

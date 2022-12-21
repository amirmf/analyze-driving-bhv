package com.taraan.dum.model.hibernate;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "AccountRole")
public class AccountRole implements BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AccountRole_id_seq")
    @SequenceGenerator(sequenceName = "AccountRole_id_seq", allocationSize = 1, name = "AccountRole_id_seq")
    @Column(name = "Id", nullable = false)
    private Long id;
    @Override
    public Long getId() {
        return id;
    }
    @Override
    public void setId(Long id) {
        this.id = id;
    }
    @Column(name = "code", unique = true)
    private String code;
    @Column(name = "name")
    private String name;
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

    public Timestamp getVersion() {
        return version;
    }

    public void setVersion(Timestamp version) {
        this.version = version;
    }
}

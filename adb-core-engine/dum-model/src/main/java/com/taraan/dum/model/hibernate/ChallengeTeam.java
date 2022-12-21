package com.taraan.dum.model.hibernate;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "ChallengeTeam")
public class ChallengeTeam implements BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ChallengeTeam_id_seq")
    @SequenceGenerator(sequenceName = "ChallengeTeam_id_seq", allocationSize = 1, name = "ChallengeTeam_id_seq")
    @Column(name = "Id", nullable = false)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "rule")
    private String rule;
    @Column(name = "description")
    private String description;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public String getRule() {
        return rule;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public Timestamp getVersion() {
        return version;
    }

    public void setVersion(Timestamp version) {
        this.version = version;
    }
}

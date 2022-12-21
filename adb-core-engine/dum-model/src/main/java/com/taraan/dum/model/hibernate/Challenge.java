package com.taraan.dum.model.hibernate;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "challenge")
@NamedQueries(
        {
                @NamedQuery(name = "challenge-by-analyzeFormula", query = "select challenge from Challenge challenge where challenge.analyzeFormula.id= :ANALYZE_FORMULA_ID")
        }
)
public class Challenge implements BaseEntity, Effectivate {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "challenge_id_seq")
    @SequenceGenerator(sequenceName = "challenge_id_seq", allocationSize = 1, name = "challenge_id_seq")
    @Column(name = "Id", nullable = false)
    private Long id;
    @Column(name = "Icon")
    private String icon;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "image")
    private String image;
    @Column(name = "prizeDescription")
    private String prizeDescription;
    @Column(name = "prizeName")
    private String prizeName;
    @Column(name = "startScore")
    private Long startScore;
    @Column(name = "display")
    private Boolean display;
    @Column(name = "prizeImage")
    private String prizeImage;
    @OneToMany(cascade = {CascadeType.ALL})
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name = "Challenge_Id")
    @OrderBy("Id")
    private List<ChallengeTeam> teams = new ArrayList<>();
    @ManyToOne
    private AnalyzeFormula analyzeFormula;
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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrizeDescription() {
        return prizeDescription;
    }

    public void setPrizeDescription(String prizeDescription) {
        this.prizeDescription = prizeDescription;
    }

    public String getPrizeName() {
        return prizeName;
    }

    public void setPrizeName(String prizeName) {
        this.prizeName = prizeName;
    }

    public String getPrizeImage() {
        return prizeImage;
    }

    public void setPrizeImage(String prizeImage) {
        this.prizeImage = prizeImage;
    }

    public List<ChallengeTeam> getTeams() {
        return teams;
    }

    public void setTeams(List<ChallengeTeam> teams) {
        this.teams = teams;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public AnalyzeFormula getAnalyzeFormula() {
        return analyzeFormula;
    }

    public void setAnalyzeFormula(AnalyzeFormula analyzeFormula) {
        this.analyzeFormula = analyzeFormula;
    }

    public Timestamp getVersion() {
        return version;
    }

    public void setVersion(Timestamp version) {
        this.version = version;
    }

    public Long getStartScore() {
        return startScore;
    }

    public void setStartScore(Long startScore) {
        this.startScore = startScore;
    }

    public Boolean getDisplay() {
        return display;
    }

    public void setDisplay(Boolean display) {
        this.display = display;
    }
}

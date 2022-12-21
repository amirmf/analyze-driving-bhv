package com.taraan.dum.model.hibernate;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "News")
public class News implements BaseEntity, Effectivate {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "news_id_seq")
    @SequenceGenerator(sequenceName = "news_id_seq", allocationSize = 1, name = "news_id_seq")
    @Column(name = "Id", nullable = false)
    private Long id;
    @Column(name = "registerDate")
    private Date registerDate;
    @Column(name = "title")
    private String title;
    @Embedded
    private DateRange dateRange;
    @Column(name = "detailed")
    private String detailed;
    @Column(name = "newsImage")
    private String newsImage;
    @OneToOne(fetch = FetchType.LAZY)
    private NewsImage imageData;
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


    public NewsImage getImageData() {
        return imageData;
    }

    public void setImageData(NewsImage imageData) {
        this.imageData = imageData;
    }
    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetailed() {
        return detailed;
    }

    public void setDetailed(String detailed) {
        this.detailed = detailed;
    }

    @Override
    public DateRange getDateRange() {
        return dateRange;
    }

    @Override
    public void setDateRange(DateRange dateRange) {
        this.dateRange = dateRange;
    }

    public Timestamp getVersion() {
        return version;
    }

    public void setVersion(Timestamp version) {
        this.version = version;
    }

    public String getNewsImage() {
        return newsImage;
    }

    public void setNewsImage(String newsImage) {
        this.newsImage = newsImage;
    }
}

package com.taraan.dum.model.hibernate;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "NewsImage")
public class NewsImage {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "newsImage_id_seq")
    @SequenceGenerator(sequenceName = "newsImage_id_seq", allocationSize = 1, name = "newsImage_id_seq")
    @Column(name = "Id", nullable = false)
    private Long id;

    @Lob
    @Column(name = "ImageData")
    private byte[] data;

    @Version
    private Timestamp version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public Timestamp getVersion() {
        return version;
    }

    public void setVersion(Timestamp version) {
        this.version = version;
    }
}

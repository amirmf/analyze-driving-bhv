package com.taraan.dum.model.hibernate;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "DumMessage")
@NamedQueries({@NamedQuery(name = "update-unread-messages",query = "update Message message set message.read = true where message.read = false and message.user.id=:USER_ID")})
public class Message implements BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "message_id_seq")
    @SequenceGenerator(sequenceName = "message_id_seq", allocationSize = 1, name = "message_id_seq")
    @Column(name = "Id", nullable = false)
    private Long id;
    @Column(name = "MessageText")
    private String text;
    @Column(name = "MessageDate")
    private Date date;
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;
    @Version
    private Timestamp version;
    @Column(name = "readMessage")
    private boolean read;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Timestamp getVersion() {
        return version;
    }

    public void setVersion(Timestamp version) {
        this.version = version;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }
}

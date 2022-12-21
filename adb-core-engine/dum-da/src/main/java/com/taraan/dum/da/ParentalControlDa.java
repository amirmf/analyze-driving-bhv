package com.taraan.dum.da;

import com.taraan.dum.model.hibernate.News;
import com.taraan.dum.model.hibernate.ParentalControl;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ParentalControlDa extends GenericDa<ParentalControl> {
    public ParentalControl getByFromUserAndToUser(Long fromUser, Long toUser) {
        return (ParentalControl) this.getSession().createQuery("select pc from ParentalControl pc where " +
                "pc.fromUser.id = :FROM_USER  and pc.toUser.id = :TO_USER")
                .setParameter("FROM_USER", fromUser)
                .setParameter("TO_USER", toUser).getSingleResult();
    }

    public List<ParentalControl> getChildOfParentalControl(Long userId) {
        return  this.getSession().createQuery("select pc from ParentalControl pc where " +
                "pc.fromUser.id = :USER and pc.range.to is null")
                .setParameter("USER", userId).getResultList();
    }
    public Long getCountChildOfParentalControl(Long userId) {
        return (Long) this.getSession().createQuery("select count(pc.id) from ParentalControl pc where " +
                "pc.fromUser.id = :USER and pc.range.to is null")
                .setParameter("USER", userId).getSingleResult();
    }
    public List<ParentalControl> getParentOfParentalControl(Long userId) {
        return  this.getSession().createQuery("select pc from ParentalControl pc where " +
                "pc.toUser.id = :USER and pc.range.to is null")
                .setParameter("USER", userId)
                .getResultList();
    }
    public Long getCountParentOfParentalControl(Long userId) {
        return (Long) this.getSession().createQuery("select count(pc.id) from ParentalControl pc where " +
                "pc.toUser.id = :USER and pc.range.to is null")
                .setParameter("USER", userId)
                .getSingleResult();
    }
}

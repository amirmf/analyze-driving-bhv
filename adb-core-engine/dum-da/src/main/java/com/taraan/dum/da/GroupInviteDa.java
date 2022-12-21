package com.taraan.dum.da;


import com.taraan.dum.model.hibernate.GroupInvite;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GroupInviteDa extends GenericDa<GroupInvite> {

    public List<GroupInvite> getByUser(Long userId,int from, int size) {
        return this.getSession().createQuery("select groupInvite from GroupInvite groupInvite where groupInvite.user.id=:User_ID and groupInvite.state= 'REQUEST'")
                .setParameter("User_ID", userId).setMaxResults(size).setFirstResult(from).list();
    }
    public Long getCountByUser(Long userId) {
        return (Long) this.getSession().createQuery("select count(groupInvite.id) from GroupInvite groupInvite where groupInvite.user.id=:User_ID and groupInvite.state= 'REQUEST'")
                .setParameter("User_ID", userId).getSingleResult();
    }

    public List<GroupInvite> getByUserAndGroup(Long userId, Long groupId) {
        return this.getSession().createQuery("select groupInvite from GroupInvite groupInvite" +
                " where groupInvite.user.id=:User_ID and groupInvite.group.id= :GROUP_ID")
                .setParameter("User_ID", userId).setParameter("GROUP_ID", groupId).list();

    }
}

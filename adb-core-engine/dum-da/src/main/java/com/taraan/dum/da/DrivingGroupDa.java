package com.taraan.dum.da;

import com.taraan.dum.model.hibernate.DrivingGroup;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DrivingGroupDa extends GenericDa<DrivingGroup> {
    public List<DrivingGroup> getByUser(Long userId, int from, int size) {
        return this.getSession().createQuery("select distinct drivingGroup from DrivingGroup drivingGroup inner  join " +
                "drivingGroup.users user where user.id=:User_ID order by drivingGroup.id desc")
                .setParameter("User_ID", userId).setMaxResults(size).setFirstResult(from).list();
    }

    public Long getCountByUser(Long userId) {
        return (Long) this.getSession().createQuery("select count(distinct drivingGroup.id) from DrivingGroup drivingGroup inner  join " +
                "drivingGroup.users user where user.id=:User_ID order by drivingGroup.id desc")
                .setParameter("User_ID", userId).getSingleResult();
    }

}

package com.taraan.dum.da;


import com.taraan.dum.model.hibernate.Reward;
import com.taraan.dum.model.hibernate.RewardState;
import com.taraan.dum.model.hibernate.RewardType;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RewardDa extends GenericDa<Reward> {
    public List<Reward> get(String type, String name, String rewardState, Long from, Long size) {
        String queryString = "select reward from Reward reward";
        String whereString = "";
        if (type != null && !type.isEmpty())
            whereString += " and reward.type=:TYPE";
        if (name != null && !name.isEmpty())
            whereString += " and reward.name=:NAME";
        if (rewardState != null && !rewardState.isEmpty())
            whereString += " and reward.rewardState =:STATE";
        if (!whereString.isEmpty())
            queryString += " where " + whereString.substring(4);
        final Query query = this.getSession().createQuery(queryString);
        if (type != null && !type.isEmpty())
            query.setParameter("TYPE", RewardType.valueOf(type));
        if (name != null && !name.isEmpty())
            query.setParameter("NAME", name);
        if (rewardState != null && !rewardState.isEmpty())
            query.setParameter("STATE", RewardState.valueOf(rewardState));

        if (from != null)
            query.setFirstResult(from.intValue());
        if (size != null)
            query.setMaxResults(size.intValue());

        return query.list();
    }

    public Long getCount(String type, String name, String rewardState) {
        String queryString = "select count(reward.id) from Reward reward";
        String whereString = "";
        if (type != null && !type.isEmpty())
            whereString += " and reward.type=:TYPE";
        if (name != null && !name.isEmpty())
            whereString += " and reward.name=:NAME";
        if (rewardState != null && !rewardState.isEmpty())
            whereString += " and reward.rewardState=:STATE";
        if (!whereString.isEmpty())
            queryString += " where " + whereString.substring(4);
        final Query query = this.getSession().createQuery(queryString);
        if (type != null && !type.isEmpty())
            query.setParameter("TYPE", RewardType.valueOf(type));
        if (name != null && !name.isEmpty())
            query.setParameter("NAME", name);
        if (rewardState != null && !rewardState.isEmpty())
            query.setParameter("STATE", RewardState.valueOf(rewardState));

        return (Long) query.uniqueResult();
    }

    public List<Reward> getActiveRewards() {
        String query = "select reward from Reward reward where reward.rewardState = 'ACTIVE'";
        return this.getSession().createQuery(query).list();
    }
}

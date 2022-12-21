package com.taraan.dum.da;


import com.taraan.dum.dto.statistic.BadgeUserDto;
import com.taraan.dum.model.hibernate.UserBadge;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserBadgeDa extends GenericDa<UserBadge> {
    public List<BadgeUserDto> getRewardByUser(Long userId, int from, int size) {
        final String userBadgeQuery = "select new com.taraan.dum.dto.statistic.BadgeUserDto(count(userBadge.badge.id),userBadge.badge.id) from UserBadge userBadge " +
                " group by userBadge.user.id, userBadge.badge.id,userBadge.userBadgeState having userBadge.user.id=:USER_ID and (userBadge.userBadgeState is null or userBadge.userBadgeState = 1)";
        return this.getSession().createQuery(userBadgeQuery).setParameter("USER_ID", userId).setFirstResult(from).setMaxResults(size).list();
    }

    public List<BadgeUserDto> getRewardByUser(Long userId) {
        final String userBadgeQuery = "select new com.taraan.dum.dto.statistic.BadgeUserDto(count(userBadge.id),badge.id) from UserBadge userBadge " +
                "inner join userBadge.badge badge group by userBadge.user.id, badge.id,userBadge.id having userBadge.user.id=:USER_ID";
        return this.getSession().createQuery(userBadgeQuery).setParameter("USER_ID", userId).list();
    }

    public Long getCountSavedBadge(Long userId, Long badgeId) {
        final String userBadgeQuery = "select count(userBadge.id) from UserBadge userBadge " +
                "where userBadge.badge.id = :BADGE_ID and userBadge.user.id = :USER_ID and userBadge.userBadgeState = 1";
        return (Long) this.getSession().createQuery(userBadgeQuery).setParameter("BADGE_ID", badgeId)
                .setParameter("USER_ID", userId).uniqueResult();
    }

    public List<UserBadge> getSavedBadge(Long userId, Long badgeId) {
        final String userBadgeQuery = "select userBadge from UserBadge userBadge " +
                "where userBadge.badge.id = :BADGE_ID and userBadge.user.id = :USER_ID and userBadge.userBadgeState = 1";
        return this.getSession().createQuery(userBadgeQuery).setParameter("BADGE_ID", badgeId)
                .setParameter("USER_ID", userId).list();
    }

    public List<UserBadge> getByUserAndBadge(Long user, Long badge) {
        final String userBadgeQuery = "select userBadge from UserBadge userBadge " +
                "where userBadge.badge.id = :BADGE_ID and userBadge.user.id = :USER_ID";
        return this.getSession().createQuery(userBadgeQuery).setParameter("BADGE_ID", badge)
                .setParameter("USER_ID", user).list();
    }
}

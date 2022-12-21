package com.taraan.dum.da;


import com.taraan.dum.dto.challenge.ChallengeMemberDto;
import com.taraan.dum.model.hibernate.Challenge;
import com.taraan.dum.model.hibernate.ChallengeMember;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class ChallengeMemberDa extends GenericDa<ChallengeMember> {
    public ChallengeMember getByUserAndChallenge(Long userId, Long challengeId) {
        return (ChallengeMember) this.getSession().createQuery("select challengeMember from ChallengeMember challengeMember where " +
                "challengeMember.user.id = :USER_ID and challengeMember.challenge.id = :CHALLENGE_ID")
                .setParameter("USER_ID", userId).setParameter("CHALLENGE_ID", challengeId).uniqueResult();
    }

    public List<ChallengeMember> getTopChallengeMember(Long challengeId, Long from, Long size) {
        return this.getSession().createQuery("select challengeMember from ChallengeMember challengeMember where challengeMember.challenge.id = :CHALLENGE_ID and challengeMember.score <> 0" +
                " order by challengeMember.score desc").setMaxResults(size.intValue()).setFirstResult(from.intValue())
                .setParameter("CHALLENGE_ID", challengeId).list();
    }
    public List<ChallengeMember> getAllChallengeMember(Long challengeId, Long from, Long size) {
        return this.getSession().createQuery("select challengeMember from ChallengeMember challengeMember where challengeMember.challenge.id = :CHALLENGE_ID" +
                " order by challengeMember.score desc").setMaxResults(size.intValue()).setFirstResult(from.intValue())
                .setParameter("CHALLENGE_ID", challengeId).list();
    }

    public List<ChallengeMember> getWinnerChallengeMember(Long challengeId, Long from, Long size) {
        return this.getSession().createQuery("select challengeMember from ChallengeMember challengeMember where challengeMember.challenge.id = :CHALLENGE_ID and challengeMember.score > 0" +
                " order by challengeMember.score desc").setMaxResults(size.intValue()).setFirstResult(from.intValue())
                .setParameter("CHALLENGE_ID", challengeId).list();
    }

    public List<ChallengeMember> getTopTeamChallengeMember(Long teamId, Long challengeId) {
        return this.getSession().createQuery("select challengeMember from ChallengeMember challengeMember where " +
                "challengeMember.challenge.id = :CHALLENGE_ID and challengeMember.team.id = :TEAM_ID " +
                " order by challengeMember.score desc")
                .setParameter("CHALLENGE_ID", challengeId)
                .setParameter("TEAM_ID", teamId).list();
    }

    public Long getCountByChallenge(Long challengeId) {
        return (Long) this.getSession().createQuery("select count(challengeMember.id) from ChallengeMember challengeMember where challengeMember.challenge.id = :CHALLENGE_ID")
                .setParameter("CHALLENGE_ID", challengeId).uniqueResult();
    }
}

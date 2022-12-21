package com.taraan.dum.analyzer.assembler;

import com.taraan.dum.analyzer.evaluate.FormulaEvaluator;
import com.taraan.dum.da.*;
import com.taraan.dum.dto.analyze.*;
import com.taraan.dum.model.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Component
@Transactional
public class AnalyzeAssembler {

    @Autowired
    private FormulaEvaluator formulaEvaluator;
    @Autowired
    private TripDa tripDa;
    @Autowired
    private ChallengeDa challengeDa;
    @Autowired
    private ChallengeMemberDa challengeMemberDa;
    @Autowired
    private ChallengeScoreDa challengeScoreDa;
    @Autowired
    private UserDa userDa;
    @Autowired
    private UserBadgeDa userBadgeDa;
    @Autowired
    private DailyTripInfoDa dailyTripInfoDa;
    @Autowired
    private BadgeDa badgeDa;
    @Autowired
    private UserScoreDa userScoreDa;
    @Autowired
    private AnalyzeFormulaDa analyzeFormulaDa;


    public void analyzeDailyData(JmsDailyAnalyzeRequest jmsAnalyzeRequest) {
        final User user = userDa.get(jmsAnalyzeRequest.getUserId());
        final DailyTripInfo dailyTripInfo = dailyTripInfoDa.get(jmsAnalyzeRequest.getDailyTripInfo());
        List<ScoreResult> scoreResults = new ArrayList<>();
        List<BadgeResult> badgeResults = new ArrayList<>();
        final ArrayList<ChallengeResult> challengeResults = new ArrayList<>();
        parseFormulas(null, dailyTripInfo, scoreResults, badgeResults, challengeResults, FormulaEvent.END_OF_DAY);
        Date currentDate = new Date();
        for (BadgeResult badgeResult : badgeResults) {
            try {
                UserBadge userBadge = getTransientUserBadge(user, null, dailyTripInfo, currentDate, badgeResult);
                userBadgeDa.save(userBadge);
            } catch (Exception ignore) {
                ignore.printStackTrace();
            }
        }

        Long score = 0L;
        for (ScoreResult scoreResult : scoreResults) {
            try {
                UserScore userScore = getTransientUserScore(currentDate, null, dailyTripInfo, user, scoreResult);
                userScoreDa.save(userScore);
                score += scoreResult.getScore();
            } catch (Exception ignore) {
                ignore.printStackTrace();

            }
        }
        final Date date = new Date();
        for (ChallengeResult challengeResult : challengeResults) {
            try {
                final List<Challenge> challenges = challengeDa.getByAnalyzeFormula(challengeResult.getAnalyzeFormula().getId());
                for (Challenge challenge : challenges) {
                    if (!isValidChallenge(date, challenge))
                        continue;
                    final ChallengeMember challengeMember = challengeMemberDa.getByUserAndChallenge(jmsAnalyzeRequest.getUserId(), challenge.getId());
                    if (challengeMember == null)
                        continue;
                    if (challengeMember.getScore() == null)
                        challengeMember.setScore(0L);
                    if (challengeResult.getScore().equals(-9999L))
                        challengeMember.setScore(0L);
                    else
                        challengeMember.setScore(challengeMember.getScore() + challengeResult.getScore());
                    challengeMemberDa.update(challengeMember);
                    ChallengeScore challengeScore = new ChallengeScore();
                    challengeScore.setScore(challengeResult.getScore());
                    challengeScore.setChallenge(challenge);
                    challengeScore.setChallengeMember(challengeMember);
                    challengeScore.setDate(date);
                    challengeScore.setUser(user);
                    challengeScore.setDailyTripInfo(dailyTripInfo);
                    challengeScoreDa.save(challengeScore);
                }
            } catch (Exception ignore) {
                ignore.printStackTrace();
            }


        }
    }

    private UserScore getTransientUserScore(Date currentDate, Trip trip, DailyTripInfo dailyTripInfo, User user, ScoreResult scoreResult) {
        UserScore userScore = new UserScore();
        userScore.setAnalyzeFormula(scoreResult.getAnalyzeFormula());
        userScore.setTrip(trip);
        userScore.setDailyTripInfo(dailyTripInfo);
        userScore.setUser(user);
        userScore.setScore(scoreResult.getScore());
        userScore.setDate(currentDate);
        return userScore;
    }

    private UserBadge getTransientUserBadge(User user, Trip trip, DailyTripInfo dailyTripInfo, Date currentDate, BadgeResult badgeResult) {
        UserBadge userBadge = new UserBadge();
        userBadge.setAnalyzeFormula(badgeResult.getAnalyzeFormula());
        userBadge.setDailyTripInfo(dailyTripInfo);
        userBadge.setTrip(trip);
        userBadge.setUser(user);
        userBadge.setUserBadgeState(UserBadgeState.SAVED);
        userBadge.setBadge(badgeDa.get(badgeResult.getCode()));
        userBadge.setDate(currentDate);
        return userBadge;
    }

    public void analyzeData(JmsAnalyzeRequest jmsAnalyzeRequest) {
        Date currentDate = new Date();
        final Trip trip = tripDa.get(jmsAnalyzeRequest.getTripId());
        final User user = userDa.get(jmsAnalyzeRequest.getUserId());
        List<ScoreResult> scoreResults = new ArrayList<>();
        List<BadgeResult> badgeResults = new ArrayList<>();
        final ArrayList<ChallengeResult> challengeResults = new ArrayList<>();
        parseFormulas(trip, null, scoreResults, badgeResults, challengeResults, FormulaEvent.END_TRIP);

        for (BadgeResult badgeResult : badgeResults) {
            try {

                UserBadge userBadge = getTransientUserBadge(user, trip, null, currentDate, badgeResult);
                userBadgeDa.save(userBadge);
            } catch (Exception ignore) {
                ignore.printStackTrace();
            }
        }

        Long score = 0L;
        for (ScoreResult scoreResult : scoreResults) {
            try {
                UserScore userScore = getTransientUserScore(currentDate, trip,null, user, scoreResult);
                userScoreDa.save(userScore);
                score += scoreResult.getScore();
            } catch (Exception ignore) {
                ignore.printStackTrace();

            }
        }
        final Date date = new Date();
        for (ChallengeResult challengeResult : challengeResults) {
            try {
                final List<Challenge> challenges = challengeDa.getByAnalyzeFormula(challengeResult.getAnalyzeFormula().getId());
                for (Challenge challenge : challenges) {
                    if (!isValidChallenge(date, challenge))
                        continue;
                    final ChallengeMember challengeMember = challengeMemberDa.getByUserAndChallenge(jmsAnalyzeRequest.getUserId(), challenge.getId());
                    if (challengeMember == null)
                        continue;
                    if (challengeMember.getScore() == null)
                        challengeMember.setScore(0L);

                    if (challengeMember.getScore() == null)
                        challengeMember.setScore(0L);
                    if (challengeResult.getScore().equals(-9999L))
                        challengeMember.setScore(0L);
                    else
                        challengeMember.setScore(challengeMember.getScore() + challengeResult.getScore());
                    challengeMemberDa.update(challengeMember);
                    ChallengeScore challengeScore = new ChallengeScore();
                    challengeScore.setScore(challengeResult.getScore());
                    challengeScore.setChallenge(challenge);
                    challengeScore.setChallengeMember(challengeMember);
                    challengeScore.setDate(date);
                    challengeScore.setUser(user);
                    challengeScore.setTrip(trip);
                    challengeScoreDa.save(challengeScore);
                }
            } catch (Exception ignore) {
                ignore.printStackTrace();
            }


        }
        trip.setScore(score);
        tripDa.update(trip);
    }


    private boolean isValidChallenge(Date date, Challenge challenge) {
        if (challenge.getDateRange().getTo() != null)
            if (challenge.getDateRange().getTo().getTime() < date.getTime())
                return false;
        return true;
    }

    private void parseFormulas(Trip trip, DailyTripInfo dailyTripInfo, List<ScoreResult> scoreResults, List<BadgeResult> badgeResults, List<ChallengeResult> challengeResults, FormulaEvent formulaEvent) {
        List<AnalyzeFormula> analyzeFormulas = analyzeFormulaDa.getByFormulaEvent(formulaEvent);
        analyzeFormulas.sort(Comparators.ANALYZE_FORMULA_COMPARATOR);
        for (AnalyzeFormula analyzeFormula : analyzeFormulas) {
            try {
                AnalyzeTripResult analyzeTripResult = this.parseFormula(analyzeFormula, trip, dailyTripInfo);
                if (analyzeTripResult.getType().equals(ScoreResult.class.getSimpleName())) {
                    final ScoreResult scoreResult = analyzeTripResult.getScoreResult();
                    scoreResult.setAnalyzeFormula(analyzeFormula);
                    scoreResults.add(scoreResult);
                } else if (analyzeTripResult.getType().equals(BadgeResult.class.getSimpleName())) {
                    final BadgeResult badgeResult = analyzeTripResult.getBadgeResult();
                    badgeResult.setAnalyzeFormula(analyzeFormula);
                    badgeResults.add(badgeResult);
                } else if (analyzeTripResult.getType().equals(ChallengeResult.class.getSimpleName())) {
                    final ChallengeResult challengeResult = analyzeTripResult.getChallengeResult();
                    challengeResult.setAnalyzeFormula(analyzeFormula);
                    challengeResults.add(challengeResult);
                }
            } catch (Exception ignore) {
                ignore.printStackTrace();
            }
        }
    }

    private AnalyzeTripResult parseFormula(AnalyzeFormula analyzeFormula, Trip trip, DailyTripInfo dailyTripInfo) {
        try {
            AnalyzeTripRequest request = new AnalyzeTripRequest();
            request.setFormula(analyzeFormula.getFormula());
            if (trip != null) {
                request.setUserId(trip.getUser().getId());
                request.setTripId(trip.getId());
            }
            if (dailyTripInfo != null) {
                request.setUserId(dailyTripInfo.getUser().getId());
                request.setDailyTripInfoId(dailyTripInfo.getId());
            }
            return parseFormula(request);
        } catch (Exception e) {
            e.printStackTrace();
            return new AnalyzeTripResult("null");
        }
    }


    public AnalyzeTripResult parseFormula(AnalyzeTripRequest request) {
        AnalyzeTripResult analyzeTripResult = new AnalyzeTripResult();
        final Object o = formulaEvaluator.evaluateFormula(request, new HashMap());
        if (o == null) {
            analyzeTripResult.setType("null");
            return analyzeTripResult;
        }
        if (o instanceof BadgeResult) {
            analyzeTripResult.setBadgeResult((BadgeResult) o);
            analyzeTripResult.setType(BadgeResult.class.getSimpleName());
        } else if (o instanceof ScoreResult) {
            analyzeTripResult.setScoreResult((ScoreResult) o);
            analyzeTripResult.setType(ScoreResult.class.getSimpleName());
        } else if (o instanceof ChallengeResult) {
            analyzeTripResult.setChallengeResult((ChallengeResult) o);
            analyzeTripResult.setType(ChallengeResult.class.getSimpleName());
        } else {
            throw new IllegalArgumentException();
        }
        return analyzeTripResult;
    }
}

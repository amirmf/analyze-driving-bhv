package com.taraan.dum.logic;

import com.taraan.dum.common.DateUtils;
import com.taraan.dum.common.FileTypesUtils;
import com.taraan.dum.da.*;
import com.taraan.dum.dto.challenge.*;
import com.taraan.dum.exceptions.DumException;
import com.taraan.dum.exceptions.EntityDoesNotExistException;
import com.taraan.dum.exceptions.UserAlreadyJoinException;
import com.taraan.dum.model.hibernate.*;
import jersey.repackaged.com.google.common.collect.ImmutableMap;
import org.apache.commons.io.FileUtils;
import org.apache.tika.io.IOUtils;
import org.mvel2.ParserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import javax.annotation.PostConstruct;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class ChallengeLogic {

    @Autowired
    private ChallengeDa challengeDa;
    @Autowired
    private AnalyzeFormulaDa analyzeFormulaDa;
    @Autowired
    private ChallengeMemberDa challengeMemberDa;
    @Autowired
    private UserBadgeDa userBadgeDa;
    @Autowired
    private BadgeDa badgeDa;
    @Autowired
    private UserDa userDa;
    @Autowired
    private RuleEvaluator ruleEvaluator;
    @Value("${challengePath}")
    private String challengePath;
    @Value("${iconPath}")
    private String iconPath;
    @Value("${challengeUrl}")
    private String challengeUrl;
    @Value("${prizePath}")
    private String prizePath;
    @Value("${prizeUrl}")
    private String prizeUrl;
    @Value("${iconUrl}")
    private String iconUrl;

    public Long createChallenge(CreateChallengeRequest createChallengeRequest) throws IOException {
        Challenge challenge = new Challenge();
        challenge.setName(createChallengeRequest.getName());
        challenge.setDescription(createChallengeRequest.getDescription());
        challenge.setPrizeName(createChallengeRequest.getName());
        challenge.setPrizeDescription(createChallengeRequest.getPrizeDescription());
        challenge.setStartScore(createChallengeRequest.getStartScore());
        challenge.setDateRange(new DateRange(DateUtils.getDate(createChallengeRequest.getStartDate())));
        if (createChallengeRequest.getEndDate() != null && !createChallengeRequest.getEndDate().isEmpty())
            challenge.getDateRange().setTo(DateUtils.getDate(createChallengeRequest.getEndDate()));
        challenge.setDisplay(createChallengeRequest.isDisplay());
        if (createChallengeRequest.getAnalyzeFormula() != null)
            challenge.setAnalyzeFormula(analyzeFormulaDa.get(createChallengeRequest.getAnalyzeFormula()));
        final ChallengeTeam e = new ChallengeTeam();
        e.setName("team 1");
        e.setRule("true");
        e.setDescription("");
        challenge.getTeams().add(e);
        challenge = challengeDa.save(challenge);
        return challenge.getId();
    }

    public Long updateChallenge(CreateChallengeRequest createChallengeRequest) throws IOException {
        Challenge challenge = challengeDa.get(createChallengeRequest.getId());
        challenge.setName(createChallengeRequest.getName());
        challenge.setDescription(createChallengeRequest.getDescription());
        challenge.setPrizeName(createChallengeRequest.getName());
        challenge.setPrizeDescription(createChallengeRequest.getPrizeDescription());
        challenge.setStartScore(createChallengeRequest.getStartScore());
        challenge.setDateRange(new DateRange(DateUtils.getDate(createChallengeRequest.getStartDate())));
        if (createChallengeRequest.getEndDate() != null && !createChallengeRequest.getEndDate().isEmpty())
            challenge.getDateRange().setTo(DateUtils.getDate(createChallengeRequest.getEndDate()));
        else
            challenge.getDateRange().setTo(null);

        challenge.setDisplay(createChallengeRequest.isDisplay());
        if (createChallengeRequest.getAnalyzeFormula() != null)
            challenge.setAnalyzeFormula(analyzeFormulaDa.get(createChallengeRequest.getAnalyzeFormula()));
        else
            challenge.setAnalyzeFormula(null);
        challenge = challengeDa.save(challenge);
        return challenge.getId();
    }

    public void uploadIcon(InputStream inputStream, Long id) throws IOException {
        Challenge challenge = challengeDa.get(id);
        final byte[] data = IOUtils.toByteArray(inputStream);
        final String fileType = FileTypesUtils.checkType(data);
        final String fileName = System.currentTimeMillis() + "." + fileType;
        final File file = new File(iconPath + fileName);
        FileUtils.writeByteArrayToFile(file, data);
        challenge.setIcon(fileName);
        challengeDa.update(challenge);

    }

    public void uploadImage(InputStream inputStream, Long id) throws IOException {
        Challenge challenge = challengeDa.get(id);
        final byte[] data = IOUtils.toByteArray(inputStream);
        final String fileType = FileTypesUtils.checkType(data);
        final String fileName = System.currentTimeMillis() + "." + fileType;
        final File file = new File(challengePath + fileName);
        FileUtils.writeByteArrayToFile(file, data);
        challenge.setImage(fileName);
        challengeDa.update(challenge);

    }

    public void uploadPrizeImage(InputStream inputStream, Long id) throws IOException {
        Challenge challenge = challengeDa.get(id);
        final byte[] data = IOUtils.toByteArray(inputStream);
        final String fileType = FileTypesUtils.checkType(data);
        final String fileName = System.currentTimeMillis() + "." + fileType;
        final File file = new File(prizePath + fileName);
        FileUtils.writeByteArrayToFile(file, data);
        challenge.setPrizeImage(fileName);
        challengeDa.update(challenge);
    }


    public void joinChallenge(Long challengeId, Long userId) {
        Challenge challenge = challengeDa.get(challengeId);
        if (challenge == null)
            throw new EntityDoesNotExistException("challenge");
        if (!checkDateRange(challenge.getDateRange(), new Date()))
            throw new EntityDoesNotExistException("challenge");
        User user = userDa.get(userId);
        if (user == null)
            throw new EntityDoesNotExistException("user");
        ChallengeMember challengeMember = challengeMemberDa.getByUserAndChallenge(userId, challengeId);
        if (challengeMember != null)
            throw new UserAlreadyJoinException();

        challengeMember = new ChallengeMember();
        for (ChallengeTeam challengeTeam : challenge.getTeams()) {
            if (checkRule(challengeTeam.getRule(), user)) {
                challengeMember.setTeam(challengeTeam);
                break;
            }
        }
        challengeMember.setUser(user);
        challengeMember.setChallenge(challenge);
        challengeMember.setJoinDate(new Date());
        challengeMember.setScore(challenge.getStartScore());
        challengeMemberDa.save(challengeMember);
        Badge badge = badgeDa.get("CHALLENGE_BADGE");
        if (badge != null) {
            UserBadge userBadge = new UserBadge();
            userBadge.setDate(new Date());
            userBadge.setUser(user);
            userBadge.setUserBadgeState(UserBadgeState.SAVED);
            userBadge.setBadge(badge);
            userBadgeDa.save(userBadge);
        }
    }

    public static boolean checkDateRange(DateRange dateRange, Date date) {
        if (dateRange == null) return true;
        if (date == null) return true;

        return ((dateRange.getFrom() == null || !after(dateRange.getFrom(), date)) &&
                (dateRange.getTo() == null || !before(dateRange.getTo(), date)));
    }

    public static boolean before(Date date1, Date date2) {
        return date1.getTime() < date2.getTime();
    }

    public static boolean after(Date date1, Date date2) {
        return date1.getTime() > date2.getTime();
    }

    private boolean checkRule(String rule, User user) {
        final ImmutableMap<String, Object> parameters = ImmutableMap.<String, Object>builder().
                put("user", user).
                build();
        return ruleEvaluator.evaluateFormula(rule, parameters);
    }

    public AdminChallengeDto getChallenge(Long id) {
        final Challenge challenge = challengeDa.get(id);
        if (challenge == null)
            throw new EntityDoesNotExistException("challenge");
        AdminChallengeDto challengeDto = new AdminChallengeDto();
        challengeDto.setId(challenge.getId());
        challengeDto.setIcon(iconUrl + challenge.getIcon());
        challengeDto.setName(challenge.getName());
        challengeDto.setDescription(challenge.getDescription());
        challengeDto.setImage(challengeUrl + challenge.getImage());
        challengeDto.setPrizeName(challenge.getPrizeName());
        challengeDto.setPrizeImage(prizeUrl + challenge.getPrizeImage());
        challengeDto.setPrizeDescription(challenge.getPrizeDescription());
        challengeDto.setStartScore(challenge.getStartScore());
        challengeDto.setDisplay(challenge.getDisplay());
        if (challenge.getAnalyzeFormula() != null)
            challengeDto.setAnalyzeFormula(challenge.getAnalyzeFormula().getId());
        if (challenge.getDateRange().getFrom() != null) {
            challengeDto.setStartDate(DateUtils.getDate(challenge.getDateRange().getFrom()));
        }
        if (challenge.getDateRange().getTo() != null) {
            challengeDto.setEndDate(DateUtils.getDate(challenge.getDateRange().getTo()));
        }
        List<ChallengeMemberDto> members = new ArrayList<>();
        return challengeDto;
    }

    public ChallengeDto getChallenge(Long userId, Long id) {
        final Challenge challenge = challengeDa.get(id);
        if (challenge == null)
            throw new EntityDoesNotExistException("challenge");
        ChallengeDto challengeDto = new ChallengeDto();
        challengeDto.setIcon(iconUrl + challenge.getIcon());

        challengeDto.setActive(false);
        final Date date = new Date();
        challengeDto.setWinners(Collections.emptyList());
        if (challenge.getDateRange().getTo() == null)
            challengeDto.setActive(true);
        else {
            if (challenge.getDateRange().getTo().getTime() < date.getTime()) {
                challengeDto.setActive(false);
                challengeDto.setWinners(getChallengeMemberDto(challengeMemberDa.getWinnerChallengeMember(challenge.getId(), 0L, 5L)));
            } else if (challenge.getDateRange().getFrom().getTime() > date.getTime()) {
                challengeDto.setActive(false);
            } else
                challengeDto.setActive(true);
        }
        final ChallengeMember challengeMember = challengeMemberDa.getByUserAndChallenge(userId, challenge.getId());
        challengeDto.setImage(challengeUrl + challenge.getImage());
        challengeDto.setName(challenge.getName());
        challengeDto.setDescription(challenge.getDescription());
        challengeDto.setTeamCount(challenge.getTeams().size());
        challengeDto.setParticipantCount(challengeMemberDa.getCountByChallenge(challenge.getId()));
        challengeDto.setStartDate(DateUtils.getDate(challenge.getDateRange().getFrom()));
        if (challenge.getDateRange().getTo() != null) {
            challengeDto.setEndDate(DateUtils.getDate(challenge.getDateRange().getTo()));
            final int realDayDateDiffrent = DateUtils.getRealDayDateDiffrent(challenge.getDateRange().getTo(), date);
            if (realDayDateDiffrent < 0)
                challengeDto.setDayLeft(0);
            else
                challengeDto.setDayLeft(realDayDateDiffrent);
        }
        challengeDto.setChallengeTopMembers(getChallengeMemberDto(challengeMemberDa.getAllChallengeMember(challenge.getId(), 0L, 1000L)));//todo must be pagination
        challengeDto.setAllChallengeMembers(getChallengeMemberDto(challengeMemberDa.getAllChallengeMember(challenge.getId(), 0L, 1000L)));//todo must be pagination
        challengeDto.setScoreChallengeMembers(getChallengeMemberDto(challengeMemberDa.getTopChallengeMember(challenge.getId(), 0L, 1000L)));//todo must be pagination
        if (challengeMember != null) {
            challengeDto.setActive(false);
            challengeDto.setTeamTopMembers(getChallengeMemberDto(challengeMemberDa.getTopTeamChallengeMember(challengeMember.getTeam().getId(), challenge.getId())));
        } else
            challengeDto.setTeamTopMembers(getChallengeMemberDto(challengeMemberDa.getTopTeamChallengeMember(challenge.getTeams().get(0).getId(), challenge.getId())));

        challengeDto.setPrize(challenge.getPrizeName());
        challengeDto.setPrizeImage(prizeUrl + challenge.getPrizeImage());
        challengeDto.setPrizeDescription(challenge.getPrizeDescription());
        return challengeDto;
    }

    private List<ChallengeMemberDto> getChallengeMemberDto(List<ChallengeMember> challengeMembers) {
        List<ChallengeMemberDto> challengeMemberDtos = new ArrayList<>();
        for (ChallengeMember challengeMember : challengeMembers) {
            final ChallengeMemberDto e = new ChallengeMemberDto();
            String firstName = "";
            String lastName = "";
            if (challengeMember.getUser().getFirstName() != null)
                firstName = challengeMember.getUser().getFirstName();
            if (challengeMember.getUser().getLastName() != null)
                lastName = challengeMember.getUser().getLastName();
            if ((firstName + lastName).isEmpty())
                e.setName("کاربر " + challengeMember.getUser().getId());
            else
                e.setName(firstName + " " + lastName);
            if (challengeMember.getScore() == null)
                e.setScore(0);
            else
                e.setScore(challengeMember.getScore());
            challengeMemberDtos.add(e);
        }
        return challengeMemberDtos;
    }

    public ChallengePage getChallenges(Long userId) {
        List<Challenge> challenges = challengeDa.getDisplayChallenge(null, null);
        ChallengePage challengePage = new ChallengePage();
        for (Challenge challenge : challenges) {
            ChallengeItem e = getChallengeItem(challenge);
            challengePage.getChallengeItems().add(e);
        }
        return challengePage;
    }

    public List<ChallengeItem> getDisplayChallenge(Long from, Long size) {
        List<Challenge> challenges = challengeDa.getDisplayChallenge(from, size);
        List<ChallengeItem> challengeItems = new ArrayList<>();
        for (Challenge challenge : challenges) {
            challengeItems.add(getChallengeItem(challenge));
        }
        return challengeItems;


    }

    private ChallengeItem getChallengeItem(Challenge challenge) {
        ChallengeItem e = new ChallengeItem();
        e.setId(challenge.getId());
        e.setIcon(iconUrl + challenge.getIcon());
        e.setName(challenge.getName());
        e.setDescription(challenge.getDescription());
        e.setTeamCount(challenge.getTeams().size());
        e.setStartDate(DateUtils.getDate(challenge.getDateRange().getFrom()));
        if (challenge.getDateRange().getTo() != null) {
            e.setEndDate(DateUtils.getDate(challenge.getDateRange().getTo()));
            final int realDayDateDiffrent = DateUtils.getRealDayDateDiffrent(challenge.getDateRange().getTo(), new Date());
            if (realDayDateDiffrent < 0)
                e.setDayLeft(0);
            else
                e.setDayLeft(realDayDateDiffrent);
        }
        e.setParticipantCount(challengeMemberDa.getCountByChallenge(challenge.getId()));
        return e;
    }

    public void removeTeamFromChallenge(RemoveTeamFromChallengeRequest request) {
        final Challenge challenge = challengeDa.get(request.getId());
        if (challenge == null)
            throw new EntityDoesNotExistException("challenge");
        final List<ChallengeTeam> newTeams = challenge.getTeams().stream().filter(team -> !team.getId().equals(request.getChallengeTeamId())).collect(Collectors.toList());
        challenge.setTeams(newTeams);
        challengeDa.update(challenge);
    }

    public void addTeamToChallenge(AddTeamToChallengeRequest request) {
        final Challenge challenge = challengeDa.get(request.getId());
        if (challenge == null)
            throw new EntityDoesNotExistException("challenge");
        final ChallengeTeam e = new ChallengeTeam();
        e.setName(request.getChallengeTeam().getName());
        e.setDescription(request.getChallengeTeam().getDescription());
        e.setRule(request.getChallengeTeam().getRule());
        challenge.getTeams().add(e);
        challengeDa.update(challenge);
    }

    public AdminChallengePage getChallenges(String name, String fromStartDate,
                                            String toStartDate,
                                            String fromEndDate, String toEndDate, Long from, Long size) {
        Date fStartDate = null;
        Date tStartDate = null;
        Date fEndDate = null;
        Date tEndDate = null;
        if (fromStartDate != null && !fromStartDate.isEmpty())
            fStartDate = DateUtils.getDate(fromStartDate);
        if (toStartDate != null && !toStartDate.isEmpty())
            tStartDate = DateUtils.getDate(toStartDate);
        if (fromEndDate != null && !fromEndDate.isEmpty())
            fEndDate = DateUtils.getDate(fromEndDate);
        if (toEndDate != null && !toEndDate.isEmpty())
            tEndDate = DateUtils.getDate(toEndDate);
        final AdminChallengePage adminChallengePage = new AdminChallengePage();
        List<Challenge> challenges = challengeDa.get(name, fStartDate, tStartDate, fEndDate, tEndDate, from, size);
        for (Challenge challenge : challenges) {
            final AdminChallengeItem e = new AdminChallengeItem();
            e.setId(challenge.getId());
            e.setDescription(challenge.getDescription());
            e.setName(challenge.getName());
            if (challenge.getDateRange().getFrom() != null)
                e.setStartDate(DateUtils.getDate(challenge.getDateRange().getFrom()));
            if (challenge.getDateRange().getTo() != null)
                e.setEndDate(DateUtils.getDate(challenge.getDateRange().getTo()));
            adminChallengePage.getChallengeItems().add(e);
        }
        adminChallengePage.setCount(challengeDa.getCount(name, fStartDate, tStartDate, fEndDate, tEndDate));
        return adminChallengePage;
    }

}

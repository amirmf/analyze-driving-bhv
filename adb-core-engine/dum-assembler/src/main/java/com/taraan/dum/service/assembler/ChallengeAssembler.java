package com.taraan.dum.service.assembler;

import com.taraan.dum.dto.challenge.*;
import com.taraan.dum.logic.ChallengeLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

@Component
@Transactional
public class ChallengeAssembler {
    @Autowired
    private ChallengeLogic challengeLogic;

    public ChallengeDto getChallenge(Long userId, Long id) {
        return challengeLogic.getChallenge(userId, id);
    }

    public AdminChallengeDto getChallenge(Long id) {
        return challengeLogic.getChallenge(id);
    }

    public ChallengePage getChallenges(Long userId) {
        return challengeLogic.getChallenges(userId);
    }

    public Long createChallenge(CreateChallengeRequest createChallengeRequest) throws IOException {
        return challengeLogic.createChallenge(createChallengeRequest);
    }

    public Long updateChallenge(CreateChallengeRequest createChallengeRequest) throws IOException {
        return challengeLogic.updateChallenge(createChallengeRequest);
    }

    public void uploadIcon(InputStream inputStream, Long id) throws IOException {
        challengeLogic.uploadIcon(inputStream, id);
    }

    public void joinChallenge(Long challengeId, Long userId) {
        challengeLogic.joinChallenge(challengeId, userId);
    }


    private ChallengeDto getMockChallengeDto(Long id) {
        if (id != 1)
            return new ChallengeDto();
        ChallengeDto challengeDto = new ChallengeDto();
        challengeDto.setIcon("http://31.184.132.112:8080/icon/GoldAward.png");
        challengeDto.setActive(true);
        challengeDto.setImage("http://31.184.132.112:8080/challenge/tehran.jpg");
        challengeDto.setName("چالش داخل شهر");
        challengeDto.setDescription("۲۰۰۰ کیلومتر داخل شهر");
        challengeDto.setTeamCount(1);
        challengeDto.setParticipantCount(100L);
        challengeDto.setDayLeft(12);
        final ArrayList<ChallengeMemberDto> challengeTopMembers = new ArrayList<>();
        challengeTopMembers.add(getChallengeMemberDto("صفر", 12.5d));
        challengeTopMembers.add(getChallengeMemberDto("مصیب", 10.5d));
        challengeTopMembers.add(getChallengeMemberDto("مجتبی", 10d));
        challengeTopMembers.add(getChallengeMemberDto("امیر", 8.5d));
        challengeDto.setChallengeTopMembers(challengeTopMembers);
        challengeDto.setTeamTopMembers(challengeTopMembers);
        challengeDto.setWinners(Collections.emptyList());
        challengeDto.setPrize("دوچرخه");
        challengeDto.setPrizeDescription("دوچرخه کوهستان");
        challengeDto.setPrizeImage("http://31.184.132.112:8080/prize/bike.jpg");
        return challengeDto;
    }


    private ChallengePage getMockChallengePage() {
        ChallengePage challengePage = new ChallengePage();
        final ChallengeItem e = new ChallengeItem();
        e.setId(1L);
        e.setDayLeft(21);
        e.setName("چالش داخل شهر");
        e.setDescription("۲۰۰۰ کیلومتر داخل شهر");
        e.setDayLeft(12);
        e.setIcon("http://31.184.132.112:8080/icon/GoldAward.png");
        e.setParticipantCount(100L);
        e.setTeamCount(1);
        challengePage.getChallengeItems().add(e);
        return challengePage;
    }

    private ChallengeMemberDto getChallengeMemberDto(String name, double score) {
        final ChallengeMemberDto e = new ChallengeMemberDto();
        e.setName(name);
        e.setScore(score);
        return e;
    }

    public void removeTeamFromChallenge(RemoveTeamFromChallengeRequest request) {
        challengeLogic.removeTeamFromChallenge(request);
    }

    public void addTeamToChallenge(AddTeamToChallengeRequest request) {
        challengeLogic.addTeamToChallenge(request);
    }

//    public AdminChallengePage getChallenges(String name, boolean active, Long from, Long size) {
//        return challengeLogic.getChallenges(name, active, from, size);
//    }

    public void uploadImage(InputStream uploadedInputStream, Long id) throws IOException {
        challengeLogic.uploadImage(uploadedInputStream, id);
    }

    public void uploadPrizeImage(InputStream uploadedInputStream, Long id) throws IOException {
        challengeLogic.uploadPrizeImage(uploadedInputStream, id);
    }

    public AdminChallengePage getChallenges(String name, String fromStartDate,
                                            String toStartDate,
                                            String fromEndDate, String toEndDate, Long from, Long size) {
        return challengeLogic.getChallenges(name, fromStartDate, toStartDate, fromEndDate, toEndDate, from, size);
    }
}

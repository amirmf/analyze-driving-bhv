package com.taraan.dum.service.assembler;

import com.taraan.dum.dto.reward.*;
import com.taraan.dum.dto.reward.RewardDto;
import com.taraan.dum.dto.trip.TripPage;
import com.taraan.dum.logic.RewardLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;

@Component
@Transactional
public class RewardAssembler {
    @Autowired
    private RewardLogic rewardLogic;

    public void createReward(CreateRewardRequest request) throws IOException {
        rewardLogic.createReward(request);
    }

    public void uploadImage(InputStream inputStream, Long id) throws IOException {
        rewardLogic.uploadImage(inputStream, id);
    }

    public void updateReward(UpdateRewardRequest request) throws IOException {
        rewardLogic.updateReward(request);
    }

    public void addBadgeRequired(AddBadgeRequiredRequest request) throws IOException {
        rewardLogic.addBadgeRequired(request);
    }

    public void removeBadgeRequired(RemoveBadgeRequiredRequest request) throws IOException {
        rewardLogic.removeBadgeRequired(request);
    }

    public void deleteReward(Long id) {
        rewardLogic.deleteReward(id);
    }

    public RewardDto getReward(Long id) {
        return rewardLogic.getReward(id);
    }

    public RewardPage getRewards(Long from, Long size) {
        return rewardLogic.getRewards(from, size);
    }

    public RewardPage getRewards(String type, String name, String rewardState, Long from, Long size) {
        return rewardLogic.getRewards(type, name, rewardState, from, size);
    }

    public AdminUserRewardPage getRewards(String phoneNumber, String fromDate, String toDate, String userRewardType, Long from, Long size) {
        return rewardLogic.getRewards(phoneNumber, fromDate, toDate, userRewardType, from, size);
    }

    public UserRewardPage getRewards(Long userId) {
        return rewardLogic.getRewards(userId);
    }

    public void enterRewards(Long userId, Long rewardId) {
        rewardLogic.enterRewards(userId, rewardId);
    }

}

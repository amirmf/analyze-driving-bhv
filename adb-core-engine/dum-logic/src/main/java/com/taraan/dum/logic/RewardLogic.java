package com.taraan.dum.logic;

import com.taraan.dum.common.FileTypesUtils;
import com.taraan.dum.da.*;
import com.taraan.dum.dto.reward.*;
import com.taraan.dum.exceptions.reward.InsufficientBadgeException;
import com.taraan.dum.model.hibernate.*;
import org.apache.commons.io.FileUtils;
import org.apache.tika.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import javax.naming.InsufficientResourcesException;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
public class RewardLogic {
    @Autowired
    private RewardDa rewardDa;
    @Autowired
    private BadgeDa badgeDa;
    @Autowired
    private UserBadgeDa userBadgeDa;
    @Autowired
    private UserRewardDa userRewardDa;
    @Autowired
    private MessageDa messageDa;
    @Autowired
    private UserDa userDa;
    @Value("${rewardUrl}")
    private String rewardUrl;
    @Value("${rewardPath}")
    private String rewardPath;
    @Value("${iconPath}")
    private String iconPath;

    public void createReward(CreateRewardRequest request) throws IOException {
        Reward reward = new Reward();
        reward.setDateRange(new DateRange(new Date()));
        reward.setDescription(request.getDescription());
        reward.setName(request.getName());
        reward.setType(RewardType.valueOf(request.getType()));
        reward.setRewardState(RewardState.valueOf(request.getRewardState()));
        reward.setRewardText(request.getRewardText());
        for (AddBadgeRequiredRequest badgeRequiredRequest : request.getBadges()) {
            final RequiredBadge requiredBadge = getTransientRequiredBadge(badgeRequiredRequest.getBadge(), badgeRequiredRequest.getCount());
            reward.getRequiredBadges().add(requiredBadge);
        }
        reward = rewardDa.save(reward);
    }

    public void uploadImage(InputStream inputStream, Long id) throws IOException {
        Reward reward = rewardDa.get(id);
        final byte[] data = IOUtils.toByteArray(inputStream);
        final String fileType = FileTypesUtils.checkType(data);
        final String fileName = System.currentTimeMillis() + "." + fileType;
        final File file = new File(rewardPath + fileName);
        FileUtils.writeByteArrayToFile(file, data);
        reward.setRewardImage(fileName);
        rewardDa.update(reward);

    }

    private RequiredBadge getTransientRequiredBadge(Long badgeId, Long count) {
        final RequiredBadge requiredBadge = new RequiredBadge();
        requiredBadge.setBadge(badgeDa.get(badgeId));
        requiredBadge.setCount(count);
        requiredBadge.setDateRange(new DateRange(new Date()));
        return requiredBadge;
    }

    public void removeBadgeRequired(RemoveBadgeRequiredRequest request) {
        Reward reward = rewardDa.get(request.getReward());
        for (RequiredBadge requiredBadge : reward.getRequiredBadges()) {
            if (requiredBadge.getBadge().getId().equals(request.getBadge())) {
                if (requiredBadge.getDateRange().getTo() == null)
                    requiredBadge.getDateRange().setTo(new Date());
            }
        }
        rewardDa.update(reward);
    }

    public void addBadgeRequired(AddBadgeRequiredRequest request) {
        Reward reward = rewardDa.get(request.getReward());
        reward.getRequiredBadges().add(getTransientRequiredBadge(request.getBadge(), request.getCount()));
        rewardDa.update(reward);
    }

    public void updateReward(UpdateRewardRequest request) {
        Reward reward = rewardDa.get(request.getId());
        reward.setDateRange(new DateRange(new Date()));
        reward.setDescription(request.getDescription());
        reward.setName(request.getName());
        reward.setType(RewardType.valueOf(request.getType()));
        reward.setRewardState(RewardState.valueOf(request.getRewardState()));
        reward.setRewardText(request.getRewardText());
        rewardDa.update(reward);
    }

    public void deleteReward(Long id) {
        Reward reward = rewardDa.get(id);
        reward.getDateRange().setTo(new Date());
        rewardDa.update(reward);
    }

    public RewardDto getReward(Long id) {
        Reward reward = rewardDa.get(id);
        final RewardDto rewardDto = new RewardDto();
        rewardDto.setName(reward.getName());
        rewardDto.setDescription(reward.getDescription());
        rewardDto.setType(reward.getType().name());
        rewardDto.setRewardState(reward.getRewardState().name());
        rewardDto.setRewardText(reward.getRewardText());
        for (RequiredBadge requiredBadge : reward.getRequiredBadges()) {
            if (requiredBadge.getDateRange().getTo() != null)
                continue;
            final RequiredBadgeItemDto e = new RequiredBadgeItemDto();
            e.setName(requiredBadge.getBadge().getName());
            e.setId(requiredBadge.getBadge().getId());
            e.setCount(requiredBadge.getCount());
            rewardDto.getBadges().add(e);
        }
        if (reward.getRewardImage() != null)
            rewardDto.setRewardImage(rewardUrl + reward.getRewardImage());

        return rewardDto;
    }

    public RewardPage getRewards(Long from, Long size) {
        RewardPage rewardPage = new RewardPage();
        final List<Reward> rewards = rewardDa.get(from, size);
        for (Reward reward : rewards) {
            final RewardItem rewardItem = getRewardItem(reward);
            rewardPage.getRewards().add(rewardItem);
        }
        rewardPage.setCount(rewardDa.getCount());
        return rewardPage;
    }

    private RewardItem getRewardItem(Reward reward) {
        final RewardItem rewardItem = new RewardItem();
        rewardItem.setId(reward.getId());
        rewardItem.setName(reward.getName());
        rewardItem.setRewardState(reward.getRewardState().name());
        rewardItem.setType(reward.getType().name());
        return rewardItem;
    }

    public RewardPage getRewards(String type, String name, String rewardState, Long from, Long size) {
        RewardPage rewardPage = new RewardPage();
        final List<Reward> rewards = rewardDa.get(type, name, rewardState, from, size);
        for (Reward reward : rewards) {
            final RewardItem rewardItem = getRewardItem(reward);
            rewardPage.getRewards().add(rewardItem);
        }
        rewardPage.setCount(rewardDa.getCount(type, name, rewardState));
        return rewardPage;
    }

    public UserRewardPage getRewards(Long userId) {
        UserRewardPage userRewardPage = new UserRewardPage();
        final List<Reward> activeRewards = rewardDa.getActiveRewards();
        for (Reward activeReward : activeRewards) {
            final UserRewardItem rewardItem = new UserRewardItem();
            rewardItem.setId(activeReward.getId());
            rewardItem.setName(activeReward.getName());
            rewardItem.setDescription(activeReward.getDescription());
            rewardItem.setType(activeReward.getType().name());
//            rewardItem.setRewardText(activeReward.getRewardText());
            boolean active = true;
            for (RequiredBadge requiredBadge : activeReward.getRequiredBadges()) {
                final RewardBadgeItem rewardBadgeItem = new RewardBadgeItem();
                rewardBadgeItem.setIcon(iconPath + requiredBadge.getBadge().getIcon());
                rewardBadgeItem.setName(requiredBadge.getBadge().getName());
                rewardBadgeItem.setDescription(requiredBadge.getBadge().getDescription());
                if (userBadgeDa.getCountSavedBadge(userId, requiredBadge.getBadge().getId()) == 0)
                    active = false;
                rewardItem.getRequiredBadges().add(rewardBadgeItem);
            }
            rewardItem.setActive(active);
            if (activeReward.getRewardImage() != null)
                rewardItem.setRewardImage(rewardPath + activeReward.getRewardImage());
            userRewardPage.getRewards().add(rewardItem);
        }
        userRewardPage.setCount((long) activeRewards.size());
        return userRewardPage;
    }

    public void enterRewards(Long userId, Long rewardId) {
        Reward reward = rewardDa.get(rewardId);
        final List<RequiredBadge> requiredBadges = reward.getRequiredBadges();
        UserReward userReward = new UserReward();
        for (RequiredBadge requiredBadge : requiredBadges) {
            List<UserBadge> userBadges = userBadgeDa.getSavedBadge(userId, requiredBadge.getBadge().getId());
            if (userBadges == null || userBadges.isEmpty())
                throw new InsufficientBadgeException();
            if (userBadges.size() < requiredBadge.getCount())
                throw new InsufficientBadgeException();

            int i = 0;
            for (UserBadge userBadge : userBadges) {
                if (i >= requiredBadge.getCount())
                    break;
                userBadge.setUserBadgeState(UserBadgeState.CONSUMED);
                userBadge = userBadgeDa.update(userBadge);
                userReward.getUserBadges().add(userBadge);
                i++;
            }
        }
        userReward.setDate(new Date());
        userReward.setReward(reward);
        final User user = userDa.get(userId);
        userReward.setUser(user);
        if (reward.getType().equals(RewardType.CONTEST))
            userReward.setUserRewardType(UserRewardType.APPLY);
        else if (reward.getType().equals(RewardType.PRIZE))
            userReward.setUserRewardType(UserRewardType.WINNER);
        Message message = new Message();
        message.setDate(new Date());
        message.setText(reward.getRewardText());
        message.setRead(false);
        message.setUser(user);
        messageDa.save(message);
        userRewardDa.save(userReward);
    }

    public AdminUserRewardPage getRewards(String phoneNumber, String fromDate, String toDate, String userRewardType, Long from, Long size) {
        return null;
    }
}

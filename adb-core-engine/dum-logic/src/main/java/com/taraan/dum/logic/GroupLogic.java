package com.taraan.dum.logic;

import com.taraan.dum.da.*;
import com.taraan.dum.dto.group.*;
import com.taraan.dum.exceptions.DumException;
import com.taraan.dum.exceptions.user.InvalidUserException;
import com.taraan.dum.exceptions.user.InvalidUserPasswordException;
import com.taraan.dum.model.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class GroupLogic {
    @Autowired
    private UserDa userDa;
    @Autowired
    private DrivingGroupDa drivingGroupDa;
    @Autowired
    private GroupInviteDa groupInviteDa;
    @Autowired
    private TripDa tripDa;
    @Autowired
    private UserScoreDa userScoreDa;

    public void createDrivingGroup(CreateGroupDrivingRequest groupDto, Long userId) {
        DrivingGroup drivingGroup = new DrivingGroup();
        drivingGroup.setDateRange(new DateRange(new Date()));
        final User owner = userDa.get(userId);
        drivingGroup.setOwner(owner);
        drivingGroup.setDescription(groupDto.getDescription());
        drivingGroup.setName(groupDto.getName());
        drivingGroup.getUsers().add(owner);
        drivingGroupDa.save(drivingGroup);
    }

    public void invites(GroupMultipleInviteRequest groupMultipleInviteRequest, Long userId) {
        for (String phoneNumber : groupMultipleInviteRequest.getPhoneNumbers()) {
            final GroupInviteRequest groupInviteRequest = new GroupInviteRequest();
            groupInviteRequest.setGroupId(groupMultipleInviteRequest.getGroupId());
            groupInviteRequest.setPhoneNumber(phoneNumber);
            this.invite(groupInviteRequest, userId);
        }
    }

    public void invite(GroupInviteRequest groupInviteRequest, Long userId) {
        if (groupInviteRequest.getPhoneNumber().startsWith("+")) {
            groupInviteRequest.setPhoneNumber("0" + groupInviteRequest.getPhoneNumber().substring(3));
        }
        final User user = userDa.getByPhoneNumber(groupInviteRequest.getPhoneNumber());
        if (user == null)
            throw new InvalidUserPasswordException();
        final List<GroupInvite> groups = groupInviteDa.getByUserAndGroup(user.getId(), groupInviteRequest.getGroupId());
        for (GroupInvite groupInvite : groups) {
            final boolean b = groupInvite.getState().equals(GroupInviteState.ACCEPT) || groupInvite.getState().equals(GroupInviteState.REQUEST);
            if (b)
                return;
        }
        GroupInvite groupInvite = new GroupInvite();
        groupInvite.setDateRange(new DateRange(new Date()));
        groupInvite.setGroup(drivingGroupDa.get(groupInviteRequest.getGroupId()));
        groupInvite.setState(GroupInviteState.REQUEST);
        groupInvite.setUser(user);
        groupInviteDa.save(groupInvite);
    }

    public GroupInvitePage getGroupInvites(Long userId, Long from, Long size) {
        List<GroupInvite> groupInvite = groupInviteDa.getByUser(userId, from.intValue(), size.intValue());
        final GroupInvitePage groupInvitePage = new GroupInvitePage();
        for (GroupInvite invite : groupInvite) {
            final GroupInviteItem e = new GroupInviteItem();
            final String lastName = invite.getUser().getLastName() == null ? "" : invite.getUser().getLastName();
            final String firstName = invite.getUser().getFirstName() == null ? "" : invite.getUser().getFirstName();
            e.setGroupName(invite.getGroup().getName());
            e.setInviteId(invite.getId());
            e.setOwnerName(firstName + lastName);
            e.setOwnerPhoneNumber(invite.getUser().getPhoneNumber());
            e.setGroupDescription(invite.getGroup().getDescription());
            groupInvitePage.getItems().add(e);
        }
        Double count = Math.ceil(groupInviteDa.getCountByUser(userId) / 10d);
        groupInvitePage.setCount(count.longValue());
        return groupInvitePage;
    }

    public void acceptInvite(Long userId, Long inviteId) {
        GroupInvite groupInvite = groupInviteDa.get(inviteId);
        if (groupInvite.getUser().getId().equals(userId)) {
            groupInvite.setState(GroupInviteState.ACCEPT);
            DrivingGroup drivingGroup = drivingGroupDa.get(groupInvite.getGroup().getId());
            drivingGroup.getUsers().add(groupInvite.getUser());
            drivingGroupDa.update(drivingGroup);
            groupInviteDa.update(groupInvite);
        }
    }

    public void rejectInvite(Long inviteId) {
        GroupInvite groupInvite = groupInviteDa.get(inviteId);
        groupInvite.setState(GroupInviteState.REJECT);
        groupInviteDa.update(groupInvite);
    }

    public GroupInfoPage getGroupData(Long groupId, Long userId) {
        final GroupInfoPage groupPage = new GroupInfoPage();
        final DrivingGroup drivingGroup = drivingGroupDa.get(groupId);
        boolean exist = false;
        for (User user : drivingGroup.getUsers()) {
            if (user.getId().longValue() == userId.longValue())
                exist = true;
            final UserGroupItem e = new UserGroupItem();
            e.setId(user.getId());
            e.setFirstName(user.getFirstName());
            e.setLastName(user.getLastName());
            e.setDistance(tripDa.getSumDistance(user.getId()));
            e.setTimeDuration(tripDa.getSumDuration(user.getId()));
            e.setScore(userScoreDa.getAverageScore(user.getId()).longValue());
            groupPage.getUserGroupItems().add(e);
        }
        groupPage.setName(drivingGroup.getName());
        groupPage.setDescription(drivingGroup.getDescription());
        groupPage.setOwner(drivingGroup.getOwner().getId().equals(userId));
        if (exist)
            return groupPage;
        return new GroupInfoPage();
    }

    public GroupPage getGroups(Long userId, Long from, Long size) {
        final GroupPage groupPage = new GroupPage();
        final List<DrivingGroup> drivingGroups = drivingGroupDa.getByUser(userId, from.intValue(), size.intValue());
        for (DrivingGroup drivingGroup : drivingGroups) {
            if (drivingGroup.getDateRange().getTo() != null)
                continue;
            final GroupDrivingDto e = new GroupDrivingDto();
            e.setId(drivingGroup.getId());
            e.setName(drivingGroup.getName());
            e.setDescription(drivingGroup.getDescription());
            final String lastName = drivingGroup.getOwner().getLastName() == null ? "" : drivingGroup.getOwner().getLastName();
            final String firstName = drivingGroup.getOwner().getFirstName() == null ? "" : drivingGroup.getOwner().getFirstName();
            e.setOwnerName(firstName + " " + lastName);
            groupPage.getGroupDrivings().add(e);
        }
        groupPage.setCount(drivingGroupDa.getCountByUser(userId));
        return groupPage;
    }

    public void deleteGroup(Long groupId, Long userId) {
        final DrivingGroup drivingGroup = this.drivingGroupDa.get(groupId);
        if (drivingGroup.getOwner().getId().equals(userId)) {
            drivingGroup.getDateRange().setTo(new Date());
            drivingGroupDa.update(drivingGroup);
            return;
        }
        final List<User> newUsers = drivingGroup.getUsers().stream().filter(user ->
                !user.getId().equals(userId)).collect(Collectors.toList());
        drivingGroup.setUsers(newUsers);
        drivingGroupDa.update(drivingGroup);
    }

    public void removeMember(Long userId, Long groupId, Long memberId) {
        final DrivingGroup drivingGroup = this.drivingGroupDa.get(groupId);
        if (!drivingGroup.getOwner().getId().equals(userId)) {
            throw new InvalidUserException();
        }
        final List<User> newUsers = drivingGroup.getUsers().stream().filter(user ->
                !user.getId().equals(memberId)).collect(Collectors.toList());
        drivingGroup.setUsers(newUsers);
        drivingGroupDa.update(drivingGroup);
    }
}

package com.taraan.dum.service.assembler;

import com.taraan.dum.dto.group.*;
import com.taraan.dum.logic.GroupLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Transactional
public class GroupAssembler {
    @Autowired
    private GroupLogic groupLogic;

    public void createGroup(CreateGroupDrivingRequest groupDto, Long userId) {
        groupLogic.createDrivingGroup(groupDto, userId);
    }

    public void invite(GroupInviteRequest groupInviteRequest, Long userId) {
        groupLogic.invite(groupInviteRequest, userId);
    }

    public GroupInvitePage getGroupInvites(Long userId, Long page) {
        long from = page * 10;
        long size = from + 10;
        return groupLogic.getGroupInvites(userId, from, size);
    }

    public void rejectInvite(Long inviteId) {
        groupLogic.rejectInvite(inviteId);
    }

    public void acceptInvite(Long userId, Long inviteId) {
        groupLogic.acceptInvite(userId, inviteId);
    }

    public GroupInfoPage getGroupData(Long groupId, Long userId) {
        return groupLogic.getGroupData(groupId, userId);
    }

    public GroupPage getGroups(Long userId, Long page) {
        long from = page * 10;
        long size = from + 10;
        return groupLogic.getGroups(userId, from, size);
    }

    public void invites(GroupMultipleInviteRequest groupInviteRequest, Long userId) {
        this.groupLogic.invites(groupInviteRequest, userId);
    }

    public void deleteGroup(Long groupId, Long userId) {
        this.groupLogic.deleteGroup(groupId,userId);
    }

    public void removeMember(Long userId, Long groupId, Long memberId) {
        this.groupLogic.removeMember(userId, groupId, memberId);
    }
}
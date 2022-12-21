package com.taraan.dum.dto.group;

import java.util.List;

public class GroupMultipleInviteRequest {
    private Long groupId;
    private List<String> phoneNumbers;

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public List<String> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<String> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }
}

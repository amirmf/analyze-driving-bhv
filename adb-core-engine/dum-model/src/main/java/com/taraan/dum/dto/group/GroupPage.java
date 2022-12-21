package com.taraan.dum.dto.group;

import java.util.ArrayList;
import java.util.List;

public class GroupPage {
    private List<GroupDrivingDto> groupDrivings= new ArrayList<>();
    private Long count;

    public List<GroupDrivingDto> getGroupDrivings() {
        return groupDrivings;
    }

    public void setGroupDrivings(List<GroupDrivingDto> groupDrivings) {
        this.groupDrivings = groupDrivings;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}

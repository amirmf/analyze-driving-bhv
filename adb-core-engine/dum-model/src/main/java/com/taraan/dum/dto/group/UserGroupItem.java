package com.taraan.dum.dto.group;

public class UserGroupItem {
    private long id;
    private Double distance;
    private Long timeDuration;
    private Long score;
    private String firstName;
    private String lastName;

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Double getDistance() {
        return distance;
    }

    public void setTimeDuration(Long timeDuration) {
        this.timeDuration = timeDuration;
    }

    public Long getTimeDuration() {
        return timeDuration;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    public Long getScore() {
        return score;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}

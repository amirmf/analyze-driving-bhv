package com.taraan.dum.service.assembler;

import com.taraan.dum.dto.user.*;
import com.taraan.dum.logic.UserLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Component
@Transactional
public class UserAssembler {
    @Autowired
    private UserLogic userLogic;

    public String registerUser(RegisterUserRequest registerUserRequest) {
        return userLogic.registerUser(registerUserRequest);
    }

    public String registerUser(AdminRegisterUserRequest registerUserRequest) {
        return userLogic.registerUser(registerUserRequest);
    }

    public LoginResponse login(LoginRequest loginRequest, boolean expire) {
        return userLogic.login(loginRequest,expire);
    }

    public void forgetPassword(RegisterUserRequest request) {
        userLogic.forgetPassword(request);
    }

    public void resetPassword(ResetPasswordRequest request) {
        userLogic.resetPassword(request);

    }


    public List<Long> getDrivingUserIds(Date from, Date to) {
        return userLogic.getDrivingUserIds(from, to);
    }

    public void updateProfile(Long id,ProfileUserDto request) {
        userLogic.updateProfile(id,request);
    }

    public ProfileUserDto getProfile(Long userId) {
        return userLogic.getProfile(userId);
    }

    public void updateNotificationToken(NotificationRequest notificationRequest) {
        userLogic.updateNotificationToken(notificationRequest);
    }

    public UserPage getUserList(String phoneNumber, String email, String firstName, String lastName, Long from, Long size) {
       return userLogic.getUserList(phoneNumber,email,firstName,lastName,from,size);
    }

    public AdminProfileUserDto getAdminProfile(Long userId) {
        return userLogic.getAdminProfile(userId);
    }

    public Boolean isCompleteProfile(Long userId) {
        return userLogic.isCompleteProfile(userId);
    }
}

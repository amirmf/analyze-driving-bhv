package com.taraan.dum.logic;

import com.taraan.dum.common.DateUtils;
import com.taraan.dum.common.TokenUtils;
import com.taraan.dum.common.password.StringUtils;
import com.taraan.dum.da.*;
import com.taraan.dum.dto.user.*;
import com.taraan.dum.exceptions.EntityDoesNotExistException;
import com.taraan.dum.exceptions.user.InvalidCodeException;
import com.taraan.dum.exceptions.user.InvalidEmailException;
import com.taraan.dum.exceptions.user.InvalidUserPasswordException;
import com.taraan.dum.model.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class UserLogic {
    @Autowired
    private UserDa userDa;
    @Autowired
    private UserBadgeDa userBadgeDa;
    @Autowired
    private BadgeDa badgeDa;
    @Autowired
    private TripDa tripDa;
    @Autowired
    private UserScoreDa userScoreDa;
    @Autowired
    private ForgetPasswordRequestDa forgetPasswordRequestDa;
    @Autowired
    private NotifyLogic notifyLogic;

    public String registerUser(RegisterUserRequest registerUserRequest) {
        if (registerUserRequest.getPhoneNumber() == null || registerUserRequest.getPhoneNumber().isEmpty())
            return null;//todo must be error
        User user = userDa.getByPhoneNumber(registerUserRequest.getPhoneNumber());
        if (user == null) {
            user = new User();
            user.setPhoneNumber(registerUserRequest.getPhoneNumber());
        }

        final String saltString = StringUtils.getSaltStringNumber(5);
        user.setPassword(saltString);
        if (user.getId() == null)
            userDa.save(user);
        if (user.getId() != null)
            userDa.update(user);
        final String text = "کد دعوت شما به  کارنو " + saltString + ".\nدوست عزیز با کارنو رانندگی کن و جایزه بگیر.";
        notifyLogic.sendSms(registerUserRequest.getPhoneNumber(), text);
        return "";
    }

    public String registerUser(AdminRegisterUserRequest registerUserRequest) {
        if (registerUserRequest.getPhoneNumber() == null && registerUserRequest.getPhoneNumber().isEmpty())
            return null;//todo must be error
        User user = new User();
        user.setPhoneNumber(registerUserRequest.getPhoneNumber());
        final String saltString = StringUtils.getSaltStringNumber(5);
        user.setPassword(saltString);
        user.setAddress(registerUserRequest.getAddress());
        if (registerUserRequest.getBirthDate() != null && !registerUserRequest.getBirthDate().isEmpty())
            user.setBirthDate(DateUtils.getDate(registerUserRequest.getBirthDate()));
        user.setCity(registerUserRequest.getCity());
        user.setEmail(registerUserRequest.getEmail());
        user.setFirstName(registerUserRequest.getFirstName());
        user.setLastName(registerUserRequest.getLastName());
        user.setPostalNumber(registerUserRequest.getPostalNumber());
        userDa.save(user);
        notifyLogic.sendSms(registerUserRequest.getPhoneNumber(), "کد ورود به حساب :" + saltString);
        return "";

    }

    public LoginResponse login(LoginRequest loginRequest, boolean expire) {
        try {
            User user = userDa.getByPhoneNumber(loginRequest.getPhoneNumber());
            if (user == null)
                throw new InvalidUserPasswordException();

            if (user.getPassword().equals(loginRequest.getPassword())) {
                if (expire)
                    return new LoginResponse(TokenUtils.generateToken(String.valueOf(user.getId()), user.getEmail(), 30));
                else
                    return new LoginResponse(TokenUtils.generateToken(String.valueOf(user.getId()), user.getEmail()));
            }
            throw new InvalidUserPasswordException();
        } catch (Exception e) {
            throw new InvalidUserPasswordException(e);
        }
    }


    public void forgetPassword(RegisterUserRequest request) {
        User user = userDa.getByPhoneNumber(request.getPhoneNumber());
        if (user == null)
            throw new EntityDoesNotExistException();
        ForgetPasswordRequest forgetPasswordRequest = new ForgetPasswordRequest();
        forgetPasswordRequest.setCode(StringUtils.getSaltString(5));
        forgetPasswordRequest.setPhoneNumber(request.getPhoneNumber());
        forgetPasswordRequestDa.save(forgetPasswordRequest);
    }

    public void resetPassword(ResetPasswordRequest resetPasswordRequest) {
        ForgetPasswordRequest request = forgetPasswordRequestDa.getByEmail(resetPasswordRequest.getEmail());
        if (request == null)
            throw new InvalidEmailException();

        if (!request.getCode().equals(resetPasswordRequest.getCode()))
            throw new InvalidCodeException();

        User user = userDa.getByPhoneNumber(request.getPhoneNumber());
        if (user == null)
            throw new InvalidEmailException();
        user.setPassword(resetPasswordRequest.getPassword());
        userDa.update(user);
    }

    public List<Long> getDrivingUserIds(Date from, Date to) {
        return userDa.getDrivingUserIds(from, to);
    }

    public void updateProfile(Long userId, ProfileUserDto request) {
        final User user = userDa.get(userId);
        user.setAddress(request.getAddress());
        boolean badge = true;
        if (request.getBirthDate() != null && !request.getBirthDate().isEmpty())
            user.setBirthDate(DateUtils.getDate(request.getBirthDate()));
        else {
            badge = false;
            user.setBirthDate(null);
        }
        if (request.getEmail() == null || request.getEmail().isEmpty())
            badge = false;
        user.setEmail(request.getEmail());
//        if (request.getPostalNumber() == null || request.getPostalNumber().isEmpty())
//            badge = false;
//        user.setPostalNumber(request.getPostalNumber());
        if (request.getCity() == null || request.getCity().isEmpty())
            badge = false;
        user.setCity(request.getCity());
        if (request.getFirstName() == null || request.getFirstName().isEmpty())
            badge = false;
        user.setFirstName(request.getFirstName());
        if (request.getLastName() == null || request.getLastName().isEmpty())
            badge = false;
        user.setLastName(request.getLastName());
        if (request.getGender() != null && !request.getGender().isEmpty())
            user.setGender(Gender.valueOf(request.getGender()));
        else
            badge = false;

//        if (request.getAge() == null)
//            badge = false;
        user.setAge(request.getAge());
        userDa.update(user);
        if (badge) {
            final Badge profile_badge = badgeDa.get("PROFILE_BADGE");
            if (profile_badge != null) {
                List<UserBadge> userBadges = userBadgeDa.getByUserAndBadge(user.getId(), profile_badge.getId());
                if (userBadges.isEmpty()) {
                    UserBadge userBadge = new UserBadge();
                    userBadge.setBadge(profile_badge);
                    userBadge.setUserBadgeState(UserBadgeState.SAVED);
                    userBadge.setUser(user);
                    userBadge.setDate(new Date());
                    userBadgeDa.save(userBadge);
                }

            }
        }
    }

    public ProfileUserDto getProfile(Long userId) {
        ProfileUserDto profileUserDto = new ProfileUserDto();
        final User user = userDa.get(userId);
        profileUserDto.setAddress(user.getAddress());
        if (user.getBirthDate() != null)
            profileUserDto.setBirthDate(DateUtils.getDate(user.getBirthDate()));
        profileUserDto.setCity(user.getCity());
        profileUserDto.setEmail(user.getEmail());
        profileUserDto.setFirstName(user.getFirstName());
        profileUserDto.setLastName(user.getLastName());
        profileUserDto.setPostalNumber(user.getPostalNumber());
        profileUserDto.setPhoneNumber(user.getPhoneNumber());
        if (user.getGender() != null)
            profileUserDto.setGender(user.getGender().name());
        profileUserDto.setAge(user.getAge());
        return profileUserDto;
    }

    public void updateNotificationToken(NotificationRequest notificationRequest) {
        final User user = userDa.get(notificationRequest.getUserId());
        user.setToken(notificationRequest.getToken());
        user.setTokenType(notificationRequest.getType());
        userDa.update(user);
    }

    public UserPage getUserList(String phoneNumber, String email, String firstName, String lastName, Long from, Long size) {
        UserPage userPage = new UserPage();
        List<User> users = userDa.get(phoneNumber, email, firstName, lastName, from, size);
        Long count = userDa.getCount(phoneNumber, email, firstName, lastName);
        for (User user : users) {
            userPage.getItems().add(new UserItem(user.getId(), user.getFirstName(), user.getLastName(), user.getPhoneNumber()));
        }
        userPage.setCount(count);
        return userPage;
    }

    public AdminProfileUserDto getAdminProfile(Long userId) {
        AdminProfileUserDto profileUserDto = new AdminProfileUserDto();
        final User user = userDa.get(userId);
        profileUserDto.setAddress(user.getAddress());

        if (user.getBirthDate() != null)
            profileUserDto.setBirthDate(DateUtils.getDate(user.getBirthDate()));
        profileUserDto.setId(user.getId());
        profileUserDto.setCity(user.getCity());
        profileUserDto.setEmail(user.getEmail());
        profileUserDto.setFirstName(user.getFirstName());
        profileUserDto.setLastName(user.getLastName());
        profileUserDto.setPostalNumber(user.getPostalNumber());
        profileUserDto.setPhoneNumber(user.getPhoneNumber());
        profileUserDto.setDistance(tripDa.getSumDistance(userId));
        profileUserDto.setTime(tripDa.getSumTime(userId));
        profileUserDto.setScore(userScoreDa.getSumScore(userId));
        return profileUserDto;
    }

    public Boolean isCompleteProfile(Long userId) {
        final User user = userDa.get(userId);
        if (user == null)
            throw new EntityDoesNotExistException("User");
        final boolean isLastName = user.getLastName() != null && !user.getLastName().isEmpty();
        final boolean isFirstName = user.getFirstName() != null && !user.getFirstName().isEmpty();
        final boolean isPhoneNumber = user.getPhoneNumber() != null && !user.getPhoneNumber().isEmpty();
        final boolean isEmail = user.getEmail() != null && !user.getEmail().isEmpty();
        return isEmail && isFirstName && isLastName && isPhoneNumber;
    }
}

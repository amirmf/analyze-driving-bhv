//
//  Constants.swift
//  driver-behavior-ios-app
//
//  Created by Amir Mansouri Fard (amf.fard@gmail.com) on 6/25/18.
//  Copyright © 2018 Amir Mansouri Fard (amf.fard@gmail.com). All rights reserved.
//

import Foundation
import UIKit

class K {

    static let color_p_lighter = UIColor(named:"primary-lighter")
    static let color_p_light = UIColor(named:"primary-light")
    static let color_p = UIColor(named:"primary")
    static let color_p_dark = UIColor(named:"primary-dark")
    static let color_s = UIColor(named:"secondary");
    static let color_gray_light = UIColor(named:"gray-light");
    static let color_gray = UIColor(named:"gray");
    static let color_gray_dark = UIColor(named:"gray-dark");
    

    static let font_size : Int = 11;
    static let font_size_selected : Int = 12;
    static let font_family : String = "American Typewriter";
    
    static let lbl_setting = "تنظیمات"
    static let img_setting = "mn_setting"

    static let btn_radious : Float = 0.3;
    
    static let API_TOKEN_KEY = "apiToken";
    
    static let baseUrl = "http://31.184.132.112:8080/rest/"
    static let REST_LOGIN_URL=baseUrl + "users/login"
    static let REST_REGISTER_URL=baseUrl + "users/register"
    static let REST_HOME_STATISTIC_URL=baseUrl + "statistic/home"
    static let REST_LIST_NEWS_URL=baseUrl + "news/list"
    static let REST_LIST_CHALLENGE_URL=baseUrl + "challenge/list"
    static let REST_GET_CHALLENGE_URL=baseUrl + "challenge/"
    static let REST_JOIN_CHALLENGE_URL=baseUrl + "challenge"
    static let REST_START_TRIP = baseUrl + "trip/startTrip"
    static let REST_END_TRIP = baseUrl + "trip/endTrip"
    static let REST_LIST_TRIPS = baseUrl + "trip/list/"
    static let REST_TRIP_GEO_LOC = baseUrl + "trip/geoLocations/"
    static let REST_TRIP_TRACKER = baseUrl + "tripTrackerInfo"
    static let REST_GET_TRIP = baseUrl + "trip/"
    static let REST_LIST_MESSAGES = baseUrl + "message/list/"
    static let REST_MESSAGE_READ = baseUrl + "message/read/"
    static let REST_STATISTC_URL = baseUrl + "statistic/range/"
    static let REST_LIST_MEDALS = baseUrl + "statistic/rewards"
    static let REST_LIST_REWARDS = baseUrl + "reward/list"
    static let REST_REWARD_ENTER = baseUrl + "reward/enterReward/"
    static let REST_CREATE_GROUP = baseUrl + "group"
    static let REST_GET_GROUP = baseUrl + "group/groupData/"
    static let REST_LIST_GROUPS = baseUrl + "group/list/"
    static let REST_DELETE_GROUP = baseUrl + "group/"
    static let REST_INVITATE_MULTIPLE = baseUrl + "group/multipleInvite"
    static let REST_LIST_INVITATIONS = baseUrl + "group/inviteList/"
    static let REST_ACCEPT_INVITATION = baseUrl + "group/acceptInvite/"
    static let REST_REMOVE_MEMEBER = baseUrl + "group/removeMember"
    static let REST_GET_USER_PROFILE = baseUrl + "users/profile"
    static let REST_GET_REMINDER = baseUrl + "reminder"
    static let REST_UPDATE_REMINDER = baseUrl + "reminder"
    static let REST_DELETE_TRIP = baseUrl + "trip/"
    static let REST_GET_CHILDS = baseUrl + "parentalControl/child"
    static let REST_GET_PARENTS = baseUrl + "parentalControl/parent"
    static let REST_ADDREMOVE_PARENT = baseUrl + "parentalControl/"

    struct APIParameterKey {
        static let password = "password"
        static let email = "email"
    }
    
}


enum HTTPHeaderField: String {
    case authentication = "Authorization"
    case contentType = "Content-Type"
    case acceptType = "Accept"
    case acceptEncoding = "Accept-Encoding"
}

enum ContentType: String {
    case json = "application/json"
}

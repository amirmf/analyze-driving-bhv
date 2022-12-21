//
//  DTO.swift
//  driver-behavior-ios-app
//
//  Created by Amir Mansouri Fard (amf.fard@gmail.com) on 6/23/18.
//  Copyright © 2018 Amir Mansouri Fard (amf.fard@gmail.com). All rights reserved.
//

import Foundation

// OK
struct LoginRequest{
    var phoneNumber : String;
    var password : String;
    
    init(phoneNumber:String,password:String){
        self.phoneNumber=phoneNumber;
        self.password=password;
    }
}

struct LoginResponse{
    init(){
        
    }
}

// OK
struct RegisterRequest{
    var phoneNumber : String;
}

// OK
struct RegisterResponse{
    var code : String;
}

struct ChallengeMember : Codable{
    var name : String;
    var score: Double;
}

// OK
struct Challenge : Codable{
    var icon : String;
    var name: String;
    var descrption:String?;
    var teamCount : Int;
    var participantCount:Int;
    var dayLeft:Int;
    var id : Int;
    var startDate:String;
    var endDate:String;
}
struct Challenges : Codable{
    var challengeItems : Array<Challenge>;
}

// OK
struct ChallengeDetail : Codable{
    
    var name: String;
    var description:String;
    var active: Bool;
    var teamCount : Int;
    var participantCount:Int;
    var challengeTopMembers : Array<ChallengeMember>;
    var scoreChallengeMembers : Array<ChallengeMember>
    var allChallengeMembers : Array<ChallengeMember>
    var teamTopMembers : Array<ChallengeMember>;
    var winners : Array<ChallengeMember>;
    var prize : String;
    var prizeDescription : String;
    var prizeImage : String;
    var icon : String;
    var image :String;
    var dayLeft:Int;
    var startDate:String;
    var endDate : String;
    
}

struct ChallengeResult{
    
}

struct NewsHome : Codable{
    var id : Int?;
    var title:String;
    var detailed : String;
    var image : String;
    var fromDate : String?;
    var toDate : String?;
    var reference : String?;
    
    init(from decoder:Decoder) throws {
        let values = try decoder.container(keyedBy: CodingKeys.self)
        id = try? values.decode(Int.self, forKey: .id)
        title = try values.decode(String.self, forKey: .title)
        detailed = try values.decode(String.self, forKey: .detailed)
        image = try values.decode(String.self, forKey: .image)
        fromDate = try? values.decode(String.self, forKey: .fromDate)
        toDate = try? values.decode(String.self, forKey: .toDate)
    }
    
    enum CodingKeys : String, CodingKey{
        case id
        case title
        case detailed
        case image
        case fromDate
        case toDate
    }
    
}


struct News : Codable{
    var id : Int?;
    var title:String;
    var detailed : String;
    var image : String;
    var startDate : String?;
    var endDate : String?;
    var reference : String?;
    
    init(from decoder:Decoder) throws {
        let values = try decoder.container(keyedBy: CodingKeys.self)
        id = try? values.decode(Int.self, forKey: .id)
        title = try values.decode(String.self, forKey: .title)
        detailed = try values.decode(String.self, forKey: .detailed)
        image = try values.decode(String.self, forKey: .image)
        startDate = try? values.decode(String.self, forKey: .startDate)
        endDate = try? values.decode(String.self, forKey: .endDate)
    }
    
    enum CodingKeys : String, CodingKey{
        case id
        case title
        case detailed
        case image
        case startDate
        case endDate
    }
    
}

struct Newses :Codable{
    
    var newses : Array<News>;
    var count : String?;
    
    init(from decoder:Decoder) throws {
        let values = try decoder.container(keyedBy: CodingKeys.self)
        newses = try values.decode(Array<News>.self, forKey: .newses)
    }
    
    private enum CodingKeys : String, CodingKey{
        case newses
    }
    
}

struct StartTripRequest : Codable{
    var deviceId : String;
}

struct StartTripResponse : Codable{
    var tripId : Int64;
}

struct EndTripRequest : Codable{
//    var userId : Int64
    var name : String
    
    init(name: String, tripId : Int64) {
        self.name = name
//        self.userId = tripId
    }
}

struct EndTripResponse : Codable{
}

struct TripsList : Codable {
    var trips : Array<Trip>
    var count : Int
}

struct Trip : Codable{
    var id : Int;
    var name : String;
    var tripStartDate : String;
    var tripEndDate : String;
}

struct TripDetail : Codable{
    var id : Int64;
    var tripStartDate : String;
    var tripEndDate : String;
    var name : String;
    var underLimitedCount : Double;
    var overSpeedCount : Double;
    var highOverSpeedCount : Double;
    var underLimitedPercentage : Double;
    var overSpeedCountPercentage : Double;
    var highOverSpeedCountPercentage : Double;
    var averageSpeed : Double;
    var maxSpeed : Double;
    var corneringCount : Int;
    var brakingCount : Int;
    var accelerationCount : Int;
    var distance : Double;
    var otherDistance : Double;
    var urbanDistance : Double;
    var highwayDistance : Double;
    var idleTime : Int64;
    var duration : Int64;
    var badDirection : Int64;
    var minIdleTime : Int64;
    var maxIdleTime : Int64;
    var movementTime : Int64;
    var minMovementTime : Int64;
    var maxMovementTime : Int64;
    var badBrakingCount : Int;
    var badAccelerationCount : Int;
    var score : Int64;
    
}

struct TripTrack : Codable {
    var order : Int64;
    var deviceId : String;
    var longitude : Double;
    var latitude : Double;
    var x : Double;
    var y : Double;
    var z : Double;
    var sumAcceleration : Double;
    var realSpeed : Double;
    var direction : Int;
    var postDate : String;
    
    init() {
        order = 0
        deviceId = ""
        longitude = 0.0
        latitude = 0.0
        x = 0.0
        y = 0.0
        z = 0.0
        sumAcceleration = 0.0
        realSpeed = 0.0
        direction = 0
        postDate = ""
    }
}

struct TripTrackInfo : Codable{
    var tripTrackerInfos : Array<TripTrack>
    
    init(tripInfos : Array<TripTrack>) {
        self.tripTrackerInfos = tripInfos
    }
}

struct Medal  : Codable{
    var title : String;
    var icon : String;
    var description : String;
    var count : Int;
}

struct Medals  : Codable{
    var rewards : Array<Medal>;
}

struct Badge :Codable{
    var icon:String;
    var name: String;
    var description : String;
}

struct Reward : Codable {
    var id : Int64;
    var requiredBadges : Array<Badge>;
    var name: String;
    var type: String;
    var description : String;
//    var rewardText : String;
    var rewardImage : String;
    var active: Bool;
}

struct Rewards : Codable{
    var rewards : Array<Reward>;
}

struct Home : Codable{
    var score : Int64;
    var tripCount : Int64;
    var sumDistance : Double;
    var averageSpeed : Int64;
    var sumTimeDuration : Int64;
    var weekPercentage : Double;
    var weekendPercentage : Double;
    var urbanPercentage : Double;
    var highwayPercentage : Double;
    var otherPercentage : Double;
    var rewards : Array<Medal>?
    var challengeItems : Array<Challenge>?
    var news :NewsHome?
    var unreadMessageCount : Int;
}

struct TripGeoLocations : Codable {
    var geoLocations : Array<GeoLocation>
}

struct GeoLocation : Codable {
    var longitude : Double
    var latitude : Double
    var event : Int
}

struct Message : Codable{
    var text : String;
    var date : String;
    var read : Bool;
}

struct Messages : Codable{
    var messages : Array<Message>?;
    var count : Int64;
    var unreadCount: Int64;
}

struct StatisticOfHourDto : Codable {
    var hour : Int;
    var distance : Double;
    var time : Double;
}

struct Statistic : Codable{
    var score : Int64;
    var tripCount : Int64;
    var accelerationEvent : Double;//todo per 100 km
    var brakingEvent : Double;//todo per 100 km
    var corneringEvent : Double;//todo per 100 km
    var averageSpeed : Double;//todo how calculate
    var maxSpeed : Double;
    var underLimitedSpeedingTime : Double;//todo percentage
    var underLimitedSpeedCount : Double;//todo percentage
    var overSpeedingTime : Double;//todo percentage
    var overSpeedCount : Double;//todo percentage
    var highOverSpeedingTime : Double;//todo percentage
    var highOverSpeedCount : Double;//todo percentage
    var dayPercentage : Double;
    var nightPercentage : Double;
    var urbanPercentage : Double;
    var highwayPercentage : Double;
    var otherPercentage : Double;
    var statisticOfHourDtos : Array<StatisticOfHourDto>;
    var duration : Double;//todo time duration
    var distance : Double;//
    var idleTime : Double;//
}

struct DrivingGroup : Codable{//GroupDrivingDto
    var id : Int64?
    var name : String?
    var description : String?
    var owner : Int64?
    var ownerName : String?
}

struct UserGroupMember : Codable{//UserGroupItem
    var id : Int64;
    var distance : Double;
    var timeDuration : Int64;
    var score : Int64;
    var firstName: String;
    var lastName: String;
}

struct UserGroupInfo : Codable{//GroupInfoPage
    var userGroupItems : Array<UserGroupMember>;
    var name : String;
    var description : String;
    var owner : Bool;
}


struct GroupInvitation : Codable{//groupInviteItem
    var inviteId : Int64;
    var ownerName : String;
    var ownerPhoneNumber : String;
    var groupName : String;
    var groupDescription : String;
}

struct GroupInvitations :Codable{//groupInvitePage
    var items : Array<GroupInvitation>;
    var count : Int64;
}

struct MultipleInviteRequest : Codable{
    
    var groupId : Int64;
    var phoneNumbers : Array<String>;
    
}

struct Parent : Codable {
    var id : Int64
    var name : String
    var score : Int64?
}

struct ParentsList : Codable {
    var items : Array<Parent>
    var count : Int64
}

struct GroupsList : Codable {
    var groupDrivings : Array<DrivingGroup>?
    var count : Int64?
}

struct RemoveMemberRequest : Codable{
    
    var groupId : Int64;
    var memberId : Int64;
    
}
struct CreateGroupRequest : Codable{
    
    var name : String;
    var description : String;
    
}

struct Profile : Codable{
    var id : Int64?
    var firstName : String?
    var lastName : String?
    var address : String?
    var city : String?
    var postalNumber : String?
    var email : String?
    var birthDate : String?
    var phoneNumber : String?
    var gender : String?
    var age : Int?
}

struct Reminder : Codable{
    var remindDayNotification : Int64?
    var insuranceExpirationDate : String?
    var insuranceRegisterOn : String?
    var lastServiceDate : String?
    var serviceAfter : Int64?
    var active: Bool
    
    
    init(insuranceExpirationDate : String , lastServiceDate : String , serviceAfter : Int64 , active : Bool) {
        self.insuranceExpirationDate = insuranceExpirationDate
        self.serviceAfter = serviceAfter
        self.active = active
        self.lastServiceDate = lastServiceDate
    }
    
    init(active : Bool) {
        self.active = active
    }
}


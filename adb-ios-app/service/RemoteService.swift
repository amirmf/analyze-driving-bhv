//
//  HttpService.swift
//  driver-behavior-ios-app
//
//  Created by Amir Mansouri Fard (amf.fard@gmail.com) on 6/23/18.
//  Copyright © 2018 Amir Mansouri Fard (amf.fard@gmail.com). All rights reserved.
//

import Foundation
import Alamofire
import SwiftyJSON

class RemoteService{
    
    static func getToken()->String{
        let defaults = UserDefaults.standard;

        if let token = defaults.string(forKey:K.API_TOKEN_KEY){
            return token;
        }else{
            return "";
        }

    }
    
    static func login(request : LoginRequest , completion : @escaping (DataResponse<Any>) -> ()) {
        
        let response : LoginResponse? = nil;
        
        guard let url = URL(string:K.REST_LOGIN_URL) else{
            return
        }
        
        let parameters : [String:Any] = [
            "phoneNumber" : request.phoneNumber
            ,"password" : request.password
        ];

        Alamofire.request(url
            ,method:.post
            ,parameters : parameters
            , encoding : JSONEncoding.default)
            .responseJSON { res in
                completion(res)
        }
        
    }
    
    static func register(request : RegisterRequest) ->RegisterResponse?{
        
        let response : RegisterResponse? = nil;
        
        guard let url = URL(string:K.REST_REGISTER_URL) else{
            return response;
        }
        
        let parameters : [String:Any] = [
            "phoneNumber" : request.phoneNumber
        ];
        
        Alamofire.request(url,
                          method:.post
            ,parameters : parameters
            , encoding : JSONEncoding.default)
            .responseJSON { res in
                
                guard res.result.isSuccess else {
                    print("Error while login: \(String(describing: res.result.error))");
                    return;
                }
                
                guard let value = res.result.value else {
                    print("Error in result of login request: \(String(describing: res.result.value))");
                    return;
                }
                
        }
        return response;
    }
    
//    static func startDriving(request : StartDriveRequest) -> StartDriveResponse{
//
//    }
    
//    static func stopDriving(request : StopDriveRequest) -> StopDriveResponse{
//
//    }
    
    static func listChallenges(callback:  APICallBack)-> Void{
        
        guard let url = URL(string:K.REST_LIST_CHALLENGE_URL) else{
            return;
        }
        
        var request = URLRequest(url: url)
        
        request.httpMethod = "GET"
        request.setValue(getToken(), forHTTPHeaderField: "authentication")

        
        Alamofire.request(request).responseJSON { (res) in
            guard let resCode = res.response?.statusCode else{
                print("Error while getting list of challenges: \(String(describing: res.result.error))");
                callback.onError("");
                return;
            }
            
            guard resCode >= 199 && resCode < 300 else{
                print("Error while getting list of challenges: \(String(describing: res.result.error))");
                callback.onError("");
                return;
            }
            
            guard let value = res.result.value else {
                print("Error in result of getting challenge request: \(String(describing: res.result.value))");
                return;
            }
            
            guard let data = res.data else {
                print("Error in result(data) of getting challenge request: \(String(describing: res.result.value))");
                return;
            }
            
            let json = JSON(data);//["token"].stringValue;
            
            print(json.debugDescription)
            
            let result : Challenges? =  try?
                JSONDecoder().decode(Challenges.self,from:data);
            
            callback.onSuccess(result);
        }
    
    }
    
    static func getChallengeDetail(_ id  : Int,_ callback:  APICallBack){
        
        guard let url = URL(string:K.REST_GET_CHALLENGE_URL + String(id)) else{
            return;
        }
        
        var request = URLRequest(url: url)
        
        request.httpMethod = "GET"
        request.setValue(getToken(), forHTTPHeaderField: "authentication")

        Alamofire.request(request).responseJSON { (res) in
            guard let resCode = res.response?.statusCode else{
                print("Error while getting list of challenges: \(String(describing: res.result.error))");
                callback.onError("");
                return;
            }
            
            guard resCode >= 199 && resCode < 300 else{
                print("Error while getting list of challenges: \(String(describing: res.result.error))");
                callback.onError("");
                return;
            }
            
            guard let value = res.result.value else {
                print("Error in result of getting challenge request: \(String(describing: res.result.value))");
                return;
            }
            
            guard let data = res.data else {
                print("Error in result(data) of getting challenge request: \(String(describing: res.result.value))");
                return;
            }
            
            let json = JSON(data);//["token"].stringValue;
            
            print(json.debugDescription)
            
            let result : ChallengeDetail? =  try?
                JSONDecoder().decode(ChallengeDetail.self,from:data);
            
            callback.onSuccess(result);
        }
        
//        Alamofire.request(url
//            ,method:.get
//            ,parameters : parameters
//            , encoding : JSONEncoding.default
//            ,headers : headers)
//            .responseJSON { res in
//
////                guard res.result.isSuccess else {
////                    print("Error while getting list of challenges: \(String(describing: res.result.error))");
////                    callback.onError("");
////                    return;
////                }
//
//
//
//        }

    }
    
    static func listNews(pageNumber : Int , completion : @escaping (DataResponse<Any>)->())-> Void{
        
        guard let url = URL(string:K.REST_LIST_NEWS_URL + "/\(pageNumber)") else{
            return;
        }
        
        var request = URLRequest(url: url)
        
        request.httpMethod = "GET"
        request.setValue(getToken(), forHTTPHeaderField: "authentication")
        
        
        Alamofire.request(request).responseJSON { (res) in
            completion(res)
        }
        
    }

    static func startTrip(req : StartTripRequest, completion : @escaping (DataResponse<Any>)->())-> Void{
        
        guard let url = URL(string:K.REST_START_TRIP) else{
            return;
        }
        
        var request = URLRequest(url: url)
        
        request.httpMethod = "POST"
        let postData = "{\"deviceId\":\"\(req.deviceId)\"}"
        request.httpBody = postData.data(using: .utf8)
        request.addValue(getToken(), forHTTPHeaderField: "authentication")
        request.addValue("application/json", forHTTPHeaderField: "Content-Type")
        
        
        Alamofire.request(request).responseJSON { (res) in
            completion(res)
        }
        
    }
    
    static func endTrip(req : EndTripRequest, completion : @escaping (DataResponse<Any>)->())-> Void{
        
        guard let url = URL(string:K.REST_END_TRIP) else{
            return;
        }
        
        var request = URLRequest(url: url)
        
        request.httpMethod = "POST"
        var postData = ""
        do {
            let jsonData = try JSONEncoder().encode(req)
            postData = String(data: jsonData , encoding: .utf8) ?? ""
        } catch let err {
            print(err.localizedDescription)
        }
        request.httpBody = postData.data(using: .utf8)
        request.addValue(getToken(), forHTTPHeaderField: "authentication")
        request.addValue("application/json", forHTTPHeaderField: "Content-Type")
        
        
        Alamofire.request(request).responseJSON { (res) in
            completion(res)
        }
        
    }

    /*
     * Trip
     */
    
    static func listTrips( _ pageNumber : Int, completion : @escaping (DataResponse<Any>)->())-> Void{
        
        guard let url = URL(string:K.REST_LIST_TRIPS + String(pageNumber)) else{
            return;
        }
        
        var request = URLRequest(url: url)
        
        request.httpMethod = "GET"
        request.setValue(getToken(), forHTTPHeaderField: "authentication")
        
        
        Alamofire.request(request).responseJSON { (res) in
            completion(res)
        }
        
    }

    /*
     * TripDetail
     */
    static func getTripDetails( _ tripId : Int, completion : @escaping (DataResponse<Any>)->())-> Void{
        
        guard let url = URL(string:K.REST_GET_TRIP + "\(tripId)") else{
            return;
        }
        
        var request = URLRequest(url: url)
        
        request.httpMethod = "GET"
        request.setValue(getToken(), forHTTPHeaderField: "authentication")
        
        
        Alamofire.request(request).responseJSON { (res) in
            completion(res)
        }
        
    }
    
    static func joinChannelge( _ challengeId : Int, completion : @escaping (DataResponse<Any>)->())-> Void{
        
        guard let url = URL(string:K.REST_JOIN_CHALLENGE_URL) else{
            return;
        }
        
        var request = URLRequest(url: url)
        
        request.httpMethod = "POST"
        let postData = "{\"challengeId\":\(challengeId)}"
        request.httpBody = postData.data(using: .utf8)
        request.setValue(getToken(), forHTTPHeaderField: "authentication")
        request.addValue("application/json", forHTTPHeaderField: "Content-Type")
        
        
        Alamofire.request(request).responseJSON { (res) in
            completion(res)
        }
        
    }
    
    static func getHome(completion : @escaping (DataResponse<Any>)->())-> Void{
        
        guard let url = URL(string:K.REST_HOME_STATISTIC_URL) else{
            return;
        }
        
        var request = URLRequest(url: url)
        
        request.httpMethod = "GET"
        request.setValue(getToken(), forHTTPHeaderField: "authentication")
        
        
        Alamofire.request(request).responseJSON { (res) in
            completion(res)
        }
        
    }

    static func listMessages( _ pageNumber : Int, completion : @escaping (DataResponse<Any>)->())-> Void{
        
        guard let url = URL(string:K.REST_LIST_MESSAGES + String(pageNumber)) else{
            return;
        }
        
        var request = URLRequest(url: url)
        
        request.httpMethod = "GET"
        request.setValue(getToken(), forHTTPHeaderField: "authentication")
        
        
        Alamofire.request(request).responseJSON { (res) in
            completion(res)
        }
        
    }
    
    static func readMessages(completion : @escaping (DataResponse<Any>)->())-> Void{
        
        guard let url = URL(string:K.REST_MESSAGE_READ) else{
            return;
        }
        
        var request = URLRequest(url: url)
        
        request.httpMethod = "POST"
        request.setValue(getToken(), forHTTPHeaderField: "authentication")
        
        
        Alamofire.request(request).responseJSON { (res) in
            completion(res)
        }
        
    }

    static func getTripGeoLocation(_ tripId : Int , completion : @escaping (DataResponse<Any>)->())-> Void{

        guard let url = URL(string:K.REST_TRIP_GEO_LOC + String(tripId)) else{
            return;
        }
        
        var request = URLRequest(url: url)
        
        request.httpMethod = "GET"
        request.setValue(getToken(), forHTTPHeaderField: "authentication")
        
        
        Alamofire.request(request).responseJSON { (res) in
            completion(res)
        }

    }
    
    static func sendTripTrack(_ dto : TripTrackInfo, completion : @escaping (DataResponse<Any>)->())-> Void{
        
        guard let url = URL(string:K.REST_TRIP_TRACKER) else{
            return;
        }
        
        var request = URLRequest(url: url)
        
        request.httpMethod = "POST"
        var postData = ""
        do {
            let jsonData = try JSONEncoder().encode(dto)
            postData = String(data: jsonData , encoding: .utf8) ?? ""
        } catch let err {
            print(err.localizedDescription)
        }
        
        request.httpBody = postData.data(using: .utf8)
        request.setValue(getToken(), forHTTPHeaderField: "authentication")
        request.addValue("application/json", forHTTPHeaderField: "Content-Type")
        
        
        Alamofire.request(request).responseJSON { (res) in
            completion(res)
        }
        
    }

    static func getStatistic(_ rangeFrom : String, _ rangeTo : String , completion : @escaping (DataResponse<Any>)->()) {
        
        guard let url = URL(string:K.REST_STATISTC_URL + rangeFrom + "/" + rangeTo) else{
            return;
        }
        
        var request = URLRequest(url: url)
        
        request.httpMethod = "GET"
        request.setValue(getToken(), forHTTPHeaderField: "authentication")
        
        
        Alamofire.request(request).responseJSON { (res) in
            completion(res)
        }
    }
    
    static func listMedals(completion : @escaping (DataResponse<Any>)->())-> Void{
        
        guard let url = URL(string:K.REST_LIST_MEDALS) else{
            return;
        }
        
        var request = URLRequest(url: url)
        
        request.httpMethod = "GET"
        request.setValue(getToken(), forHTTPHeaderField: "authentication")
        
        
        Alamofire.request(request).responseJSON { (res) in
            completion(res)
        }
        
    }

    static func listRewards(completion : @escaping (DataResponse<Any>)->())-> Void{
        
        guard let url = URL(string:K.REST_LIST_REWARDS) else{
            return;
        }
        
        var request = URLRequest(url: url)
        
        request.httpMethod = "GET"
        request.setValue(getToken(), forHTTPHeaderField: "authentication")
        
        
        Alamofire.request(request).responseJSON { (res) in
            completion(res)
        }
        
    }
    
    static func enterReward(_ rewardId : Int64, completion : @escaping (DataResponse<Any>)->())-> Void{
        
        guard let url = URL(string:K.REST_REWARD_ENTER + String(rewardId)) else{
            return;
        }
        
        var request = URLRequest(url: url)
        
        request.httpMethod = "POST"
        request.setValue(getToken(), forHTTPHeaderField: "authentication")
        request.httpBody = "".data(using: .utf8)
        
        Alamofire.request(request).responseJSON { (res) in
            completion(res)
        }
        
    }
    
    static func createGroup(_ req : CreateGroupRequest, completion : @escaping (DataResponse<Any>)->())-> Void{
        
        guard let url = URL(string:K.REST_CREATE_GROUP) else{
            return;
        }
        
        var request = URLRequest(url: url)
        
        request.httpMethod = "POST"
        request.addValue(getToken(), forHTTPHeaderField: "authentication")
        request.addValue("application/json", forHTTPHeaderField: "Content-Type")
        
        var postData = ""
        
        do {
            let jsonData = try JSONEncoder().encode(req)
            postData = String(data: jsonData, encoding: .utf8) ?? ""
        } catch let err {
            print(err.localizedDescription)
        }
        
        request.httpBody = postData.data(using: .utf8)
        
        Alamofire.request(request).responseJSON { (res) in
            completion(res)
        }
        
    }

    static func getGroup(_ id : Int64, completion : @escaping (DataResponse<Any>)->())-> Void{
        
        guard let url = URL(string:K.REST_GET_GROUP + String(id)) else{
            return;
        }
        
        var request = URLRequest(url: url)
        
        request.httpMethod = "GET"
        request.setValue(getToken(), forHTTPHeaderField: "authentication")
        
        
        Alamofire.request(request).responseJSON { (res) in
            completion(res)
        }
        
    }
    
    static func listGroups(_ pageNum : Int64, completion : @escaping (DataResponse<Any>)->())-> Void{
        
        guard let url = URL(string:K.REST_LIST_GROUPS + String(pageNum)) else{
            return;
        }
        
        var request = URLRequest(url: url)
        
        request.httpMethod = "GET"
        request.setValue(getToken(), forHTTPHeaderField: "authentication")
        
        
        Alamofire.request(request).responseJSON { (res) in
            completion(res)
        }
        
    }

    static func deleteGroup(_ id : Int, completion : @escaping (DataResponse<Any>)->())-> Void{
        
        guard let url = URL(string:K.REST_DELETE_GROUP + String(id)) else{
            return;
        }
        
        var request = URLRequest(url: url)
        
        request.httpMethod = "DELETE"
        request.setValue(getToken(), forHTTPHeaderField: "authentication")
        request.httpBody = "{}".data(using: .utf8)
        
        
        Alamofire.request(request).responseJSON { (res) in
            completion(res)
        }
        
    }

    
    static func multipleInvire(_ req : MultipleInviteRequest, completion : @escaping (DataResponse<Any>)->())-> Void{
        
        guard let url = URL(string:K.REST_INVITATE_MULTIPLE ) else{
            return;
        }
        
        var request = URLRequest(url: url)
        
        request.httpMethod = "POST"
        request.setValue(getToken(), forHTTPHeaderField: "authentication")
        request.addValue("application/json", forHTTPHeaderField: "Content-Type")
        
        var postData = ""
        
        do {
            let json = try JSONEncoder().encode(req)
            postData = String(data: json , encoding : .utf8) ?? ""
        } catch let err {
            print(err.localizedDescription)
        }
        
        request.httpBody = postData.data(using: String.Encoding.utf8)
        
        Alamofire.request(request).responseJSON { (res) in
            completion(res)
        }
        
    }
    
    static func listInvitations(_ pageNum : Int64, completion : @escaping (DataResponse<Any>)->())-> Void{
        
        guard let url = URL(string:K.REST_LIST_INVITATIONS + String(pageNum)) else{
            return;
        }
        
        var request = URLRequest(url: url)
        
        request.httpMethod = "GET"
        request.setValue(getToken(), forHTTPHeaderField: "authentication")
        
        
        Alamofire.request(request).responseJSON { (res) in
            completion(res)
        }
        
    }

    static func acceptInvitations(_ invitationId : Int64, completion : @escaping (DataResponse<Any>)->())-> Void{
        
        guard let url = URL(string:K.REST_ACCEPT_INVITATION + "\(invitationId)") else{
            return;
        }
        
        var request = URLRequest(url: url)
        
        request.httpMethod = "POST"
        request.setValue(getToken(), forHTTPHeaderField: "authentication")
        request.addValue("application/json", forHTTPHeaderField: "Content-Type")
        
        let postData = "{}"
        
        request.httpBody = postData.data(using: .utf8)
        
        Alamofire.request(request).responseJSON { (res) in
            completion(res)
        }
        
    }
    static func removeMember(_ req : RemoveMemberRequest  , completion : @escaping (DataResponse<Any>)->())-> Void{
        
        guard let url = URL(string:K.REST_REMOVE_MEMEBER) else{
            return;
        }
        
        var request = URLRequest(url: url)
        
        request.httpMethod = "POST"
        //TODO : Add parameter/json
        request.setValue(getToken(), forHTTPHeaderField: "authentication")
        request.addValue("application/json", forHTTPHeaderField: "Content-Type")
        
        var postData = ""
        
        do {
            let jsondata = try JSONEncoder().encode(req)
            postData = String(data: jsondata, encoding: .utf8) ?? ""
        } catch let err {
            print(err.localizedDescription)
        }
        
        request.httpBody = postData.data(using: .utf8)
        
        Alamofire.request(request).responseJSON { (res) in
            completion(res)
        }
        
    }
    
    static func getUserProfile(completion : @escaping (DataResponse<Any>)->())-> Void{
        
        guard let url = URL(string:K.REST_GET_USER_PROFILE) else{
            return;
        }
        
        var request = URLRequest(url: url)
        
        request.httpMethod = "GET"
        request.setValue(getToken(), forHTTPHeaderField: "authentication")
        
        
        Alamofire.request(request).responseJSON { (res) in
            completion(res)
        }
        
    }

    static func updateUserProfile(_ entity : Profile  , completion : @escaping (DataResponse<Any>)->())-> Void{
        
        guard let url = URL(string:K.REST_GET_USER_PROFILE) else{
            return;
        }
        
        var request = URLRequest(url: url)
        
        request.httpMethod = "POST"
        request.addValue(getToken(), forHTTPHeaderField: "authentication")
        request.addValue("application/json", forHTTPHeaderField: "Content-Type")
        
        var postData = ""
        
        do {
            let jsonData = try JSONEncoder().encode(entity)
            postData = String(data: jsonData , encoding: .utf8) ?? ""
        } catch let err {
            print(err.localizedDescription)
        }
        
        request.httpBody = postData.data(using: .utf8)
        
        Alamofire.request(request).responseJSON { (res) in
            completion(res)
        }
        
    }

    static func getReminder(completion : @escaping (DataResponse<Any>)->())-> Void{
        
        guard let url = URL(string:K.REST_GET_REMINDER) else{
            return;
        }
        
        var request = URLRequest(url: url)
        
        request.httpMethod = "GET"
        request.addValue(getToken(), forHTTPHeaderField: "authentication")
//        request.addValue("application/json", forHTTPHeaderField: "Content-Type")
        
//        var postData = ""
//
//        do {
//            let jsonData = try JSONEncoder().encode(entity)
//            postData = String(data: jsonData , encoding: .utf8) ?? ""
//        } catch let err {
//            print(err.localizedDescription)
//        }
//
//        request.httpBody = postData.data(using: .utf8)
        
        Alamofire.request(request).responseJSON { (res) in
            completion(res)
        }
        
    }
    
    
    static func updateReminder(reminder : Reminder ,completion : @escaping (DataResponse<Any>)->())-> Void{
        
        guard let url = URL(string:K.REST_UPDATE_REMINDER) else{
            return;
        }
        
        var request = URLRequest(url: url)
        
        request.httpMethod = "POST"
        request.addValue(getToken(), forHTTPHeaderField: "authentication")
        request.addValue("application/json", forHTTPHeaderField: "Content-Type")
       
        var postData = ""
       
        do {
            let jsonData = try JSONEncoder().encode(reminder)
            postData = String(data: jsonData , encoding: .utf8) ?? ""
        } catch let err {
            print(err.localizedDescription)
        }
       
        request.httpBody = postData.data(using: .utf8)
        
        Alamofire.request(request).responseJSON { (res) in
            completion(res)
        }
        
    }
    
    static func deleteTrip(tripId : Int ,completion : @escaping (DataResponse<Any>)->())-> Void{
        
        guard let url = URL(string:K.REST_DELETE_TRIP + "\(tripId)") else{
            return;
        }
        
        var request = URLRequest(url: url)
        
        request.httpMethod = "DELETE"
        request.addValue(getToken(), forHTTPHeaderField: "authentication")
    
        Alamofire.request(request).responseJSON { (res) in
            completion(res)
        }
        
    }
    
    static func getChilds(completion : @escaping (DataResponse<Any>)->())-> Void{
        
        guard let url = URL(string:K.REST_GET_CHILDS) else{
            return;
        }
        
        var request = URLRequest(url: url)
        
        request.httpMethod = "GET"
        request.addValue(getToken(), forHTTPHeaderField: "authentication")
        
        Alamofire.request(request).responseJSON { (res) in
            completion(res)
        }
        
    }
    
    static func getParents(completion : @escaping (DataResponse<Any>)->())-> Void{
        
        guard let url = URL(string:K.REST_GET_PARENTS) else{
            return;
        }
        
        var request = URLRequest(url: url)
        
        request.httpMethod = "GET"
        request.addValue(getToken(), forHTTPHeaderField: "authentication")
        
        Alamofire.request(request).responseJSON { (res) in
            completion(res)
        }
        
    }
    
    static func addParent(number : String ,completion : @escaping (DataResponse<Any>)->())-> Void{
        
        guard let url = URL(string:K.REST_ADDREMOVE_PARENT + number) else{
            return;
        }
        
        var request = URLRequest(url: url)
        
        request.httpMethod = "POST"
        request.addValue(getToken(), forHTTPHeaderField: "authentication")
        request.addValue("application/json", forHTTPHeaderField: "Content-Type")
        
        request.httpBody = "{:}".data(using: .utf8)
        
        Alamofire.request(request).responseJSON { (res) in
            completion(res)
        }
        
    }
    
    static func deleteParent(id : Int ,completion : @escaping (DataResponse<Any>)->())-> Void{
        
        guard let url = URL(string:K.REST_ADDREMOVE_PARENT + "\(id)") else{
            return;
        }
        
        var request = URLRequest(url: url)
        
        request.httpMethod = "DELETE"
        request.addValue(getToken(), forHTTPHeaderField: "authentication")
        
        Alamofire.request(request).responseJSON { (res) in
            completion(res)
        }
        
    }
}

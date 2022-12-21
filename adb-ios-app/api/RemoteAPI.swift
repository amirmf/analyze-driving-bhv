//
//  RemoteAPI.swift
//  driver-behavior-ios-app
//
//  Created by Amir Mansouri Fard (amf.fard@gmail.com) on 6/25/18.
//  Copyright © 2018 Amir Mansouri Fard (amf.fard@gmail.com). All rights reserved.
//

import Foundation
import Alamofire

enum APIRouter: URLRequestConvertible {
    
    case user_login(username:String, password:String)
    case user_register(phoneNumber : String)
    case join_challenge
    case trip_of_state
    
    // MARK: - HTTPMethod
    private var method: HTTPMethod {
        switch self {
        case .user_login, .user_register, .join_challenge:
            return .post
        case .trip_of_state:
            return .get
        }
    }
    
    // MARK: - Path
    private var path: String {
        switch self {
        case .user_login:
            return "user/login"
        case .user_register:
            return "user/register"
        case .join_challenge:
            return ""
        case .trip_of_state:
            return ""
        }
    }
    
    // MARK: - Parameters
    private var parameters: Parameters? {
        switch self {
        case .user_login:
            return [:]
        default :
            return [:]
        }
    }
    
    // MARK: - URLRequestConvertible
    func asURLRequest() throws -> URLRequest {
        
        let url = try K.baseUrl.asURL()
        
        var urlRequest = URLRequest(url: url.appendingPathComponent(path))
        
        urlRequest.httpMethod = method.rawValue
        
        urlRequest.setValue(ContentType.json.rawValue, forHTTPHeaderField: HTTPHeaderField.acceptType.rawValue)
        urlRequest.setValue(ContentType.json.rawValue, forHTTPHeaderField: HTTPHeaderField.contentType.rawValue)
        
        if let parameters = parameters {
            do {
                urlRequest.httpBody = try JSONSerialization.data(withJSONObject: parameters, options: [])
            } catch {
                throw AFError.parameterEncodingFailed(reason: .jsonEncodingFailed(error: error))
            }
        }
        
        return urlRequest
    }
}



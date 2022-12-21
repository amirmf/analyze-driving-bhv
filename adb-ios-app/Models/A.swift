//
//  A.swift
//  driver-behavior-ios-app
//
//  Created by Amir Mansouri Fard (amf.fard@gmail.com) on 7/26/18.
//  Copyright © 2018 Amir Mansouri Fard (amf.fard@gmail.com). All rights reserved.
//

import Foundation
import UIKit

/// BinMan1 : A Variable for save not read messages count
var NOTREADCOUNT = 0


/// BinMan1 : A function for make a loading in a super view and start loading in some postions
enum PositionsOfLoading { // use for set position of loading
    case bottom
    case center
    case top
}


enum RangeMode {
    case day
    case week
    case month
    case year
}

/// BinMan1 : Show loading function in all around project
func ShowLoading(superView : UIView  , position : PositionsOfLoading , size : CGFloat , afterLoading : (_ loading : UIActivityIndicatorView) -> ()) {
    var loading : UIActivityIndicatorView!
    
    switch position {
    case .top:
        loading = UIActivityIndicatorView(frame: CGRect(x: superView.width/2 - (size/2), y: 50, width: size, height: size))
        break
    case .center:
        loading = UIActivityIndicatorView(frame: CGRect(x: superView.width/2 - (size/2), y: superView.height/2 - (size/2), width: size, height: size))
        break
    case .bottom:
        loading = UIActivityIndicatorView(frame: CGRect(x: superView.width/2 - (size/2), y: superView.height - size - 10, width: size, height: size))
        break
    }
    loading.color = UIColor.blue
    loading.hidesWhenStopped = true
    superView.addSubview(loading)
    loading.startAnimating()
    afterLoading(loading)
}



/// BinMan1: A Method for get UUID String code
func getDeviceCode() -> String {
    return UIDevice.current.identifierForVendor!.uuidString
}

/// BinMan1 : A method for convert date
func convertDate(dateString : String) -> String {
    let formatter = DateFormatter()
    formatter.calendar = Calendar(identifier: .gregorian)
    formatter.dateFormat = "yyyy-MM-dd hh:mm:ss"
    let date = formatter.date(from: dateString)
    formatter.calendar = Calendar(identifier: .persian)
    return formatter.string(from: date ?? Date()).replacingOccurrences(of: "PM", with: "ب.ظ").replacingOccurrences(of: "AM", with: "ق.ظ")
}

func convertDateToGregorian(dateString : String) -> String {
    let formatter = DateFormatter()
    formatter.calendar = Calendar(identifier: .persian)
    formatter.dateFormat = "yyyy-MM-dd hh:mm:ss"
    let date = formatter.date(from: dateString)
    formatter.calendar = Calendar(identifier: .gregorian)
    return formatter.string(from: date ?? Date())
}

func secondsToHoursMinutesSeconds (seconds : Int) -> (Int, Int, Int) {
    return (seconds / 3600, (seconds % 3600) / 60, (seconds % 3600) % 60)
}

func getRange(mode : RangeMode) -> String {
    var result = ""
    let formatter = DateFormatter()
    formatter.calendar = Calendar(identifier: .gregorian)
    formatter.dateFormat = "yyyy-MM-dd hh:mm:ss"
    switch mode {
    case .day:
        result = formatter.string(from: Date())
        break
    case .week:
        let calendar = Calendar.current.date(byAdding: .day, value: -7, to: Date())
        result = formatter.string(from: calendar!)
        break
    case .month:
        let calendar = Calendar.current.date(byAdding: .month, value: -1, to: Date())
        result = formatter.string(from: calendar!)
        break
    case .year:
        let calendar = Calendar.current.date(byAdding: .year, value: -1, to: Date())
        result = formatter.string(from: calendar!)
        break
    }
    
    return result
}


func showNothingLabel (context : UIViewController , text : String) -> UILabel {
    let label = UILabel(frame: CGRect(x: 8, y: context.view.height/2 - 20, width: context.view.width - 16, height: 40))
    label.font = UIFont(name: "IRANSans(FaNum)", size: 15)
    label.textColor = .gray
    label.textAlignment = .center
    label.text = text
    return label
}

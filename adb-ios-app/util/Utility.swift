//
//  Utility.swift
//  driver-behavior-ios-app
//
//  Created by Amir Mansouri Fard (amf.fard@gmail.com) on 6/20/18.
//  Copyright © 2018 Amir Mansouri Fard (amf.fard@gmail.com). All rights reserved.
//

import Foundation
import UIKit

class Utility {
    
    static let min_font_size = 11;
    static let max_font_size = 48;
    static let font_size : Int = 11;
    static let font_size_selected : Int = 12;
    static let font_family : String = "American Typewriter";
    
    static let lbl_setting = "پروفایل من"
    static let img_setting = "mn_setting"

    static let lbl_start = "شروع"
    static let lbl_start_driving = "شروع رانندگی"

    static func doNothing(){
        
    }
    
    static func enable(_ control : UIControl){
        control.isEnabled = true;
        control.alpha = 1;
    }
    
    static func disable(_ control : UIControl){
        control.isEnabled = false;
        control.alpha = 0.6;
    }
    
    static func showMessage(_ label : UILabel , msg message:String){
        label.text = message;
        UIView.animate(withDuration: 0.25, animations: {
            label.isHidden = false;
        });
    }
    static func hideMessage(_ label : UILabel){
        UIView.animate(withDuration: 0.25, animations: {
            label.isHidden = true;
        });
    }
}

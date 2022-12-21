//
//  LanuchScreenViewController.swift
//  driver-behavior-ios-app
//
//  Created by Amir Mansouri Fard (amf.fard@gmail.com) on 6/18/18.
//  Copyright © 2018 Amir Mansouri Fard (amf.fard@gmail.com). All rights reserved.
//

import Foundation
import UIKit

class LaunchScreenViewController : UIViewController{

    
    
    func isAppAlreadyLaunchedOnce()-> Bool{
        let defaults = UserDefaults.standard;
        if let isAppAlreadyLaunchedOnce = defaults.string(forKey: "isAppAlreadyLaunchedOnce"){
            return true;
        }else{
            defaults.set(true,forKey:"isAppAlreadyLaunchedOnce");
            return false;
        }
    }
    
    
}

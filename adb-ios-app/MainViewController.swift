//
//  DefaultRevealController.swift
//  driver-behavior-ios-app
//
//  Created by Amir Mansouri Fard (amf.fard@gmail.com) on 6/29/18.
//  Copyright © 2018 Amir Mansouri Fard (amf.fard@gmail.com). All rights reserved.
//

import Foundation
import UIKit
import KYDrawerController

class MainViewController : KYDrawerController{

    static var singleton : MainViewController?

    
    required init?(coder aDecoder: NSCoder) {
        super.init(coder:aDecoder);
        MainViewController.singleton = self;
    }

    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.drawerDirection = .right
        
    }
}

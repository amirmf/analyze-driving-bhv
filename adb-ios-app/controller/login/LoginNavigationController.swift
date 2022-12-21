//
//  LoginNavigationController.swift
//  driver-behavior-ios-app
//
//  Created by Amir Mansouri Fard (amf.fard@gmail.com) on 7/12/18.
//  Copyright © 2018 Amir Mansouri Fard (amf.fard@gmail.com). All rights reserved.
//

import UIKit

class LoginNavigationController: UINavigationController {

    override func viewDidLoad() {
        super.viewDidLoad()
        self.navigationBar.topItem?.title = "حساب کاربری";
        self.navigationBar.topItem?.largeTitleDisplayMode = .always;
        
        self.navigationBar.isTranslucent = true;//?
        
        self.navigationBar.tintColor = UIColor.white;
        self.navigationBar.titleTextAttributes=[NSAttributedStringKey.foregroundColor:UIColor.white];
        
        // Do any additional setup after loading the view.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */

    // MARK : Actions
    
}

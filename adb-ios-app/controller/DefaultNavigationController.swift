//
//  DefaultNavigationController.swift
//  driver-behavior-ios-app
//
//  Created by Amir Mansouri Fard (amf.fard@gmail.com) on 6/26/18.
//  Copyright © 2018 Amir Mansouri Fard (amf.fard@gmail.com). All rights reserved.
//

import UIKit

class DefaultNavigationController: UINavigationController {

    static var singleton : DefaultNavigationController?

    override func viewDidLoad() {
        super.viewDidLoad()
        DefaultNavigationController.singleton = self;

        // Do any additional setup after loading the view.
        
//        UINavigationBar.appearance().tintColor = UIColor.white
//        UINavigationBar.appearance().barTintColor = K.color_p_lighter
//    UINavigationBar.appearance().titleTextAttributes=[NSAttributedStringKey.foregroundColor:UIColor.white]
        
        if let imgBar = UIImage(named: "mn_switch") {
            let customImgBarBtn = UIBarButtonItem(image: imgBar.withRenderingMode(.alwaysTemplate), style: .plain, target: self, action: nil)
            self.navigationItem.rightBarButtonItem  = customImgBarBtn
        }
        
    }
    func handleClick(sender: UIBarButtonItem){
        
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    override func viewWillAppear(_ animated: Bool) {
        navigationController?.navigationBar.barStyle = UIBarStyle.default
        navigationController?.navigationBar.tintColor = UIColor.black
    }

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */

}

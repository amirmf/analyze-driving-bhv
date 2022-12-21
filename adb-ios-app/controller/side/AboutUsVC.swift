//
//  AboutUsVC.swift
//  driver-behavior-ios-app
//
//  Created by Amir Mansouri Fard (amf.fard@gmail.com) on 7/31/18.
//  Copyright © 2018 Amir Mansouri Fard (amf.fard@gmail.com). All rights reserved.
//

import UIKit

class AboutUsVC: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        self.navigationItem.title = "درباره ما"
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

    
    @IBAction func btnsSelector(_ sender: UIButton) {
        var url : String!
        switch sender.tag {
        case 0:
            url = "tg://resolve?domain=Careno_app"
            break
        case 1:
            url = "http://instagram.com/_u/careno.app"
            break
        case 2:
            url = "mailto:careno.app@gmail.com"
            break
        case 3:
            url = "https://twitter.com"
            break
        default:
            break
        }
        
        
        let reqUrl = URL(string: url)
        if UIApplication.shared.canOpenURL(reqUrl!) {
            UIApplication.shared.open(reqUrl!, options: [:], completionHandler: nil)
        }
    }
}

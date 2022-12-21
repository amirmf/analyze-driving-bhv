//
//  RewardsVC.swift
//  driver-behavior-ios-app
//
//  Created by Amir Mansouri Fard (amf.fard@gmail.com) on 8/1/18.
//  Copyright © 2018 Amir Mansouri Fard (amf.fard@gmail.com). All rights reserved.
//

import UIKit
import XLPagerTabStrip

class RewardsVC: ButtonBarPagerTabStripViewController {

    static var singleton : RewardsVC!
    
    override func viewDidLoad() {
        self.navigationItem.title = "جوایز"
        self.settings.style.buttonBarItemBackgroundColor = UIColor(red: 79/255, green: 195/255, blue: 247/255, alpha: 1.0)
        self.settings.style.buttonBarBackgroundColor = UIColor(red: 79/255, green: 195/255, blue: 247/255, alpha: 1.0)
        self.settings.style.selectedBarHeight = 3
        self.settings.style.selectedBarBackgroundColor = .white
        self.settings.style.buttonBarMinimumLineSpacing = 0
        self.settings.style.buttonBarMinimumInteritemSpacing = 0
        
        super.viewDidLoad()

        RewardsVC.singleton = self
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    override func viewControllers(for pagerTabStripController: PagerTabStripViewController) -> [UIViewController] {
        let medals = self.storyboard?.instantiateViewController(withIdentifier: "MedalsTabVC") as! MedalsTabVC
        let rewards = self.storyboard?.instantiateViewController(withIdentifier: "RewardsTabVC") as! RewardsTabVC
        return [medals , rewards]
    }
}

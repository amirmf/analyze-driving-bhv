//
//  TripDetailsVC.swift
//  driver-behavior-ios-app
//
//  Created by Amir Mansouri Fard (amf.fard@gmail.com) on 7/28/18.
//  Copyright © 2018 Amir Mansouri Fard (amf.fard@gmail.com). All rights reserved.
//

import UIKit
import XLPagerTabStrip

class TripDetailsVC: ButtonBarPagerTabStripViewController {
    
    var tripID : Int!
    
    override func viewDidLoad() {
        self.settings.style.buttonBarItemBackgroundColor = UIColor(red: 79/255, green: 195/255, blue: 247/255, alpha: 1.0)
        self.settings.style.buttonBarBackgroundColor = UIColor(red: 79/255, green: 195/255, blue: 247/255, alpha: 1.0)
        self.settings.style.selectedBarHeight = 3
        self.settings.style.selectedBarBackgroundColor = .white
        self.settings.style.buttonBarMinimumLineSpacing = 0
        self.settings.style.buttonBarMinimumInteritemSpacing = 0
        super.viewDidLoad()
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    
    override func viewControllers(for pagerTabStripController: PagerTabStripViewController) -> [UIViewController] {
        var controllers = [UIViewController]()
        let summary = self.storyboard?.instantiateViewController(withIdentifier: "SummaryTripDetailVC") as! SummaryTripDetailVC
        let map = self.storyboard?.instantiateViewController(withIdentifier: "MapTripDetailVC") as! MapTripDetailVC
        summary.tripId = self.tripID
        map.tripId = self.tripID
        controllers.append(summary)
        controllers.append(map)
        
        return controllers
    }
}

//
//  StatisticViewController.swift
//  driver-behavior-ios-app
//
//  Created by Amir Mansouri Fard (amf.fard@gmail.com) on 6/28/18.
//  Copyright © 2018 Amir Mansouri Fard (amf.fard@gmail.com). All rights reserved.
//

import UIKit
import XLPagerTabStrip

class StatisticViewController: ButtonBarPagerTabStripViewController, DefaultBarViewController {

    override func viewDidLoad() {
        self.settings.style.buttonBarItemBackgroundColor = UIColor(red: 79/255, green: 195/255, blue: 247/255, alpha: 1.0)
        self.settings.style.buttonBarBackgroundColor = UIColor(red: 79/255, green: 195/255, blue: 247/255, alpha: 1.0)
        self.settings.style.selectedBarHeight = 3
        self.settings.style.selectedBarBackgroundColor = .white
        self.settings.style.buttonBarMinimumLineSpacing = 0
        self.settings.style.buttonBarMinimumInteritemSpacing = 0
        super.viewDidLoad()

        // Do any additional setup after loading the view.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    override func viewDidAppear(_ animated:Bool) {
        super.viewDidAppear(animated);
        self.navigationController?.navigationBar.topItem?.title = ""
        self.navigationController?.navigationBar.alpha = 1;
        
    }
    
    override func viewControllers(for pagerTabStripController: PagerTabStripViewController) -> [UIViewController] {
        let day = self.storyboard?.instantiateViewController(withIdentifier: "StatisticsDetailsVC") as! StatisticsDetailsVC
        day.tabTitle = "روز"
        day.fromRange = getRange(mode: .day).replacingOccurrences(of: " ", with: "%20")
        day.toRange = getRange(mode: .day).replacingOccurrences(of: " ", with: "%20")
        
        let week = self.storyboard?.instantiateViewController(withIdentifier: "StatisticsDetailsVC") as! StatisticsDetailsVC
        week.tabTitle = "هفته"
        week.fromRange = getRange(mode: .day).replacingOccurrences(of: " ", with: "%20")
        week.toRange = getRange(mode: .week).replacingOccurrences(of: " ", with: "%20")
        
        let month = self.storyboard?.instantiateViewController(withIdentifier: "StatisticsDetailsVC") as! StatisticsDetailsVC
        month.tabTitle = "ماه"
        month.fromRange = getRange(mode: .day).replacingOccurrences(of: " ", with: "%20")
        month.toRange = getRange(mode: .month).replacingOccurrences(of: " ", with: "%20")
        
        let year = self.storyboard?.instantiateViewController(withIdentifier: "StatisticsDetailsVC") as! StatisticsDetailsVC
        year.tabTitle = "سال"
        year.fromRange = getRange(mode: .day).replacingOccurrences(of: " ", with: "%20")
        year.toRange = getRange(mode: .year).replacingOccurrences(of: " ", with: "%20")
        return [year, month, week, day]
    }
}

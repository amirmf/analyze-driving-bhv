//
//  TeamsVC.swift
//  driver-behavior-ios-app
//
//  Created by Amir Mansouri Fard (amf.fard@gmail.com) on 8/5/18.
//  Copyright © 2018 Amir Mansouri Fard (amf.fard@gmail.com). All rights reserved.
//

import UIKit
import XLPagerTabStrip

class TeamsVC: ButtonBarPagerTabStripViewController {

    override func viewDidLoad() {
        self.settings.style.buttonBarItemBackgroundColor = UIColor(red: 79/255, green: 195/255, blue: 247/255, alpha: 1.0)
        self.settings.style.buttonBarBackgroundColor = UIColor(red: 79/255, green: 195/255, blue: 247/255, alpha: 1.0)
        self.settings.style.selectedBarHeight = 3
        self.settings.style.selectedBarBackgroundColor = .white
        self.settings.style.buttonBarMinimumLineSpacing = 0
        self.settings.style.buttonBarMinimumInteritemSpacing = 0
        super.viewDidLoad()

    }

    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        self.navigationController?.navigationBar.topItem?.title = "تیم ها"
        self.navigationController?.navigationBar.alpha = 1
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    override func viewControllers(for pagerTabStripController: PagerTabStripViewController) -> [UIViewController] {
        let team = self.storyboard?.instantiateViewController(withIdentifier: "TeamsListVC") as! TeamsListVC
        let invitation = self.storyboard?.instantiateViewController(withIdentifier: "InvitationsVC") as! InvitationsVC
        return [team, invitation]
    }
}

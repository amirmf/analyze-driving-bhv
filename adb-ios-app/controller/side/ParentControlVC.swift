//
//  ParentControlVC.swift
//  driver-behavior-ios-app
//
//  Created by Amir Mansouri Fard (amf.fard@gmail.com) on 9/10/18.
//  Copyright © 2018 Amir Mansouri Fard (amf.fard@gmail.com). All rights reserved.
//

import UIKit
import XLPagerTabStrip

class ParentControlVC: ButtonBarPagerTabStripViewController {

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

    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        self.navigationController?.navigationBar.topItem?.title = "کنترل والدین"
        self.navigationController?.navigationBar.alpha = 1
    }
    

    override func viewControllers(for pagerTabStripController: PagerTabStripViewController) -> [UIViewController] {
        let parents = self.storyboard?.instantiateViewController(withIdentifier: "ParentsListVC") as! ParentsListVC
        let childs = self.storyboard?.instantiateViewController(withIdentifier: "ChildsListVC") as! ChildsListVC
        return [childs, parents]
    }

}

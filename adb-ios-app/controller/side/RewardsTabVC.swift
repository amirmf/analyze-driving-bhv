//
//  RewardsTabVC.swift
//  driver-behavior-ios-app
//
//  Created by Amir Mansouri Fard (amf.fard@gmail.com) on 8/1/18.
//  Copyright © 2018 Amir Mansouri Fard (amf.fard@gmail.com). All rights reserved.
//

import UIKit
import XLPagerTabStrip

class RewardsTabVC: UIViewController,  IndicatorInfoProvider {

    static var singleton : RewardsTabVC!
    var nothgin : UILabel!
    @IBOutlet weak var table: UITableView!
    
    let selfController = RewardsTabC()
    override func viewDidLoad() {
        super.viewDidLoad()
        RewardsTabVC.singleton = self
        self.table.estimatedRowHeight = 50
        self.table.rowHeight = UITableViewAutomaticDimension
        self.table.separatorStyle = .none
        selfController.getRewards(context: self)
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func indicatorInfo(for pagerTabStripController: PagerTabStripViewController) -> IndicatorInfo {
        return IndicatorInfo(title: "جوایز")
    }
}

extension RewardsTabVC : UITableViewDelegate , UITableViewDataSource {
    func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if selfController.result == nil {
            return 0
        }
        
        return selfController.result.rewards.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "Cell", for: indexPath) as! RewardTabCell
        cell.configureCell(model: selfController.result.rewards[indexPath.row])
        cell.selectionStyle = .none
        return cell
    }
}

class RewardsTabC {
    var result : Rewards!
    func getRewards (context : RewardsTabVC) {
        ShowLoading(superView: context.view, position: .center, size: 40) { (loading) in
            RemoteService.listRewards(completion: { (response) in
                loading.stopAnimating()
                guard response.error == nil else {
                    print(response.error.debugDescription)
                    return
                }
                do {
                    self.result = try
                        JSONDecoder().decode(Rewards.self,from: response.data!)
                    
                    if self.result.rewards.isEmpty {
                        context.nothgin = showNothingLabel(context: context, text: "هنوز سفری ثبت نکرده اید")
                        context.view.addSubview(context.nothgin)
                    } else {
                        if context.nothgin != nil {
                            context.nothgin.removeFromSuperview()
                            context.nothgin = nil
                        }
                    }
                    context.table.reloadData()
                } catch let err {
                    print(err.localizedDescription)
                }
            })
        }
    }
}

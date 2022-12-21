//
//  InvitationsVC.swift
//  driver-behavior-ios-app
//
//  Created by Amir Mansouri Fard (amf.fard@gmail.com) on 8/5/18.
//  Copyright © 2018 Amir Mansouri Fard (amf.fard@gmail.com). All rights reserved.
//

import UIKit
import XLPagerTabStrip

class InvitationsVC: UIViewController , IndicatorInfoProvider {

    static var singleton : InvitationsVC?
    
    @IBOutlet weak var table : UITableView!
    var nothgin : UILabel!
    
    let selfController = InvitationsC()
    
    var paginationNumber = 0
    
    override func viewDidLoad() {
        super.viewDidLoad()

        InvitationsVC.singleton = self
        
        table.estimatedRowHeight = 40
        table.rowHeight = UITableViewAutomaticDimension
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        self.paginationNumber = 0
        selfController.result = nil
        selfController.getInvitations(context: self)
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    

    func indicatorInfo(for pagerTabStripController: PagerTabStripViewController) -> IndicatorInfo {
        return IndicatorInfo(title: "دعوت نامه ها")
    }
}

extension InvitationsVC : UITableViewDelegate, UITableViewDataSource {
    func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if selfController.result == nil {
            return 0
        }
        
        return selfController.result.items.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "Cell", for: indexPath) as! InvitationCell
        cell.configureCell(model: selfController.result.items[indexPath.row])
        cell.selectionStyle = .none
        return cell
    }
}

extension InvitationsVC : UIScrollViewDelegate {
    func scrollViewDidEndDragging(_ scrollView: UIScrollView, willDecelerate decelerate: Bool) {
        // UITableView only moves in one direction, y axis
        let currentOffset = scrollView.contentOffset.y
        let maximumOffset = scrollView.contentSize.height - scrollView.frame.size.height
        
        // Change 10.0 to adjust the distance from bottom
        if maximumOffset - currentOffset <= 10.0 {
            paginationNumber += 1
            selfController.getInvitations(context: self)
        }
    }
}

class InvitationsC {
    
    var result : GroupInvitations!
    func getInvitations(context : InvitationsVC) {
        ShowLoading(superView: context.view, position: .center, size: 40) { (loading) in
            RemoteService.listInvitations(Int64(context.paginationNumber), completion: { (response) in
                loading.stopAnimating()
                guard response.error == nil else {
                    print(response.error.debugDescription)
                    return
                }
                
                do {
                    if self.result == nil {
                        self.result = try JSONDecoder().decode(GroupInvitations.self , from : response.data!)
                    } else {
                        let res = try JSONDecoder().decode(GroupInvitations.self, from: response.data!)
                        if !res.items.isEmpty {
                            self.result.items.append(contentsOf: res.items)
                        }
                    }
                    
                    if self.result.items.isEmpty {
                        context.nothgin = showNothingLabel(context: context, text: "موردی یافت نشد")
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

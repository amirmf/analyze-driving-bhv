//
//  TeamsListVC.swift
//  driver-behavior-ios-app
//
//  Created by Amir Mansouri Fard (amf.fard@gmail.com) on 8/5/18.
//  Copyright © 2018 Amir Mansouri Fard (amf.fard@gmail.com). All rights reserved.
//

import UIKit
import XLPagerTabStrip

class TeamsListVC: UIViewController , IndicatorInfoProvider {

    static var singleton : TeamsListVC?
    
    @IBOutlet weak var table : UITableView!
    var nothgin : UILabel!
    
    let selfController = TeamsListC()
    
    var paginationNumber = 0
    
    override func viewDidLoad() {
        super.viewDidLoad()

        TeamsListVC.singleton = self
        
        table.estimatedRowHeight = 40
        table.rowHeight = UITableViewAutomaticDimension
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    

    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        self.paginationNumber = 0
        selfController.result = nil
        selfController.getTeamsList(context: self)
    }
    
    func indicatorInfo(for pagerTabStripController: PagerTabStripViewController) -> IndicatorInfo {
        return IndicatorInfo(title: "تیم ها")
    }

    @IBAction func createTeamSelector(_ sender: RadiusButton) {
        let alert = UIAlertController(title: "ساخت گروه", message: "", preferredStyle: UIAlertControllerStyle.alert)
        
        var titleTF: UITextField!
        var descTF : UITextField!
        
        alert.addTextField { (tf) in
            tf.font = UIFont(name: "IRANSans(FaNum)", size: 15)
            tf.placeholder = "لطفا یک عنوان وارد کنید"
            tf.textAlignment = .right
            titleTF = tf
        }
        
        alert.addTextField { (tf) in
            tf.font = UIFont(name: "IRANSans(FaNum)", size: 15)
            tf.placeholder = "توضیحات"
            tf.textAlignment = .right
            descTF = tf
        }
        
        alert.addAction(UIAlertAction(title: "انصراف", style: .default, handler: { (action) in
            alert.dismiss(animated: true, completion: nil)
        }))
        
        alert.addAction(UIAlertAction(title: "ثبت", style: .default, handler: { (action) in
            alert.dismiss(animated: true, completion: nil)
            ShowLoading(superView: self.view, position: .center, size: 40, afterLoading: { (loading) in
                RemoteService.createGroup(CreateGroupRequest(name: titleTF.text ?? "", description: descTF.text ?? ""), completion: { (response) in
                    loading.stopAnimating()
                    if response.response != nil && response.response!.statusCode == 200 {
                        self.selfController.getTeamsList(context: self)
                    } else {
                        let alert2 = UIAlertController(title: "خطا", message: "عملیات با خطا مواجه شد", preferredStyle: .actionSheet)
                        alert2.addAction(UIAlertAction(title: "باشه", style: .default, handler: { (action) in
                            alert2.dismiss(animated: true, completion: nil)
                        }))
                        
                        self.present(alert2, animated: true, completion: nil)
                    }
                })
            })
        }))
        
        self.present(alert, animated: true, completion: nil)
    }
}

extension TeamsListVC : UITableViewDelegate, UITableViewDataSource {
    func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if selfController.result == nil {
            return 0
        }
        
        return Int(selfController.result.groupDrivings?.count ?? 0)
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "Cell", for: indexPath) as! GroupCell
        cell.configureCell(model: selfController.result.groupDrivings![indexPath.row])
        return cell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        tableView.deselectRow(at: indexPath, animated: true)
        let vc = self.storyboard?.instantiateViewController(withIdentifier: "MemberListVC") as! MemberListVC
        vc.groupId = selfController.result.groupDrivings![indexPath.row].id ?? -1
        self.navigationController?.pushViewController(vc, animated: true)
    }
}

extension TeamsListVC : UIScrollViewDelegate {
    func scrollViewDidEndDragging(_ scrollView: UIScrollView, willDecelerate decelerate: Bool) {
        // UITableView only moves in one direction, y axis
        let currentOffset = scrollView.contentOffset.y
        let maximumOffset = scrollView.contentSize.height - scrollView.frame.size.height
        
        // Change 10.0 to adjust the distance from bottom
        if maximumOffset - currentOffset <= 10.0 {
            paginationNumber += 1
            selfController.getTeamsList(context: self)
        }
    }
}

class TeamsListC {
    var result : GroupsList!
    func getTeamsList(context : TeamsListVC) {
        ShowLoading(superView: context.view, position: .center, size: 40) { (loading) in
            RemoteService.listGroups(Int64(context.paginationNumber), completion: { (response) in
                loading.stopAnimating()
                guard response.error == nil else {
                    print(response.error.debugDescription)
                    return
                }
                
                do {
                    context.table.reloadData()
                    
                    if self.result == nil {
                        self.result = try JSONDecoder().decode(GroupsList.self , from : response.data!)
                    } else {
                        let res = try JSONDecoder().decode(GroupsList.self , from : response.data!)
                        if res.groupDrivings != nil && !res.groupDrivings!.isEmpty {
                            self.result.groupDrivings!.append(contentsOf: res.groupDrivings!)
                        }
                    }
                    
                    if self.result.groupDrivings == nil || self.result.groupDrivings!.isEmpty {
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


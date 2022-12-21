//
//  MemberCell.swift
//  driver-behavior-ios-app
//
//  Created by Amir Mansouri Fard (amf.fard@gmail.com) on 8/11/18.
//  Copyright © 2018 Amir Mansouri Fard (amf.fard@gmail.com). All rights reserved.
//

import UIKit

class MemberCell: UITableViewCell {

    @IBOutlet weak var memberName : UILabel!
    @IBOutlet weak var memberPoint : UILabel!
    @IBOutlet weak var deleteBtn : UIButton!
    
    var model : UserGroupMember!
    var parentModel : Parent!
    
    func configureCell(model : UserGroupMember) {
        self.model = model
        self.memberName.text = model.firstName + " " + model.lastName
        self.memberPoint.text = "\(model.score) امتیاز"
    }
    
    func configureCell(model : Parent) {
        self.parentModel = model
        self.memberName.text = model.name
        self.memberPoint.text = model.score != nil ? "\(model.score!) امتیاز" : ""
    }
    
    @IBAction func deleteSelector(sender : UIButton) {
        if parentModel == nil {
            ShowLoading(superView: MemberListVC.singleton.view, position: .center, size: 40) { (loading) in
                RemoteService.removeMember(RemoveMemberRequest(groupId: MemberListVC.singleton.groupId, memberId: model.id), completion: { (response) in
                    loading.stopAnimating()
                    if response.response != nil && response.response!.statusCode == 200 {
                        MemberListVC.singleton.selfController.getMembers(context: MemberListVC.singleton)
                    } else {
                        let alert = UIAlertController(title: "خطا", message: "خطایی رخ داده است. لطفا مجددا تلاش کنید", preferredStyle: UIAlertControllerStyle.alert)
                        alert.addAction(UIAlertAction(title: "باشه", style: .default, handler: { (action) in
                            alert.dismiss(animated: true, completion: nil)
                        }))
                        
                        MemberListVC.singleton.present(alert, animated: true, completion: nil)
                    }
                })
            }
        } else {
            ShowLoading(superView: ParentsListVC.singleton.view, position: .center, size: 40) { (loading) in
                RemoteService.deleteParent(id: Int(parentModel.id), completion: { (response) in
                    loading.stopAnimating()
                    if response.response != nil && response.response!.statusCode == 200 {
                        ParentsListVC.singleton.selfController.getParents(context: ParentsListVC.singleton)
                    }
                })
            }
        }
    }
}

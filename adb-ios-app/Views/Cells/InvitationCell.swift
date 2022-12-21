//
//  InvitationCell.swift
//  driver-behavior-ios-app
//
//  Created by Amir Mansouri Fard (amf.fard@gmail.com) on 8/5/18.
//  Copyright © 2018 Amir Mansouri Fard (amf.fard@gmail.com). All rights reserved.
//

import UIKit

class InvitationCell: UITableViewCell {
    @IBOutlet weak var titleLBL: UILabel!
    @IBOutlet weak var descLBL: UILabel!
    @IBOutlet weak var nameLBL: UILabel!
    @IBOutlet weak var mobileLBL: UILabel!
    
    var model : GroupInvitation!
    
    func configureCell(model : GroupInvitation) {
        self.model = model
        titleLBL.text = model.groupName
        descLBL.text = model.groupDescription
        nameLBL.text = model.ownerName
        mobileLBL.text = model.ownerPhoneNumber
    }

    @IBAction func acceptSelector(_ sender: RadiusButton) {
        ShowLoading(superView: InvitationsVC.singleton!.view, position: .center, size: 40) { (loading) in
            RemoteService.acceptInvitations(model.inviteId, completion: { (response) in
                loading.stopAnimating()
                guard response.error == nil else {
                    print(response.error.debugDescription)
                    return
                }
                
                InvitationsVC.singleton!.paginationNumber = 0
                InvitationsVC.singleton!.selfController.result = nil
                InvitationsVC.singleton!.selfController.getInvitations(context: InvitationsVC.singleton!)
            })
        }
    }
    
    @IBAction func rejectSleector(_ sender: RadiusButton) {
        let alert = UIAlertController(title: "", message: "آیا می خواهید این درخواست را رد کنید؟", preferredStyle: .alert)
        alert.addAction(UIAlertAction(title: "بله", style: UIAlertActionStyle.default, handler: { (action) in
            alert.dismiss(animated: true, completion: nil)
            InvitationsVC.singleton!.table.reloadData()
        }))
        
        alert.addAction(UIAlertAction(title: "خیر", style: .default, handler: { (action) in
            alert.dismiss(animated: true, completion: nil)
        }))
        
        InvitationsVC.singleton?.present(alert, animated: true, completion: nil)
    }
}

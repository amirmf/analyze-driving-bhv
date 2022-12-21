//
//  GroupCell.swift
//  driver-behavior-ios-app
//
//  Created by Amir Mansouri Fard (amf.fard@gmail.com) on 8/9/18.
//  Copyright © 2018 Amir Mansouri Fard (amf.fard@gmail.com). All rights reserved.
//

import UIKit

class GroupCell: UITableViewCell {
    @IBOutlet weak var groupNameLBL: UILabel!
    @IBOutlet weak var adminNameLBL: UILabel!
    @IBOutlet weak var descLBL: UILabel!
    
    func configureCell(model : DrivingGroup) {
        self.groupNameLBL.text = "نام گروه : " + (model.name ?? "")
        self.adminNameLBL.text = "مدیر گروه : " + (model.ownerName ?? "")
        self.descLBL.text = "توضیحات : " + (model.description ?? "")
    }
}

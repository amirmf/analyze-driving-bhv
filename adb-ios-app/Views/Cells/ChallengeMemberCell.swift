//
//  ChallengeMemberCell.swift
//  driver-behavior-ios-app
//
//  Created by Amir Mansouri Fard (amf.fard@gmail.com) on 7/27/18.
//  Copyright © 2018 Amir Mansouri Fard (amf.fard@gmail.com). All rights reserved.
//

import UIKit

class ChallengeMemberCell: UITableViewCell {

    @IBOutlet weak var memberNameLBL : UILabel!
    @IBOutlet weak var pointLBL : UILabel!
    
    func configureCell(model: ChallengeMember) {
        self.memberNameLBL.text = model.name
        self.pointLBL.text = "امتیاز \(Int(model.score))"
    }

}

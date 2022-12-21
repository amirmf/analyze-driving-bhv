//
//  MessageCell.swift
//  driver-behavior-ios-app
//
//  Created by Amir Mansouri Fard (amf.fard@gmail.com) on 9/10/18.
//  Copyright © 2018 Amir Mansouri Fard (amf.fard@gmail.com). All rights reserved.
//

import UIKit

class MessageCell: UITableViewCell {
    @IBOutlet weak var dateLBL : UILabel!
    @IBOutlet weak var messageLBL : UILabel!
    
    func configureCell(model: Message) {
        self.messageLBL.text = model.text
        self.dateLBL.text = convertDate(dateString: model.date).replacingOccurrences(of: "-", with: "/")
    }
}

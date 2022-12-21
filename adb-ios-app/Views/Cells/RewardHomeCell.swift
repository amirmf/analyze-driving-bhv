//
//  RewardHomeCell.swift
//  driver-behavior-ios-app
//
//  Created by Amir Mansouri Fard (amf.fard@gmail.com) on 7/29/18.
//  Copyright © 2018 Amir Mansouri Fard (amf.fard@gmail.com). All rights reserved.
//

import UIKit

class RewardHomeCell: UICollectionViewCell {
    @IBOutlet weak var thumbIMG: UIImageView!
    @IBOutlet weak var countLBL: UILabel!
    @IBOutlet weak var nameLBL: UILabel!
    
    
    func configureCell(model: Medal) {
        self.countLBL.text = "\(model.count)"
        self.nameLBL.text = model.title
        self.thumbIMG.kf.setImage(with: URL(string: model.icon))
    }
}

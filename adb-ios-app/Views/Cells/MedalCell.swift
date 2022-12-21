//
//  MedalCell.swift
//  driver-behavior-ios-app
//
//  Created by Amir Mansouri Fard (amf.fard@gmail.com) on 8/1/18.
//  Copyright © 2018 Amir Mansouri Fard (amf.fard@gmail.com). All rights reserved.
//

import UIKit

class MedalCell: UICollectionViewCell {
    @IBOutlet weak var thumbIMG: UIImageView!
    @IBOutlet weak var countLBL: UILabel!
    @IBOutlet weak var titleLBL: UILabel!
    
    
    func configureCell(model : Medal) {
        self.thumbIMG.kf.setImage(with: URL(string: model.icon))
        self.titleLBL.text = model.title
        self.countLBL.text = "\(model.count)"
    }
}

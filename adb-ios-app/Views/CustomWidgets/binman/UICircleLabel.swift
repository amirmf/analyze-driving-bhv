//
//  UIRadiusLabel.swift
//  Teztop
//
//  Created by Amir Mansouri Fard (amf.fard@gmail.com) on 6/15/18.
//  Copyright © 2018 Amir Mansouri Fard (amf.fard@gmail.com). All rights reserved.
//

import UIKit

class UICircleLabel: UILabel {

    override func layoutSubviews() {
        super.layoutSubviews()
        self.layer.cornerRadius = self.width/2
        self.layer.masksToBounds = true
    }

}

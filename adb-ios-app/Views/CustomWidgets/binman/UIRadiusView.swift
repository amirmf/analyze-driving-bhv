//
//  UIRadiusView.swift
//  Teztop
//
//  Created by Amir Mansouri Fard (amf.fard@gmail.com) on 6/16/18.
//  Copyright © 2018 Amir Mansouri Fard (amf.fard@gmail.com). All rights reserved.
//

import UIKit

class UIRadiusView: UIView {

    @IBInspectable var radiusSize : CGFloat {
        set {
            self.layer.cornerRadius = newValue
        }
        
        get {
            return self.layer.cornerRadius
        }
    }
    
    
    @IBInspectable var borderColor : UIColor {
        set {
            self.layer.borderColor = newValue.cgColor
        }
        
        get {
            return UIColor()
        }
    }
    
    @IBInspectable var borderWidth : CGFloat {
        set {
            self.layer.borderWidth = newValue
        }
        
        get {
            return self.layer.borderWidth
        }
    }

}

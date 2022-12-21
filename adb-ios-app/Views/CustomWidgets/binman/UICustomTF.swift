//
//  UICustomTF.swift
//  Teztop
//
//  Created by Amir Mansouri Fard (amf.fard@gmail.com) on 6/6/18.
//  Copyright © 2018 Amir Mansouri Fard (amf.fard@gmail.com). All rights reserved.
//

import UIKit

class UICustomTF: UITextField {
    
    let offColor = UIColor(red: 219/255, green: 219/255, blue: 219/255, alpha: 1.0)
    let onColor = UIColor(red: 35/255, green: 150/255, blue: 241/255, alpha: 1.0)
    var underLine : UIView!
    
    override func layoutSubviews() {
        super.layoutSubviews()
        makeCustom()
    }
    
    private func makeCustom() {
        underLine = UIView(frame: CGRect(x: 0, y: self.height-1.5, width: self.width, height: 1.5))
        underLine.backgroundColor = offColor
        self.addSubview(underLine)
    }
    
    override func editingRect(forBounds bounds: CGRect) -> CGRect {
        if self.underLine != nil {
            self.underLine.backgroundColor = onColor
        }
        return bounds
    }
    
    override func endEditing(_ force: Bool) -> Bool {
        if force {
            self.underLine.backgroundColor = offColor
        }
        return force
    }
}

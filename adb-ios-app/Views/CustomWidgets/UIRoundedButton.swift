//
//  RoundedButton.swift
//  driver-behavior-ios-app
//
//  Created by Amir Mansouri Fard (amf.fard@gmail.com) on 7/5/18.
//  Copyright © 2018 Amir Mansouri Fard (amf.fard@gmail.com). All rights reserved.
//

import Foundation
import UIKit

@IBDesignable
class UIRoundedButton : UIButton{
    
    @IBInspectable
    var cornerRadious : CGFloat = 15{
        didSet{
            refreshCorners(value: cornerRadious);
        }
    }
    
    override init(frame:CGRect){
        super.init(frame:frame);
        self.customInit();
    }
    
    required init?(coder aDecoder : NSCoder){
        super.init(coder:aDecoder);
        self.customInit();
    }
    
    override func prepareForInterfaceBuilder() {
        self.customInit();
    }
    
    func customInit(){
        self.refreshCorners(value: cornerRadious);
    }
    
    func refreshCorners(value : CGFloat){
//        self.layer.cornerRadius = value;
        self.layer.cornerRadius = 0.5 * self.frame.size.height;
    }
}

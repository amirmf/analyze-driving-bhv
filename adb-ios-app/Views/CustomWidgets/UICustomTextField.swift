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
class UICustomTextField : UITextField{
    
    @IBInspectable
    var topInset: CGFloat = 10.0;
    @IBInspectable
    var bottomInset: CGFloat = 10.0;
    @IBInspectable
    var leftInset: CGFloat = 10.0;
    @IBInspectable
    var rightInset: CGFloat = 10.0;

    override init(frame:CGRect){
        super.init(frame:frame);
        customInit();
    }
    
    required init?(coder aDecoder : NSCoder){
        super.init(coder:aDecoder);
        customInit();
    }
    
    override func drawText(in rect:CGRect){
        
        let insets = UIEdgeInsets.init(top:topInset,left:leftInset,bottom:bottomInset,right:rightInset);
        super.drawText(in: UIEdgeInsetsInsetRect(rect, insets));
        
    }
    
    override var intrinsicContentSize: CGSize{
        
        let size=super.intrinsicContentSize;
        return CGSize(width:size.width + leftInset + rightInset,height:size.height+topInset+bottomInset)
        
    }
    
    func customInit(){
        self.layer.borderWidth = 2.0
        self.layer.borderColor = K.color_gray_dark?.cgColor;
        self.layer.cornerRadius = 5;
    }
    
}


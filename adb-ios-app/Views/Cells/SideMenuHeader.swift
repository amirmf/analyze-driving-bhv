//
//  SideMenuHeader.swift
//  driver-behavior-ios-app
//
//  Created by Amir Mansouri Fard (amf.fard@gmail.com) on 6/23/18.
//  Copyright © 2018 Amir Mansouri Fard (amf.fard@gmail.com). All rights reserved.
//

import UIKit


class SideMenuHeader: UITableViewCell {
    
    //MARK : Properties
    @IBOutlet weak var lblTitle: UILabel!
    @IBOutlet weak var imgIcon: UIImageView!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
        self.addGradientBackground(firstColor: K.color_p_light!, secondColor: K.color_p_dark!)
    }
    
    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)
        
        // Configure the view for the selected state
    }
    
}

//class SideMenuHeader: UITableViewHeaderFooterView {
//
//    @IBOutlet weak var lblName: UILabel!
//    /*
//     // Only override draw() if you perform custom drawing.
//     // An empty implementation adversely affects performance during animation.
//     override func draw(_ rect: CGRect) {
//     // Drawing code
//     }
//     */
//
//}





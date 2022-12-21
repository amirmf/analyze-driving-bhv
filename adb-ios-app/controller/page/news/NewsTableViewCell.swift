//
//  NewsTableViewCell.swift
//  driver-behavior-ios-app
//
//  Created by Amir Mansouri Fard (amf.fard@gmail.com) on 7/24/18.
//  Copyright © 2018 Amir Mansouri Fard (amf.fard@gmail.com). All rights reserved.
//

import UIKit

class NewsTableViewCell: UITableViewCell {

 
    @IBOutlet weak var newsDetail: UILabel!
    @IBOutlet weak var newsDate: UILabel!
    
    @IBOutlet weak var newsTitle: UILabel!
    @IBOutlet weak var newsImg: UIImageView!
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }

}

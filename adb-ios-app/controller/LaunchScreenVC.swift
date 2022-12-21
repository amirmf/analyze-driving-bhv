//
//  LaunchScreenVC.swift
//  driver-behavior-ios-app
//
//  Created by Amir Mansouri Fard (amf.fard@gmail.com) on 7/26/18.
//  Copyright © 2018 Amir Mansouri Fard (amf.fard@gmail.com). All rights reserved.
//

import UIKit
import NVActivityIndicatorView

class LaunchScreenVC: UIViewController {

    @IBOutlet weak var thumbIMG: UIImageView!
    @IBOutlet weak var loading3: NVActivityIndicatorView!
    @IBOutlet weak var loading2: NVActivityIndicatorView!
    @IBOutlet weak var loading1: NVActivityIndicatorView!
    override func viewDidLoad() {
        super.viewDidLoad()

        loading1.startAnimating()
        DispatchQueue.main.asyncAfter(deadline: .now() + 0.2) {
            self.loading2.startAnimating()
        }
        
        DispatchQueue.main.asyncAfter(deadline: .now() + 0.4
        ) {
            self.loading3.startAnimating()
        }
        
        UIView.animate(withDuration: 1.0, animations: {
            self.thumbIMG.transform = CGAffineTransform(scaleX: 2, y: 2)
        }, completion: nil)
        
        DispatchQueue.main.asyncAfter(deadline: .now() + 4) {
            self.loading1.stopAnimating()
            self.loading2.stopAnimating()
            self.loading3.stopAnimating()
            let vc = self.storyboard?.instantiateViewController(withIdentifier: "rootView") as! RootViewController
            self.present(vc, animated: true, completion: nil)
        }
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

}

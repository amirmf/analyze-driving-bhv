//
//  DrivingViewController.swift
//  driver-behavior-ios-app
//
//  Created by Amir Mansouri Fard (amf.fard@gmail.com) on 6/28/18.
//  Copyright © 2018 Amir Mansouri Fard (amf.fard@gmail.com). All rights reserved.
//

import UIKit

class DrivingViewController: UIViewController, DefaultBarViewController {
    
    @IBOutlet weak var btnStartDrive: UIButton!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
    }
    
    override func viewDidAppear(_ animated:Bool) {
        super.viewDidAppear(animated);
        self.navigationController?.navigationBar.topItem?.title = "آماده رانندگی هستید؟";
        self.navigationController?.navigationBar.alpha = 1;
        animateBtnStartDrive();
    }
    
    override func viewDidDisappear(_ animated: Bool) {
        super.viewDidDisappear(animated)
    }
    
    override func viewDidLayoutSubviews() {
        super.viewDidLayoutSubviews()
        btnStartDrive.layer.cornerRadius = btnStartDrive.width/2
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func animateBtnStartDrive(){
        
        UIView.animate(withDuration: 1
            , delay: 1
            , options: [.repeat , .autoreverse]
            , animations:{
                self.btnStartDrive.transform = CGAffineTransform(scaleX: 1.1, y: 1.1)
                self.view.layoutIfNeeded()
        }
            , completion: {(flag) in
                UIView.animate(withDuration: 0.6) {
                    self.btnStartDrive.transform = CGAffineTransform.identity
                }
        });
        
    }
    
    @IBAction func startSelector(_ sender: RadiusButton) {
        sender.isEnabled = false
        ShowLoading(superView: self.view, position: .center, size: 40) { (loading) in
            RemoteService.startTrip(req: StartTripRequest(deviceId: getDeviceCode()), completion: { (response) in
                loading.stopAnimating()
                sender.isEnabled = true
                guard response.error == nil else {
                    print(response.error.debugDescription)
                    return
                }


                let result = Int(String(data:response.data! , encoding: .utf8) ?? "-1")
                if result != nil && result != -1 {
                    let vc = self.storyboard?.instantiateViewController(withIdentifier: "ComputeVC") as! ComputeVC
                    vc.tripID = result
                    self.present(vc, animated: true, completion: nil)
                } else {
                    print("Can't parse result to Integer type")
                }
            })
        }
    }
    
}

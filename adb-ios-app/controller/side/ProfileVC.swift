//
//  ProfileVC.swift
//  driver-behavior-ios-app
//
//  Created by Amir Mansouri Fard (amf.fard@gmail.com) on 8/2/18.
//  Copyright © 2018 Amir Mansouri Fard (amf.fard@gmail.com). All rights reserved.
//

import UIKit
import LinearProgressView

class ProfileVC: UIViewController {
    
    @IBOutlet weak var progressBar: LinearProgressView!
    @IBOutlet weak var percentageLBL: UILabel!
    @IBOutlet weak var mobileLBL: UILabel!
    @IBOutlet weak var emailLBL: UILabel!
    
    let selfController = ProfileC()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.navigationItem.title = "پروفایل من"
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        selfController.getUserProfile(context: self)
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    
    func setDataToWidgets() {
        self.mobileLBL.text = "موبایل: \(selfController.result.phoneNumber ?? "")"
        self.emailLBL.text = "ایمیل: \(selfController.result.email ?? "")"
        
        var percentage = 0.0
        if selfController.result.address != nil && !selfController.result.address!.isEmpty {
            percentage += 10
        }
        
        if selfController.result.firstName != nil && !selfController.result.firstName!.isEmpty {
            percentage += 10
        }
        
        if selfController.result.lastName != nil && !selfController.result.lastName!.isEmpty {
            percentage += 10
        }
        
        if selfController.result.birthDate != nil && !selfController.result.birthDate!.isEmpty {
            percentage += 10
        }
        
        if selfController.result.city != nil && !selfController.result.city!.isEmpty {
            percentage += 10
        }
        
        if selfController.result.email != nil && !selfController.result.email!.isEmpty {
            percentage += 10
        }
        
        if selfController.result.gender != nil && !selfController.result.gender!.isEmpty {
            percentage += 10
        }
        
        
        if selfController.result.phoneNumber != nil && !selfController.result.phoneNumber!.isEmpty {
            percentage += 10
        }
        
        
        if selfController.result.postalNumber != nil && !selfController.result.postalNumber!.isEmpty {
            percentage += 10
        }
        
        
        if selfController.result.age != nil && !(selfController.result.age! != 0) {
            percentage += 10
        }
        
        
        progressBar.setProgress(Float(percentage), animated: true)
        percentageLBL.text = "\(Int(percentage))٪ اطلاعات تکمیل شده"
    }
    
    @IBAction func insertDataSelector(sender : UIButton) {
        if selfController.result != nil {
            let vc = self.storyboard?.instantiateViewController(withIdentifier: "EditProfileVC") as! EditProfileVC
            vc.profile = selfController.result
            self.navigationController?.pushViewController(vc, animated: true)
        }
    }
    
    @IBAction func logOutSelector(sender : UIButton) {
        let alert = UIAlertController(title: "خروج از حساب", message: "آیا می خواهید از حساب خود خارج شوید؟", preferredStyle: .alert)
        alert.addAction(UIAlertAction(title: "بله", style: .default, handler: { (action) in
            UserDefaults.standard.removeObject(forKey: K.API_TOKEN_KEY)
            let vc = self.storyboard?.instantiateViewController(withIdentifier: "LaunchScreenVC")
            (UIApplication.shared.delegate as! AppDelegate).window?.rootViewController = vc!
            (UIApplication.shared.delegate as! AppDelegate).window?.makeKeyAndVisible()
        }))
        
        alert.addAction(UIAlertAction(title: "خیر", style: .default, handler: { (action) in
            alert.dismiss(animated: true, completion: nil)
        }))
        
        self.present(alert, animated: true, completion: nil)
    }
}

class ProfileC {
    var result : Profile!
    func getUserProfile(context : ProfileVC) {
        ShowLoading(superView: context.view, position: .center, size: 40) { (loading) in
            RemoteService.getUserProfile(completion: { (response) in
                loading.stopAnimating()
                guard response.error == nil else {
                    print(response.error.debugDescription)
                    return
                }
                
                do {
                    self.result = try
                        JSONDecoder().decode(Profile.self,from: response.data!)
                    context.setDataToWidgets()
                } catch let err {
                    print(err.localizedDescription)
                }
            })
        }
    }
}

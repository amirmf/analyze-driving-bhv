//
//  InviteVC.swift
//  driver-behavior-ios-app
//
//  Created by Amir Mansouri Fard (amf.fard@gmail.com) on 8/11/18.
//  Copyright © 2018 Amir Mansouri Fard (amf.fard@gmail.com). All rights reserved.
//

import UIKit
import ContactsUI

class InviteVC: UIViewController {

    static var singleton : InviteVC!
    
    var groupId : Int64!
    
    @IBOutlet weak var table: UITableView!
    @IBOutlet weak var mobileTF: UITextField!
    
    var mobiles = Array<String>()
    
    let contact = CNContactPickerViewController()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        InviteVC.singleton = self
        
        table.estimatedRowHeight = 40
        table.rowHeight = UITableViewAutomaticDimension
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

    @IBAction func selectContactSelector(_ sender: RadiusButton) {
        contact.delegate = self
        self.present(contact, animated: true, completion: nil)
    }
    
    @IBAction func sendRequestSelector(_ sender: RadiusButton) {
        if mobiles.isEmpty {
            let alert = UIAlertController(title: nil, message: "شما هنوز مخاطبی ثبت نکرده اید", preferredStyle: .alert)
            alert.addAction(UIAlertAction(title: "باشه", style: .cancel, handler: { (action) in
                alert.dismiss(animated: true, completion: nil)
            }))
            
            self.present(alert, animated: true, completion: nil)
        } else {
            ShowLoading(superView: self.view, position: .center, size: 40) { (loading) in
                RemoteService.multipleInvire(MultipleInviteRequest(groupId: self.groupId, phoneNumbers: self.mobiles), completion: { (response) in
                    loading.stopAnimating()
                    if response.response != nil && response.response!.statusCode == 200 {
                        let alert = UIAlertController(title: nil, message: "دعوت از دوستان شما با موفقیت ارسال شد", preferredStyle: .alert)
                        alert.addAction(UIAlertAction(title: "باشه", style: .cancel, handler: { (action) in
                            alert.dismiss(animated: true, completion: nil)
                            self.navigationController?.popViewController(animated: true)
                        }))
                        self.present(alert, animated: true, completion: nil)
                    } else {
                        let alert = UIAlertController(title: nil, message: "دعوت از دوستان شما با خطا مواجه شده است. لطفا دوباره تلاش کنید", preferredStyle: .alert)
                        alert.addAction(UIAlertAction(title: "باشه", style: .cancel, handler: { (action) in
                            alert.dismiss(animated: true, completion: nil)
                        }))
                        self.present(alert, animated: true, completion: nil)
                    }
                })
            }
        }
    }
    
    @IBAction func addSelector(_ sender: RadiusButton) {
        if (self.mobileTF.text ?? "").isEmpty || isValidMobileByZero(testStr: self.mobileTF.text ?? "") {
            let alert = UIAlertController(title: nil, message: "لطفا شماره موبایل معتبری وارد کنید", preferredStyle: .alert)
            alert.addAction(UIAlertAction(title: "باشه", style: .cancel, handler: { (action) in
                alert.dismiss(animated: true, completion: nil)
            }))
            
            self.present(alert, animated: true, completion: nil)
        } else {
            mobiles.append(self.mobileTF.text!)
            mobileTF.text = ""
            self.table.reloadData()
        }
    }
    
    func isValidMobileByZero(testStr : String) -> Bool {
        let mobileRegEx = "[09][0-9]{9}"
        
        let mobileTest = NSPredicate(format:"SELF MATCHES %@", mobileRegEx)
        return mobileTest.evaluate(with: testStr)
    }
    
    @IBAction func cancelSelector(_ sender: RadiusButton) {
        self.navigationController?.popViewController(animated: true)
    }
}

extension InviteVC : CNContactPickerDelegate {
    func contactPickerDidCancel(_ picker: CNContactPickerViewController) {
        picker.dismiss(animated: true, completion: nil)
    }
    
    func contactPicker(_ picker: CNContactPickerViewController, didSelect contacts: [CNContact]) {
        for contact in contacts {
            mobiles.append(contact.phoneNumbers.first!.value.stringValue)
        }
        
        table.reloadData()
    }
}

extension InviteVC : UITableViewDelegate , UITableViewDataSource {
    func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return self.mobiles.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "Cell", for: indexPath) as! MobileCell
        cell.configureCell(mobile: self.mobiles[indexPath.row])
        cell.selectionStyle = .none
        return cell
    }
    
    
}

class MobileCell : UITableViewCell {
    @IBOutlet weak var mobileLBL: UILabel!
    var mobile = ""
    func configureCell(mobile : String) {
        self.mobileLBL.text = mobile
        self.mobile = mobile
    }
    
    @IBAction func deleteSelector(_ sender: UIButton) {
        for i in 0..<InviteVC.singleton.mobiles.count {
            if InviteVC.singleton.mobiles[i] == self.mobile {
                InviteVC.singleton.mobiles.remove(at: i)
                InviteVC.singleton.table.reloadData()
                break
            }
        }
    }
}

//
//  EditProfileVC.swift
//  driver-behavior-ios-app
//
//  Created by Amir Mansouri Fard (amf.fard@gmail.com) on 8/2/18.
//  Copyright © 2018 Amir Mansouri Fard (amf.fard@gmail.com). All rights reserved.
//

import UIKit
import DropDown
import DatePickerDialog

class EditProfileVC: UIViewController {

    @IBOutlet weak var dateTF: UICustomTF!
    @IBOutlet weak var telTF: UICustomTF!
    @IBOutlet weak var addressTF: UICustomTF!
    @IBOutlet weak var cityTF: UICustomTF!
    @IBOutlet weak var emailTF: UICustomTF!
    @IBOutlet weak var genderTF: UICustomTF!
    @IBOutlet weak var lastNameTF: UICustomTF!
    @IBOutlet weak var mobileLBL: UILabel!
    @IBOutlet weak var nameTF: UICustomTF!
    
    var profile : Profile!
    
    let selfController = EditprofileC()
    
    let dropDown = DropDown()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.navigationItem.title = "ویرایش پروفایل من"
        self.setDataToWidgets()
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    private func setDataToWidgets() {
        self.nameTF.text = self.profile.firstName
        self.lastNameTF.text = self.profile.lastName
        self.genderTF.text = self.profile.gender == "MALE" ? "آقا" : "خانم"
        self.emailTF.text = self.profile.email
        self.mobileLBL.text = "شماره موبایل: \(self.profile.phoneNumber ?? "")"
        self.telTF.text = self.profile.phoneNumber
        self.cityTF.text = self.profile.city
        self.addressTF.text = self.profile.address
        self.dateTF.text = convertDate(dateString: self.profile.birthDate ?? "")
        self.telTF.isEnabled = false
    }

    @IBAction func sendProfileSelector(_ sender: RadiusButton) {
        selfController.sendProfile(context: self)
    }
}


extension EditProfileVC : UITextFieldDelegate {
    func textFieldDidBeginEditing(_ textField: UITextField) {
        if textField == genderTF {
            dropDown.anchorView = textField
            dropDown.topOffset = CGPoint(x: 0, y: textField.height)
            dropDown.dataSource = ["آقا" , "خانم"]
            dropDown.customCellConfiguration = {(index , item , cell) in
                cell.optionLabel.textAlignment = .right
                cell.optionLabel.font = UIFont(name: "IRANSansWeb", size: 14)
            }
            dropDown.selectionAction = {[unowned self] (index, item) in
                textField.text = item
            }
            
            dropDown.show()
            textField.endEditing(true)
        }
        
        
        if textField == dateTF {
            let datepicker = DatePickerDialog(locale: Locale(identifier: "fa_IR"))
            datepicker.datePicker.datePickerMode = .date
            datepicker.datePicker.calendar = Calendar(identifier: Calendar.Identifier.persian)
            datepicker.show("انتخاب تاریخ تولد", doneButtonTitle: "انتخاب", cancelButtonTitle: "لغو", datePickerMode: .date) {
                (date) -> Void in
                if let dt = date {
                    let formatter = DateFormatter()
                    formatter.calendar = Calendar(identifier: Calendar.Identifier.persian)
                    formatter.dateFormat = "yyyy/MM/dd"
                    textField.text = formatter.string(from: dt)
                }
            }
        }
    }
    
    func textFieldDidEndEditing(_ textField: UITextField) {
        switch textField {
        case self.nameTF:
            profile.firstName = textField.text ?? ""
            break
        case self.lastNameTF:
            profile.lastName = textField.text ?? ""
            break
        case self.genderTF:
            profile.gender = textField.text ?? "" == "آقا" ? "MALE" : "FEMALE"
            break
        case self.emailTF:
            profile.email = textField.text ?? ""
            break
        case self.cityTF:
            profile.city = textField.text ?? ""
            break
        case self.addressTF:
            profile.address = textField.text ?? ""
            break
        case self.telTF:
            profile.phoneNumber = textField.text ?? ""
            break
        case self.dateTF:
            profile.birthDate = convertDateToGregorian(dateString: textField.text ?? "")
            break
        default:
            break
        }
    }
    
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        self.view.endEditing(true)
        return true
    }
}

class EditprofileC {
    func sendProfile(context : EditProfileVC) {
        ShowLoading(superView: context.view, position: .center, size: 40) { (loading) in
            RemoteService.updateUserProfile(context.profile, completion: { (response) in
                loading.stopAnimating()
                let alert = UIAlertController()
                if response.response != nil && response.response!.statusCode == 200 {
                    alert.title = ""
                    alert.message = "ویرایش اطلاعات با موفقیت انجام شد"
                } else {
                    alert.title = ""
                    alert.message = "عملیات با خطا مواجه شد. لطفا دوباره تلاش کنید"
                }
                
                alert.addAction(UIAlertAction(title: "باشه", style: .default, handler: { (action) in
                    alert.dismiss(animated: true, completion: nil)
                }))
                
                context.present(alert, animated: true, completion: nil)
            })
        }
    }
}

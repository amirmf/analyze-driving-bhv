//
//  ReminderVC.swift
//  driver-behavior-ios-app
//
//  Created by Amir Mansouri Fard (amf.fard@gmail.com) on 9/5/18.
//  Copyright © 2018 Amir Mansouri Fard (amf.fard@gmail.com). All rights reserved.
//

import UIKit
import BEMCheckBox
import DropDown
import DatePickerDialog

class ReminderVC: UIViewController {

    @IBOutlet weak var enableReminderSwitch : UISwitch!
    @IBOutlet weak var expireCheckBox : BEMCheckBox!
    @IBOutlet weak var serviceCheckBox : BEMCheckBox!
    @IBOutlet weak var expireDateBtn : UIButton!
    @IBOutlet weak var serviceDateBtn : UIButton!
    @IBOutlet weak var monthBtn : UIButton!
    @IBOutlet weak var enablingView : UIView!
    
    
    var month = 1
    var expireDate = ""
    var serviceDate = ""
    
    
    let dropDown = DropDown()
    let selfController = ReminderC()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.navigationItem.title = "یادآوری"
        expireDateBtn.isEnabled = false
        serviceDateBtn.isEnabled = false
        monthBtn.isEnabled = false
        
        enableReminderSwitch.addTarget(self, action: #selector(self.switchSelector(sender:)), for: .valueChanged)
        expireCheckBox.addTarget(self, action: #selector(self.expireCheckBoxSelector(sender:)), for: .valueChanged)
        serviceCheckBox.addTarget(self, action: #selector(self.serviceCheckBoxSelector(sender:)), for: .valueChanged)
        expireDateBtn.addTarget(self, action: #selector(self.expireDateSelector(sender:)), for: .touchUpInside)
        serviceDateBtn.addTarget(self, action: #selector(self.serviceDateSelector(sender:)), for: .touchUpInside)
        monthBtn.addTarget(self, action: #selector(self.monthSelector(sender:)), for: .touchUpInside)
        
        selfController.getReminder(context: self)
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    
    @objc func switchSelector(sender : UISwitch) {
        enablingView.isHidden = sender.isOn
    }

    
    @objc func expireCheckBoxSelector(sender : BEMCheckBox) {
        expireDateBtn.isEnabled = sender.on
    }
    
    @objc func serviceCheckBoxSelector(sender : BEMCheckBox) {
        serviceDateBtn.isEnabled = sender.on
        monthBtn.isEnabled = sender.on
    }
    
    @objc func expireDateSelector(sender : UIButton) {
        let datepicker = DatePickerDialog(locale: Locale(identifier: "fa_IR"))
        datepicker.datePicker.datePickerMode = .date
        datepicker.datePicker.calendar = Calendar(identifier: Calendar.Identifier.persian)
        datepicker.show("انتخاب تاریخ انقضا", doneButtonTitle: "انتخاب", cancelButtonTitle: "لغو", datePickerMode: .date) {
            (date) -> Void in
            if let dt = date {
                let formatter = DateFormatter()
                formatter.calendar = Calendar(identifier: Calendar.Identifier.persian)
                formatter.dateFormat = "yyyy/MM/dd"
                self.expireDate = convertDateToGregorian(dateString: formatter.string(from: dt).replacingOccurrences(of: "/", with: "-") + " 00:00:00")
                sender.setTitle("تاریخ انقضاء : " + formatter.string(from: dt), for: .normal)
            }
        }
        
    }
    
    @objc func serviceDateSelector(sender : UIButton) {
        let datepicker = DatePickerDialog(locale: Locale(identifier: "fa_IR"))
        datepicker.datePicker.datePickerMode = .date
        datepicker.datePicker.calendar = Calendar(identifier: Calendar.Identifier.persian)
        datepicker.show("انتخاب تاریخ سرویس", doneButtonTitle: "انتخاب", cancelButtonTitle: "لغو", datePickerMode: .date) {
            (date) -> Void in
            if let dt = date {
                let formatter = DateFormatter()
                formatter.calendar = Calendar(identifier: Calendar.Identifier.persian)
                formatter.dateFormat = "yyyy/MM/dd"
                self.serviceDate = convertDateToGregorian(dateString: formatter.string(from: dt).replacingOccurrences(of: "/", with: "-") + " 00:00:00")
                sender.setTitle("تاریخ سرویس : " + formatter.string(from: dt), for: .normal)
            }
        }
    }
    
    @objc func monthSelector(sender : UIButton) {
        dropDown.anchorView = sender
        dropDown.topOffset = CGPoint(x: 0, y: sender.height)
        dropDown.dataSource = ["1" , "2" , "3" , "4" , "5" , "6" , "7" , "8" , "9" , "10" , "11" , "12"]
        dropDown.customCellConfiguration = {(index , item , cell) in
            cell.optionLabel.textAlignment = .right
            cell.optionLabel.font = UIFont(name: "IRANSansWeb", size: 14)
        }
        dropDown.selectionAction = {[unowned self] (index, item) in
            sender.setTitle("سرویس بعدی : \(item) ماه دیگر", for: .normal)
            self.month = Int(item) ?? 1
        }
        
        dropDown.show()
    }
    
    @IBAction func registerSelector(sender : RadiusButton) {
        if enableReminderSwitch.isOn {
           let reminder = Reminder(insuranceExpirationDate: expireDate, lastServiceDate: serviceDate, serviceAfter: Int64(month), active: true)
            selfController.updateReminder(context: self, reminder: reminder)
        } else {
            let reminder = Reminder(active: false)
            selfController.updateReminder(context: self, reminder: reminder)
        }
    }
}


class ReminderC {
    func getReminder(context : ReminderVC) {
        ShowLoading(superView: context.view, position: .center, size: 40) { (loading) in
            RemoteService.getReminder(completion: { (response) in
                loading.stopAnimating()
                let alert = UIAlertController()
                if response.response != nil && response.response!.statusCode == 200 {
                    do {
                        let result = try JSONDecoder().decode(Reminder.self, from: response.data!)
                        if result.active {
                            context.enablingView.isHidden = true
                            context.enableReminderSwitch.setOn(true, animated: true)
                            if result.insuranceExpirationDate != nil {
                                context.expireDate = result.insuranceExpirationDate!
                                context.expireCheckBox.setOn(true, animated: true)
                                context.expireCheckBoxSelector(sender: context.expireCheckBox)
                                context.expireDateBtn.setTitle("تاریخ انقضاء : \(convertDate(dateString: result.insuranceExpirationDate!).replacingOccurrences(of: "-", with: "/").replacingOccurrences(of: " 12:00:00", with: ""))", for: UIControlState.normal)
                            }
                            
                            if result.lastServiceDate != nil {
                                context.serviceDate = result.lastServiceDate!
                                context.serviceCheckBox.setOn(true, animated: true)
                                context.serviceCheckBoxSelector(sender: context.serviceCheckBox)
                                context.serviceDateBtn.setTitle("تاریخ سرویس : \(convertDate(dateString: result.lastServiceDate!).replacingOccurrences(of: "-", with: "/").replacingOccurrences(of: " 12:00:00", with: ""))", for: UIControlState.normal)
                            }
                            
                            if result.serviceAfter != nil {
                                context.month = Int(result.serviceAfter!)
                                context.serviceCheckBox.setOn(true, animated: true)
                                context.serviceCheckBoxSelector(sender: context.serviceCheckBox)
                                context.monthBtn.setTitle("سرویس بعدی : \(result.serviceAfter!) ماه دیگر", for: UIControlState.normal)
                            }
                        }
                    } catch let err {
                        print(err.localizedDescription)
                    }
                    
                } else {
                    alert.title = ""
                    alert.message = "عملیات با خطا مواجه شد. لطفا دوباره تلاش کنید"
                    
                    alert.addAction(UIAlertAction(title: "باشه", style: .default, handler: { (action) in
                        alert.dismiss(animated: true, completion: nil)
                    }))
                    
                    context.present(alert, animated: true, completion: nil)
                }
            })
        }
    }
    
    func updateReminder(context : ReminderVC, reminder : Reminder) {
        ShowLoading(superView: context.view, position: .center, size: 40) { (loading) in
            RemoteService.updateReminder(reminder: reminder, completion: { (response) in
                loading.stopAnimating()
                let alert = UIAlertController()
                if response.response != nil && response.response!.statusCode == 200 {
                    do {
                        let result = try JSONDecoder().decode(Reminder.self, from: response.data!)
                        if result.active {
                            context.enablingView.isHidden = true
                            context.enableReminderSwitch.setOn(true, animated: true)
                            if result.insuranceExpirationDate != nil {
                                context.expireDate = result.insuranceExpirationDate!
                                context.expireCheckBox.setOn(true, animated: true)
                                context.expireCheckBoxSelector(sender: context.expireCheckBox)
                                context.expireDateBtn.setTitle("تاریخ انقضاء : \(convertDate(dateString: result.insuranceExpirationDate!).replacingOccurrences(of: "-", with: "/").replacingOccurrences(of: " 12:00:00", with: ""))", for: UIControlState.normal)
                            }
                            
                            if result.lastServiceDate != nil {
                                context.serviceDate = result.lastServiceDate!
                                context.serviceCheckBox.setOn(true, animated: true)
                                context.serviceCheckBoxSelector(sender: context.serviceCheckBox)
                                context.serviceDateBtn.setTitle("تاریخ سرویس : \(convertDate(dateString: result.lastServiceDate!).replacingOccurrences(of: "-", with: "/").replacingOccurrences(of: " 12:00:00", with: ""))", for: UIControlState.normal)
                            }
                            
                            if result.serviceAfter != nil {
                                context.month = Int(result.serviceAfter!)
                                context.serviceCheckBox.setOn(true, animated: true)
                                context.serviceCheckBoxSelector(sender: context.serviceCheckBox)
                                context.monthBtn.setTitle("سرویس بعدی : \(result.serviceAfter!) ماه دیگر", for: UIControlState.normal)
                            }
                        } else {
                            context.enableReminderSwitch.setOn(false, animated: true)
                            context.expireCheckBox.setOn(false, animated: true)
                            context.serviceCheckBox.setOn(false, animated: true)
                            context.enablingView.isHidden = false
                        }
                    } catch let err {
                        print(err.localizedDescription)
                    }
                    
                } else {
                    alert.title = ""
                    alert.message = "عملیات با خطا مواجه شد. لطفا دوباره تلاش کنید"
                    
                    alert.addAction(UIAlertAction(title: "باشه", style: .default, handler: { (action) in
                        alert.dismiss(animated: true, completion: nil)
                    }))
                    
                    context.present(alert, animated: true, completion: nil)
                }
            })
        }
    }
}

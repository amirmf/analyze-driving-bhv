//
//  ParentsListVC.swift
//  driver-behavior-ios-app
//
//  Created by Amir Mansouri Fard (amf.fard@gmail.com) on 9/10/18.
//  Copyright © 2018 Amir Mansouri Fard (amf.fard@gmail.com). All rights reserved.
//

import UIKit
import ContactsUI
import XLPagerTabStrip

class ParentsListVC: UIViewController , IndicatorInfoProvider {

    let contact = CNContactPickerViewController()
    
    static var singleton : ParentsListVC!
    
    @IBOutlet weak var table : UITableView!
    
    let selfController = ParentsListC()
    
    var textField : UITextField!
    
    override func viewDidLoad() {
        super.viewDidLoad()

        ParentsListVC.singleton = self
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    

    func indicatorInfo(for pagerTabStripController: PagerTabStripViewController) -> IndicatorInfo {
        return IndicatorInfo(title: "لیست والدین")
    }

    @IBAction func addSelector(sender : RadiusButton) {
        let alert = UIAlertController(title: nil, message: "شماره والدین", preferredStyle: .alert)
        alert.addTextField { (tf) in
            tf.font = UIFont(name: "IRANSans(FaNum)", size: 14)
            tf.placeholder = "لطفا یک شماره موبایل وارد کنید"
            tf.textAlignment = .right
            self.textField = tf
        }
        
        alert.addAction(UIAlertAction(title: "افزودن شماره از لیست مخاطبین", style: .default, handler: { (action) in
            alert.dismiss(animated: true, completion: nil)
            self.contact.delegate = self
            self.present(self.contact, animated: true, completion: nil)
        }))
        
        alert.addAction(UIAlertAction(title: "ثبت", style: .default, handler: { (action) in
            if self.textField.text != nil && !self.textField.text!.isEmpty {
                ShowLoading(superView: self.view, position: .center, size: 40, afterLoading: { (loading) in
                    RemoteService.addParent(number: self.textField.text ?? "", completion: { (response) in
                        loading.stopAnimating()
                        if response.response != nil && response.response!.statusCode == 200 {
                            self.selfController.getParents(context: self)
                        } else {
                            if response.response != nil && response.response!.statusCode == 511 {
                                let alert3 = UIAlertController(title: "خطا", message: "شخصی با این مشخصا در سیستم یافت نشد", preferredStyle: .alert)
                                alert3.addAction(UIAlertAction(title: "باشه", style: .default, handler: { (action) in
                                    alert.dismiss(animated: true, completion: nil)
                                }))
                                
                                self.present(alert3, animated: true, completion: nil)
                            } else {
                                let alert3 = UIAlertController(title: "خطا", message: "خطایی رخ داده است. لطفا دوباره تلاش کنید", preferredStyle: .alert)
                                alert3.addAction(UIAlertAction(title: "باشه", style: .default, handler: { (action) in
                                    alert.dismiss(animated: true, completion: nil)
                                }))
                                
                                self.present(alert3, animated: true, completion: nil)
                            }
                        }
                    })
                })
            } else {
                alert.dismiss(animated: true, completion: nil)
                let alert2 = UIAlertController(title: nil, message: "شماره موبایلی را وارد کنید", preferredStyle: .alert)
                alert2.addAction(UIAlertAction(title: "باشه", style: .default, handler: { (action) in
                    alert2.dismiss(animated: true, completion: nil)
                }))
                
                self.present(alert2, animated: true, completion: nil)
            }
        }))
        
        alert.addAction(UIAlertAction(title: "انصراف", style: .default, handler: { (action) in
            alert.dismiss(animated: true, completion: nil)
        }))
        
        self.present(alert, animated: true, completion: nil)
    }
}

extension ParentsListVC : UITableViewDelegate , UITableViewDataSource {
    func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if selfController.result == nil {
            return 0
        }
        
        return selfController.result.items.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "Cell", for: indexPath) as! MemberCell
        cell.configureCell(model: selfController.result.items[indexPath.row])
        cell.selectionStyle = .none
        cell.deleteBtn.isHidden = false
        return cell
    }
}


extension ParentsListVC : CNContactPickerDelegate {
    func contactPickerDidCancel(_ picker: CNContactPickerViewController) {
        picker.dismiss(animated: true, completion: nil)
    }
    
    func contactPicker(_ picker: CNContactPickerViewController, didSelect contact: CNContact) {
        let alert = UIAlertController(title: nil, message: "شماره والدین", preferredStyle: .alert)
        alert.addTextField { (tf) in
            tf.font = UIFont(name: "IRANSans(FaNum)", size: 14)
            tf.placeholder = "لطفا یک شماره موبایل وارد کنید"
            tf.textAlignment = .right
            tf.text = contact.phoneNumbers.first!.value.stringValue
            self.textField = tf
        }
        
        alert.addAction(UIAlertAction(title: "افزودن شماره از لیست مخاطبین", style: .default, handler: { (action) in
            alert.dismiss(animated: true, completion: nil)
            self.contact.delegate = self
            self.present(self.contact, animated: true, completion: nil)
        }))
        
        alert.addAction(UIAlertAction(title: "ثبت", style: .default, handler: { (action) in
            if self.textField.text != nil && !self.textField.text!.isEmpty {
                ShowLoading(superView: self.view, position: .center, size: 40, afterLoading: { (loading) in
                    RemoteService.addParent(number: self.textField.text ?? "", completion: { (response) in
                        loading.stopAnimating()
                        if response.response != nil && response.response!.statusCode == 200 {
                            self.selfController.getParents(context: self)
                        } else {
                            if response.response != nil && response.response!.statusCode == 511 {
                                let alert3 = UIAlertController(title: "خطا", message: "شخصی با این مشخصا در سیستم یافت نشد", preferredStyle: .alert)
                                alert3.addAction(UIAlertAction(title: "باشه", style: .default, handler: { (action) in
                                    alert.dismiss(animated: true, completion: nil)
                                }))
                                
                                self.present(alert3, animated: true, completion: nil)
                            } else {
                                let alert3 = UIAlertController(title: "خطا", message: "خطایی رخ داده است. لطفا دوباره تلاش کنید", preferredStyle: .alert)
                                alert3.addAction(UIAlertAction(title: "باشه", style: .default, handler: { (action) in
                                    alert.dismiss(animated: true, completion: nil)
                                }))
                                
                                self.present(alert3, animated: true, completion: nil)
                            }
                        }
                    })
                })
            } else {
                alert.dismiss(animated: true, completion: nil)
                let alert2 = UIAlertController(title: nil, message: "شماره موبایلی را وارد کنید", preferredStyle: .alert)
                alert2.addAction(UIAlertAction(title: "باشه", style: .default, handler: { (action) in
                    alert2.dismiss(animated: true, completion: nil)
                }))
                
                self.present(alert2, animated: true, completion: nil)
            }
        }))
        
        alert.addAction(UIAlertAction(title: "انصراف", style: .default, handler: { (action) in
            alert.dismiss(animated: true, completion: nil)
        }))
        
        self.present(alert, animated: true, completion: nil)
    }
}

class ParentsListC {
    var result : ParentsList!
    func getParents(context : ParentsListVC) {
        ShowLoading(superView: context.view, position: .center, size: 40) { (loading) in
            RemoteService.getChilds(completion: { (response) in
                loading.stopAnimating()
                guard response.error == nil else {
                    return
                }
                
                do {
                    self.result = try JSONDecoder().decode(ParentsList.self, from: response.data!)
                    context.table.reloadData()
                } catch let err {
                    print(err.localizedDescription)
                }
            })
        }
    }
}

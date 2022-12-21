//
//  RegisterViewController.swift
//  driver-behavior-ios-app
//
//  Created by Amir Mansouri Fard (amf.fard@gmail.com) on 7/3/18.
//  Copyright © 2018 Amir Mansouri Fard (amf.fard@gmail.com). All rights reserved.
//

import UIKit
import SwiftyJSON

class LoginViewController: UIViewController , UITextFieldDelegate {
    
    // MARK : Properties
    
    var sendPN_count = 0;
    var sendCode_count = 0;
    
    @IBOutlet weak var sendPN_btn: UIRoundedButton!
    
    @IBOutlet weak var sendCode_btn: UIRoundedButton!
    
    @IBOutlet weak var pn_fld: UICustomTextField!
    
    @IBOutlet weak var code_fld: UICustomTextField!
    
    @IBOutlet weak var pn_fld_v: UILabel!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
        self.setuView();
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    //MARK : Actions
    
    private func enablePhoneNumber(){
        Utility.enable(self.pn_fld);
        Utility.enable(self.sendPN_btn);
    }
    
    private func disablePhoneNumber(){
        Utility.disable(self.pn_fld);
        Utility.disable(self.sendPN_btn);
    }
    
    private func enableCode(){
        Utility.enable(self.code_fld);
        Utility.enable(self.sendCode_btn);
    }
    
    private func disableCode(){
        Utility.disable(self.code_fld);
        Utility.disable(self.sendCode_btn);
    }
    
    private func setuView(){
        
        self.disablePhoneNumber();
        self.disableCode();
        Utility.enable(self.pn_fld);
        
        self.pn_fld.delegate = self;
        self.code_fld.delegate = self;
        
        self.pn_fld.addTarget(self,
                              action: #selector(textFieldDidChange(textField:)),
                              for: UIControlEvents.editingChanged);
        self.code_fld.addTarget(self,
                                action: #selector(textFieldDidChange(textField:)),
                                for: UIControlEvents.editingChanged);
        
    }
    
    @IBAction func sendPhoneNumber(_ sender: UIButton) {
        
        var timer : Timer? = nil;
        
        if let phoneNumber = self.pn_fld.text {
            let request = RegisterRequest(phoneNumber:phoneNumber);
            self.disablePhoneNumber();
            self.disableCode();
            do{
                timer = Timer.scheduledTimer(withTimeInterval: 120, repeats: false, block: { timer in
                    self.enablePhoneNumber();
                });
                let _ = RemoteService.register(request: request);
                Utility.enable(self.code_fld);
            } catch {
                self.enablePhoneNumber();
                if let t = timer {
                    t.invalidate();
                }
            }
        }
        
    }
    
    @IBAction func sendCode(_ sender: UIButton) {
        
        var timer : Timer? = nil;
        
        if let code = self.code_fld.text , let phoneNumber = self.pn_fld.text{
            let request = LoginRequest(phoneNumber:phoneNumber, password:code);
            self.disablePhoneNumber()
            self.disableCode();
            do{
                timer = Timer.scheduledTimer(withTimeInterval: 120, repeats: false, block: { timer in
                    self.enablePhoneNumber();
                    self.enableCode();
                });
                
                RemoteService.login(request: request) { (response) in
                    guard response.error == nil else {
                        let alert = UIAlertController(title: "خطا", message: "خطا دربرقرار ارتباط با سرور. لطفا دوباره تلاش کنید", preferredStyle: UIAlertControllerStyle.alert)
                        alert.addAction(UIAlertAction(title: "باشه", style: .default, handler: { (action) in
                            alert.dismiss(animated: true, completion: nil)
                        }))
                        
                        self.present(alert, animated: true, completion: nil)
                        return
                    }
                    
                    if response.response == nil || response.response!.statusCode == 404 {
                        let alert = UIAlertController(title: "خطا", message: "کد تایید وارد شده صحیح نمی باشد", preferredStyle: UIAlertControllerStyle.alert)
                        alert.addAction(UIAlertAction(title: "باشه", style: .default, handler: { (action) in
                            alert.dismiss(animated: true, completion: nil)
                        }))
                        
                        self.present(alert, animated: true, completion: nil)
                        self.enableCode();
                    } else {
                        let token = JSON(response.data!)["token"].stringValue;
                        let defaults = UserDefaults.standard;
                        defaults.set(token,forKey:K.API_TOKEN_KEY);
                        
                        NotificationCenter.default.post(name: Notification.Name.loginDone,                                                       object:nil);
                    }
                }
                
                NotificationCenter.default.addObserver(self,selector: #selector(loginDone(_:)),name: .loginDone , object : nil );
            } catch {
                self.enablePhoneNumber();
                self.enableCode();
                if let t = timer {
                    t.invalidate();
                }
            }
        }
    }
    
    @objc func loginDone(_ notification : Notification)-> Void{
        
        if let instance = RootViewController.instance {
            instance.replaceView(RootViewController.getMainView());
        }
    }
    
    func validate (_ textField : UITextField)-> (Bool,String?){
        switch textField{
        case self.pn_fld :
            let phone_number_regx = "^09[0-9'@s]{9,9}$";
            guard let text = textField.text else{
                return (false,nil);
            }
            
            if self.pn_fld === textField{
                
                let pattern = NSPredicate(format: "SELF MATCHES %@" , phone_number_regx );
                return (pattern.evaluate(with: text),"شماره موبایل معتبر نمی باشد");
                
            }
            
            return (text.characters.count>0,"این فیلد الزامی است")
        case self.code_fld :
            return (true,"");
        default :
            return (true,"");
        }
    }
    
    func checkEnglishModeNumbers(text : String) -> String{
        var text = text
        let engs = ["0" , "1" , "2" , "3" , "4" , "5" , "6" , "7" , "8" , "9"]
        for ch in text {
            var flag = false
            for english in engs {
                if english == String(ch) {
                    flag = true
                    break
                }
            }
            text = flag ? text : text.replacingOccurrences(of: "\(ch)", with: "")
        }
        
        return text
    }
    
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        textFieldDidChange(textField: textField);
        return true;
    }
    
    @objc private func textFieldDidChange(textField : UITextField){
        textField.text = checkEnglishModeNumbers(text: textField.text ?? "")
        let (isValid,message) = validate(self.pn_fld);
        switch textField{
        case self.pn_fld :
            if isValid{
                Utility.enable(self.sendPN_btn);
                Utility.hideMessage(self.pn_fld_v);
            }else{
                Utility.disable(self.sendPN_btn);
                if let msg = message {
                    Utility.showMessage(self.pn_fld_v,msg:msg);
                }
            }
        case self.code_fld :
            if isValid {
                Utility.enable(self.sendCode_btn);
            }else{
                Utility.disable(self.sendCode_btn);
            }
        default :
            Utility.doNothing();
        }
    }
    
    /*
     // MARK: - Navigation
     
     // In a storyboard-based application, you will often want to do a little preparation before navigation
     override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
     // Get the new view controller using segue.destinationViewController.
     // Pass the selected object to the new view controller.
     }
     */
    
}

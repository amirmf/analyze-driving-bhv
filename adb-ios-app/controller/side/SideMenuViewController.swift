//
//  SideMenuViewController.swift
//  driver-behavior-ios-app
//
//  Created by Amir Mansouri Fard (amf.fard@gmail.com) on 6/18/18.
//  Copyright © 2018 Amir Mansouri Fard (amf.fard@gmail.com). All rights reserved.
//

import Foundation
import UIKit

class SideMenuViewController : UITableViewController {
    
    // MARK : Properties
    
    
    let menuItems = [
        ["mn_home","صفحه اصلی","داشبورد",type(of:HomeViewController.self)],
        [ "mn_messages", "پیامها","پیامهای دریافتی خود را مشاهده کنید",type(of:MessageViewController.self)],
        [ "ic_parent", "کنترل والدین","توضیحات کنترل والدین",type(of:ReminderViewController.self)],
        [ "mn_reminder", "یادآوری","تنظیم یادآوری",type(of:ReminderViewController.self)],
        [ "mn_news", "اخبار","با آخرین خبرها بروز باشید",type(of:NewsViewController.self)],
        [ "mn_invitation", "دعوت دوستان","با دوستان رقابت کنید",type(of:InvitationsVC.self)],
        [ "mn_statistic", "آمار","درباره رانندگی خود بیشتر بدانید",type(of:StatisticViewController.self)],
        [ "mn_awards", "جوایز","گرفتن امتیاز و جایزه",type(of:AwardViewController.self)],
        [ "mn_teams", "راهنما","راهنمای سریع بخش های برنامه",type(of:IntroVC.self)],
        [ "mn_faq", "پرسش های متداول","سوالات مربوط به اپلیکیشن",type(of:StatisticViewController.self)],
        [ "mn_aboutUs", "درباره ما","با ما ارتباط برقرار کنید",type(of:ChallengeViewController.self)]
        , /*[ "mn_exit", "خروج","خروج از برنامه",type(of:ChallengeViewController.self)]*/
    ]
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        
        self.tableView.backgroundColor = K.color_s
        self.tableView.separatorColor = UIColor.clear
        self.tableView.allowsSelection = true
        self.tableView.allowsMultipleSelection = false
        
        self.tableView.bounces = false
        self.tableView.alwaysBounceVertical = false
        self.tableView.alwaysBounceHorizontal = false
        self.tableView.bouncesZoom = false
        
        self.tableView.sectionHeaderHeight = CGFloat(100.0)//UITableViewAutomaticDimension
        
    }
    
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        self.tableView.reloadData()
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    // MARK: - Table view data source
    override func numberOfSections(in tableView: UITableView) -> Int {
        // #warning Incomplete implementation, return the number of sections
        return 1
    }
    
    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        // #warning Incomplete implementation, return the number of rows
        return self.menuItems.count
    }
    
    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "cellTemplate", for: indexPath) as! SideMenuCell
        
        cell.selectedBackgroundView?.backgroundColor=UIColor.clear
        cell.backgroundColor = K.color_s
        cell.tintColor = UIColor.white
        
        cell.lblTitle?.text = menuItems[indexPath.row][1] as? String
        cell.lblTitle?.textColor = UIColor.white
        cell.lblTitle?.tintColor = UIColor.white
        
        cell.lblDetail?.text = menuItems[indexPath.row][2] as? String
        cell.lblDetail?.textColor = UIColor.white
        cell.lblDetail?.tintColor = UIColor.white
        
        let imageName = UIImage(named: menuItems[indexPath.row][0] as! String)
        cell.imgIcon?.image = imageName
        
        cell.selectionStyle = .none
        
        if indexPath.row == 1 && NOTREADCOUNT != 0 {
            cell.counterLBL.text = "\(NOTREADCOUNT)"
            cell.counterLBL.isHidden = false
        } else {
            cell.counterLBL.isHidden = true
        }
        
        return cell
    }
    
    /*
     on tap
     */
    override func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        switch indexPath.row {
        case 0:
            let vc = self.storyboard?.instantiateViewController(withIdentifier: "mainView")
            (UIApplication.shared.delegate as! AppDelegate).window?.rootViewController = vc!
            (UIApplication.shared.delegate as! AppDelegate).window?.makeKeyAndVisible()
            break
        case 1:
            let vc = self.storyboard?.instantiateViewController(withIdentifier: "MessageListVC")
            DefaultTabBarController.singleton?.navigationController?.pushViewController(vc!, animated: true)
            break
        case 2:
            let vc = self.storyboard?.instantiateViewController(withIdentifier: "ParentControlVC")
            DefaultTabBarController.singleton?.navigationController?.pushViewController(vc!, animated: true)
            break
        case 3:
            let vc = self.storyboard?.instantiateViewController(withIdentifier: "ReminderVC")
            DefaultTabBarController.singleton?.navigationController?.pushViewController(vc!, animated: true)
            break
        case 4:
            let vc = self.storyboard?.instantiateViewController(withIdentifier: "newsView")
            DefaultTabBarController.singleton?.navigationController?.pushViewController(vc!, animated: true)
            break
        case 5:
            // set up activity view controller
            let txtToShare = [ "سلام دوست عزیز، می خوای بفهمی چطور رانندگی می کنی؟ همین الان اپلیکیشن کارنو رو نصب کن . رانندگی کن و جایزه بگیر.\nلینک دانلود کافه بازار :\nhttp://cafebazaar.ir/app/?id=com.ratengroup.careno&ref=share\nلینک دانلود گوگل پلی :\nhttps://play.google.com/store/apps/details?id=com.ratengroup.careno" ]
            let activityViewController = UIActivityViewController(activityItems: txtToShare, applicationActivities: nil)
            activityViewController.popoverPresentationController?.sourceView = self.view // so that iPads won't crash
            
            // exclude some activity types from the list (optional)
            activityViewController.excludedActivityTypes = [ UIActivityType.airDrop, UIActivityType.postToFacebook ]
            
            // present the view controller
            self.present(activityViewController, animated: true, completion: nil)
            break
        case 6:
            let vc = self.storyboard?.instantiateViewController(withIdentifier: "StatisticViewController")
            DefaultTabBarController.singleton?.navigationController?.pushViewController(vc!, animated: true)
            break
        case 7:
            let vc = self.storyboard?.instantiateViewController(withIdentifier: "RewardsVC")
            DefaultTabBarController.singleton?.navigationController?.pushViewController(vc!, animated: true)
            break
        case 8:
            let vc = self.storyboard?.instantiateViewController(withIdentifier: "IntroVC") as! IntroVC
            self.present(vc, animated: true, completion: nil)
            break
        case 9:
            let vc = self.storyboard?.instantiateViewController(withIdentifier: "WebViewVC") as! WebViewVC
            vc.urlStr = "http://31.184.132.233/careno/faq"
            DefaultTabBarController.singleton?.navigationController?.pushViewController(vc, animated: true)
            break
        case 10:
            let vc = self.storyboard?.instantiateViewController(withIdentifier: "AboutUsVC")
            DefaultTabBarController.singleton?.navigationController?.pushViewController(vc!, animated: true)
            break
        default:
            let vc = self.storyboard?.instantiateViewController(withIdentifier: "EmptyNavController")
            DefaultTabBarController.singleton?.navigationController?.pushViewController(vc!, animated: true)
            break
        }
        
        MainViewController.singleton?.setDrawerState(.closed, animated: true)
    }
    
    /*
     */
    override func tableView(_ tableView: UITableView, viewForHeaderInSection section: Int) -> UIView? {
        
        let cell = tableView.dequeueReusableCell(withIdentifier: "headerTemplate") as! SideMenuHeader
        
        cell.selectedBackgroundView?.backgroundColor=UIColor.clear
        cell.backgroundColor = UIColor.gray
        cell.tintColor = UIColor.white
        
        cell.lblTitle?.text = Utility.lbl_setting
        cell.lblTitle?.textColor = UIColor.white
        cell.lblTitle?.tintColor = UIColor.white
        cell.lblTitle.font = UIFont(name: "IRANSans", size: 25)
        
        let imageName = UIImage(named: Utility.img_setting)
        cell.imgIcon?.image  = imageName
        
        //        cell.autoresizingMask = .flexibleHeight // not worked !!
        //
        //        self.tableView.beginUpdates()
        //        self.tableView.setNeedsLayout()
        //        self.tableView.endUpdates()
        
        let tapGesture = UITapGestureRecognizer(target: self, action: #selector(self.headerSelector(sender:)))
        cell.addGestureRecognizer(tapGesture)
        return cell
    }
    
    
    @objc func headerSelector(sender : UITapGestureRecognizer) {
        let vc = self.storyboard?.instantiateViewController(withIdentifier: "ProfileVC")
        DefaultTabBarController.singleton?.navigationController?.pushViewController(vc!, animated: true)
        MainViewController.singleton?.setDrawerState(.closed, animated: true)
    }
}


    //
    //  DefaultTabBarController.swift
    //  driver-behavior-ios-app
    //
    //  Created by Amir Mansouri Fard (amf.fard@gmail.com) on 6/19/18.
    //  Copyright © 2018 Amir Mansouri Fard (amf.fard@gmail.com). All rights reserved.
    //
    
    import Foundation
    import UIKit
    
    class DefaultTabBarController : UITabBarController{
        
        static var singleton : DefaultTabBarController?
        
        override func viewDidLoad() {
            super.viewDidLoad();
            DefaultTabBarController.singleton = self
//            setupTabBarItems();
            styleTabbarItems();
            
            let menuBtn = UIBarButtonItem(image: #imageLiteral(resourceName: "mn_switch"), landscapeImagePhone: #imageLiteral(resourceName: "mn_switch"), style: .plain, target: self, action: #selector(self.menuSelector(sender:)))
            menuBtn.tintColor = .white
            self.navigationItem.rightBarButtonItem = menuBtn
            
            self.selectedIndex = 4
        }
        
        func selectTabItem( clazz : Any) -> UIViewController?{
            let key = String(describing:type(of:clazz)).replacingOccurrences(of: ".Type.Type", with: "")
            for controller in self.viewControllers!{
                if String(describing:type(of:controller)) == key      {
                    self.selectedViewController = controller;
                    return controller;
                    
                }
            }
            return nil;
        }
        
//        func setupTabBarItems(){
//
//            if let numberOfCtrls = self.viewControllers?.count {
//                var viewCtrls : Array<UIViewController?> = Array(repeating:nil,count:numberOfCtrls)
//                for controller in self.viewControllers!{
//                    if controller is HomeViewController      {
//                        self.selectedViewController = controller;
//                    }
//                    if let ctrl = controller as? DefaultBarViewController{
//                        let key = String(describing:type(of:ctrl.self));
//                        if let value = theItems[key]{
//                            viewCtrls[value[0]-1] = controller
//                        }
//                    }
//                }
//                var i : Int = 0;
//                for controller in viewCtrls{
//                    if let ctrl = controller{
//                        self.viewControllers?[i] = ctrl
//                        i = i+1;
//                    }
//                }
//            }
//        }
        
        func styleTabbarItems(){
            for item in self.tabBar.subviews{
                let type: UIView? = item as? UIView
                UITabBarItem.appearance().setTitleTextAttributes([NSAttributedStringKey.font:UIFont(name: Utility.font_family, size: CGFloat(Utility.font_size) )],for:.normal);
                
                UITabBarItem.appearance().setTitleTextAttributes([NSAttributedStringKey.font:UIFont(name: Utility.font_family, size: CGFloat(Utility.font_size_selected) )],for:.selected);
                
                UITabBarItem.appearance().setTitleTextAttributes([NSAttributedStringKey.font: UIFont(name: "IRANSans", size: 10)!], for: .normal)
                UITabBarItem.appearance().setTitleTextAttributes([NSAttributedStringKey.font: UIFont(name: "IRANSans", size: 10)!], for: .selected)
                
                self.tabBar.tintColor = UIColor.white;
                self.tabBar.unselectedItemTintColor = UIColor.gray;
                //self.tabBar.selected =
                
                if type != nil{
                    for eleman in item.subviews{
                        let type = eleman as? UILabel
                        if type != nil{
                            eleman.contentMode = .center
                        }else{
                            let type = eleman as? UIImageView
                            if type != nil{
                                eleman.contentMode = .center
                            }
                        }
                    }
                }
            }
            
        }
        
        
        @objc func menuSelector(sender : UIBarButtonItem) {
            MainViewController.singleton?.setDrawerState(.opened, animated: true)
        }
        //    override func tabBar(tabBar: UITabBar, didSelectItem, item: UITabBarItem) {
        //        if item.tag == 1{
        //            //do our animations
        //        }
        //    }
    }

//
//  RootViewController.swift
//  driver-behavior-ios-app
//
//  Created by Amir Mansouri Fard (amf.fard@gmail.com) on 7/9/18.
//  Copyright © 2018 Amir Mansouri Fard (amf.fard@gmail.com). All rights reserved.
//

import UIKit

class RootViewController: UIViewController{
    
    static var instance : RootViewController?;
    
    private var current : UIViewController;
    
    required init?(coder aDecoder: NSCoder) {
        self.current = RootViewController.getCurrentView();
        super.init(coder:aDecoder);
        RootViewController.instance = self;
    }
    
    override func viewDidLoad() {
        super.viewDidLoad();
        showView(current)
        
    }
    
    static func getCurrentView()-> UIViewController{
        
        let currentView : UIViewController;
        
        let defaults = UserDefaults.standard;
        if let token = defaults.string(forKey:K.API_TOKEN_KEY){
            currentView = getMainView();
        }else{
            currentView = getLoginView();
        }
        
        return currentView;
        
    }
    
    static func getMainView()-> UIViewController{
        
        let currentView : UIViewController;
        let storyboard = UIStoryboard(name:"Main",bundle:nil);
        
        currentView = storyboard.instantiateViewController(withIdentifier: "mainView");
        return currentView;
        
    }
    
    static func getLoginView()-> UIViewController{
        
        let currentView : UIViewController;
        let storyboard = UIStoryboard(name:"Main",bundle:nil);
        
        currentView = storyboard.instantiateViewController(withIdentifier: "loginView");
        return currentView;
        
    }

    func showMainView(){
        if let instance = RootViewController.instance {
            instance.current.removeFromParentViewController();
            instance.current = RootViewController.getMainView();
        }
    }
    
    func showLoginView(){
        if let instance = RootViewController.instance {
            instance.current = RootViewController.getLoginView();
        }
    }
    
    func showView(_ vc : UIViewController)-> Void{
        addChildViewController(vc);
        vc.view.frame = view.bounds;
        view.addSubview(vc.view);
        vc.didMove(toParentViewController: self);
    }

    func replaceView(_ vc : UIViewController)->Void{
        if let curr : UIViewController = current {
            curr.removeFromParentViewController();
        }
        showView(vc);
    }
}

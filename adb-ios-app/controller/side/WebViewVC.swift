//
//  FAQVC.swift
//  driver-behavior-ios-app
//
//  Created by Amir Mansouri Fard (amf.fard@gmail.com) on 7/31/18.
//  Copyright © 2018 Amir Mansouri Fard (amf.fard@gmail.com). All rights reserved.
//

import UIKit

class WebViewVC: UIViewController {
    
    @IBOutlet weak var webView : UIWebView!
    
    var urlStr = String()
    var loading : UIActivityIndicatorView!

    override func viewDidLoad() {
        super.viewDidLoad()
        
        ShowLoading(superView: self.view, position: .center, size: 40) { (loading) in
            self.loading = loading
            let url = URL(string: self.urlStr)!
            let req = URLRequest(url: url)
            self.webView.loadRequest(req)
            self.webView.delegate = self
        }
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
}


extension WebViewVC : UIWebViewDelegate {
    func webViewDidStartLoad(_ webView: UIWebView) {
        loading.startAnimating()
    }
    
    func webViewDidFinishLoad(_ webView: UIWebView) {
        loading.stopAnimating()
    }
    
    func webView(_ webView: UIWebView, didFailLoadWithError error: Error) {
        loading.stopAnimating()
    }
}

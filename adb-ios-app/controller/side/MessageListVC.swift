//
//  MessageListVC.swift
//  driver-behavior-ios-app
//
//  Created by Amir Mansouri Fard (amf.fard@gmail.com) on 7/31/18.
//  Copyright © 2018 Amir Mansouri Fard (amf.fard@gmail.com). All rights reserved.
//

import UIKit

class MessageListVC: UIViewController {

    @IBOutlet weak var table : UITableView!
    
    var paginationNumber = 0
    let selfController = MessageListC()
    var nothgin : UILabel!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.navigationItem.title = "پیام ها"
        selfController.getMessages(context: self)
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

}

extension MessageListVC : UIScrollViewDelegate {
    func scrollViewDidEndDragging(_ scrollView: UIScrollView, willDecelerate decelerate: Bool) {
        // UITableView only moves in one direction, y axis
        let currentOffset = scrollView.contentOffset.y
        let maximumOffset = scrollView.contentSize.height - scrollView.frame.size.height
        
        // Change 10.0 to adjust the distance from bottom
        if maximumOffset - currentOffset <= 10.0 {
            paginationNumber += 1
            selfController.getMessages(context: self)
        }
    }
}

extension MessageListVC : UITableViewDelegate , UITableViewDataSource {
    func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if selfController.result == nil || selfController.result.messages == nil {
            return 0
        }
        
        return selfController.result.messages!.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "Cell", for: indexPath) as! MessageCell
        cell.configureCell(model: selfController.result.messages![indexPath.row])
        return cell
    }
    
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        tableView.deselectRow(at: indexPath, animated: true)
    }
}


class MessageListC {
    var result : Messages!
    func getMessages(context : MessageListVC) {
        ShowLoading(superView: context.view, position: .center, size: 40) { (loading) in
            RemoteService.listMessages(context.paginationNumber, completion: { (response) in
                loading.stopAnimating()
                guard response.error == nil else {
                    print(response.error.debugDescription)
                    let alert = UIAlertController(title: "خطا", message: "عملیات با خطا مواجه شد لطفا دوباره تلاش کنید", preferredStyle: .alert)
                    alert.addAction(UIAlertAction(title: "باشه", style: .default, handler: { (action) in
                        alert.dismiss(animated: true, completion: nil)
                    }))
                    
                    context.present(alert, animated: true, completion: nil)
                    return
                }
                
                do {
                    context.table.reloadData()
                    
                    if self.result == nil {
                        self.result = try JSONDecoder().decode(Messages.self , from : response.data!)
                    } else {
                        let res = try JSONDecoder().decode(Messages.self , from : response.data!)
                        if res.messages != nil && !res.messages!.isEmpty {
                            self.result.messages!.append(contentsOf: res.messages!)
                        }
                    }
                    
                    if self.result.messages == nil || self.result.messages!.isEmpty {
                        context.nothgin = showNothingLabel(context: context, text: "پیامی یافت نشد")
                        context.view.addSubview(context.nothgin)
                    } else {
                        if context.nothgin != nil {
                            context.nothgin.removeFromSuperview()
                            context.nothgin = nil
                        }
                    }
                    context.table.reloadData()
                    NOTREADCOUNT = 0
                    self.setReadMessage()
                } catch let err {
                    print(err.localizedDescription)
                }
            })
        }
    }
    
    
    func setReadMessage() {
        RemoteService.readMessages { (response) in
            print(response.response.debugDescription)
        }
    }
}

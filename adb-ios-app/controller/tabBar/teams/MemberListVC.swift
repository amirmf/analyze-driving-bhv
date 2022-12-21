//
//  MemberListVC.swift
//  driver-behavior-ios-app
//
//  Created by Amir Mansouri Fard (amf.fard@gmail.com) on 8/11/18.
//  Copyright © 2018 Amir Mansouri Fard (amf.fard@gmail.com). All rights reserved.
//

import UIKit

class MemberListVC: UIViewController {

    @IBOutlet weak var table : UITableView!
    
    static var singleton : MemberListVC!
    
    var nothgin : UILabel!
    
    let selfController = MemberListC()
    
    var groupId : Int64!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        MemberListVC.singleton = self
        
        selfController.getMembers(context: self)
    }

    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        let barButton = UIBarButtonItem(image: #imageLiteral(resourceName: "icon_trash"), landscapeImagePhone: #imageLiteral(resourceName: "icon_trash"), style: .plain, target: self, action: #selector(self.deleteGroupSelector(sender:)))
        self.navigationItem.rightBarButtonItem = barButton
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

    @objc func deleteGroupSelector(sender : UIBarButtonItem) {
        let alert = UIAlertController(title: "حذف گروه", message: "آیا از حذف این گروه اطمینان دارید؟", preferredStyle: UIAlertControllerStyle.alert)
        alert.addAction(UIAlertAction(title: "خیر", style: .default, handler: { (action) in
            alert.dismiss(animated: true, completion: nil)
        }))
        
        alert.addAction(UIAlertAction(title: "بله", style: .default, handler: { (action) in
            ShowLoading(superView: self.view, position: .center, size: 40, afterLoading: { (loading) in
                RemoteService.deleteGroup(Int(self.groupId), completion: { (response) in
                    loading.stopAnimating()
                    self.navigationController?.popViewController(animated: true)
                })
            })
        }))
        
        self.present(alert, animated: true, completion: nil)
    }
    
    @IBAction func addMemberSelector(sender : UIButton) {
        let vc = self.storyboard?.instantiateViewController(withIdentifier: "InviteVC") as! InviteVC
        vc.groupId = self.groupId
        self.navigationController?.pushViewController(vc, animated: true)
    }
}

extension MemberListVC : UITableViewDelegate , UITableViewDataSource {
    func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if selfController.groupInfo == nil {
            return 0
        }
        
        return selfController.groupInfo.userGroupItems.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "Cell", for: indexPath) as! MemberCell
        cell.configureCell(model: selfController.groupInfo.userGroupItems[indexPath.row])
        cell.selectionStyle = .none
        cell.deleteBtn.isHidden = !selfController.groupInfo.owner
        return cell
    }
    
}

class MemberListC {
    var groupInfo : UserGroupInfo!
    
    func getMembers(context : MemberListVC) {
        ShowLoading(superView: context.view, position: .center, size: 40) { (loading) in
            RemoteService.getGroup(context.groupId, completion: { (response) in
                loading.stopAnimating()
                guard response.error == nil else {
                    print(response.error.debugDescription)
                    return
                }
                
                do {
                    self.groupInfo = try JSONDecoder().decode(UserGroupInfo.self, from: response.data!)
                    
                    if self.groupInfo.userGroupItems.isEmpty {
                        context.nothgin = showNothingLabel(context: context, text: "موردی یافت نشد")
                        context.view.addSubview(context.nothgin)
                    } else {
                        if context.nothgin != nil {
                            context.nothgin.removeFromSuperview()
                            context.nothgin = nil
                        }
                    }
                    
                    context.table.reloadData()
                } catch let err {
                    print(err.localizedDescription)
                }
            })
        }
    }
}

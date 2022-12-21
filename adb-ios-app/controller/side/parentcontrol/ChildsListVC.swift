//
//  ChildsListVC.swift
//  driver-behavior-ios-app
//
//  Created by Amir Mansouri Fard (amf.fard@gmail.com) on 9/10/18.
//  Copyright © 2018 Amir Mansouri Fard (amf.fard@gmail.com). All rights reserved.
//

import UIKit
import XLPagerTabStrip

class ChildsListVC: UIViewController , IndicatorInfoProvider {

    @IBOutlet weak var table : UITableView!
    
    let selfController = ChildsListC()
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        selfController.getChilds(context: self)
    }

    func indicatorInfo(for pagerTabStripController: PagerTabStripViewController) -> IndicatorInfo {
        return IndicatorInfo(title: "لیست فرزندان")
    }

}


extension ChildsListVC : UITableViewDelegate , UITableViewDataSource {
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
        cell.deleteBtn.isHidden = true
        return cell
    }
}


class ChildsListC {
    var result : ParentsList!
    func getChilds(context : ChildsListVC) {
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

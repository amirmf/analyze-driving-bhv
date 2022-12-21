//
//  MyTripViewController.swift
//  driver-behavior-ios-app
//
//  Created by Amir Mansouri Fard (amf.fard@gmail.com) on 6/28/18.
//  Copyright © 2018 Amir Mansouri Fard (amf.fard@gmail.com). All rights reserved.
//

import UIKit

class MyTripViewController: UIViewController, DefaultBarViewController {

    static var singleton : MyTripViewController!
    
    @IBOutlet weak var tableView: UITableView!
    
    let selfContoller = MyTripC()
    
    var nothgin : UILabel!
    
    var paginationNumber = 0
    
    override func viewDidLoad() {
        super.viewDidLoad()
        MyTripViewController.singleton = self
        self.tableView.separatorStyle = .none
        selfContoller.getTrips(context: self)
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    override func viewDidAppear(_ animated:Bool) {
        super.viewDidAppear(animated);
        self.navigationController?.navigationBar.topItem?.title = "سفرهای من";
        self.navigationController?.navigationBar.alpha = 1;
    }

}


extension MyTripViewController : UITableViewDelegate , UITableViewDataSource {
    func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if selfContoller.triplist == nil {
            return 0
        }
        
        
        return selfContoller.triplist.trips.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "Cell", for: indexPath) as! TripsCell
        cell.configureCell(model: selfContoller.triplist.trips[indexPath.row])
        return cell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        tableView.deselectRow(at: indexPath, animated: true)
        let vc = self.storyboard?.instantiateViewController(withIdentifier: "TripDetailsVC") as! TripDetailsVC
        vc.tripID = selfContoller.triplist.trips[indexPath.row].id
        DefaultTabBarController.singleton?.navigationController?.pushViewController(vc, animated: true)
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return 130
    }
}

extension MyTripViewController : UIScrollViewDelegate {
    func scrollViewDidEndDragging(_ scrollView: UIScrollView, willDecelerate decelerate: Bool) {
        // UITableView only moves in one direction, y axis
        let currentOffset = scrollView.contentOffset.y
        let maximumOffset = scrollView.contentSize.height - scrollView.frame.size.height
        
        // Change 10.0 to adjust the distance from bottom
        if maximumOffset - currentOffset <= 10.0 {
            paginationNumber += 1
            selfContoller.getTrips(context: self)
        }
    }
}

class MyTripC {
    var triplist : TripsList!
    
    func getTrips(context : MyTripViewController) {
        ShowLoading(superView: context.view, position: .center, size: 40) { (loading) in
            RemoteService.listTrips(context.paginationNumber, completion: { (response) in
                loading.stopAnimating()
                guard response.error == nil else {
                    print(response.error.debugDescription)
                    return
                }
                
                if self.triplist == nil {
                    do {
                        self.triplist = try
                            JSONDecoder().decode(TripsList.self,from:response.data!)
                    } catch let err {
                        print(err.localizedDescription)
                    }
                } else {
                    do {
                        let result = try
                            JSONDecoder().decode(TripsList.self,from:response.data!)
                        self.triplist.trips += result.trips
                    } catch let err {
                        print(err.localizedDescription)
                    }
                }
                
                if self.triplist.trips.isEmpty {
                    context.nothgin = showNothingLabel(context: context, text: "هنوز سفری ثبت نکرده اید")
                    context.view.addSubview(context.nothgin)
                } else {
                    if context.nothgin != nil {
                        context.nothgin.removeFromSuperview()
                        context.nothgin = nil
                    }
                }
                
                context.tableView.reloadData()
            })
        }
    }
}

//
//  TripsCell.swift
//  driver-behavior-ios-app
//
//  Created by Amir Mansouri Fard (amf.fard@gmail.com) on 7/28/18.
//  Copyright © 2018 Amir Mansouri Fard (amf.fard@gmail.com). All rights reserved.
//

import UIKit

class TripsCell: UITableViewCell {

    @IBOutlet weak var dateLBL: UILabel!
    @IBOutlet weak var timeLBL: UILabel!
    @IBOutlet weak var thumbIMG: UIImageView!
    @IBOutlet weak var titleLBL: UILabel!
    
    var model : Trip!
    
    func configureCell(model: Trip) {
        self.model = model
        titleLBL.text = model.name
        timeLBL.text = convertDate(dateString: model.tripStartDate)
        dateLBL.text = convertDate(dateString: model.tripEndDate)
    }

    @IBAction func trashSelector(_ sender: UIButton) {
        let alert = UIAlertController(title: "حذف سفر", message: "آیا از حذف این سفر اطمینان دارید؟", preferredStyle: .alert)
        alert.addAction(UIAlertAction(title: "انصراف", style: .default, handler: { (action) in
            alert.dismiss(animated: true, completion: nil)
        }))
        
        alert.addAction(UIAlertAction(title: "حذف سفر", style: .default, handler: { (action) in
            alert.dismiss(animated: true, completion: nil)
            ShowLoading(superView: MyTripViewController.singleton.view, position: .center, size: 40, afterLoading: { (loading) in
                RemoteService.deleteTrip(tripId: self.model.id, completion: { (response) in
                    loading.stopAnimating()
                    if response.response != nil && response.response!.statusCode == 200 {
                        MyTripViewController.singleton.paginationNumber = 0
                        MyTripViewController.singleton.selfContoller.triplist = nil
                        MyTripViewController.singleton.selfContoller.getTrips(context: MyTripViewController.singleton)
                    } else {
                        let alert = UIAlertController(title: "خطا", message: "عملیات با خطا مواجه شد لطفا دوباره تلاش کنید", preferredStyle: .alert)
                        alert.addAction(UIAlertAction(title: "باشه", style: .default, handler: { (action) in
                            alert.dismiss(animated: true, completion: nil)
                        }))
                        
                        MyTripViewController.singleton.present(alert, animated: true, completion: nil)
                    }
                })
            })
        }))
        
        MyTripViewController.singleton.present(alert, animated: true, completion: nil)
    }
}

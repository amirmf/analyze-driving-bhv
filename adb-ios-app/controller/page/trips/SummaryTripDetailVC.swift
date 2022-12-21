//
//  SummaryTripDetailVC.swift
//  driver-behavior-ios-app
//
//  Created by Amir Mansouri Fard (amf.fard@gmail.com) on 7/28/18.
//  Copyright © 2018 Amir Mansouri Fard (amf.fard@gmail.com). All rights reserved.
//

import UIKit
import XLPagerTabStrip

class SummaryTripDetailVC: UIViewController , IndicatorInfoProvider {

    @IBOutlet weak var aveSpeedLBL: UILabel!
    @IBOutlet weak var pointLBL: UILabel!
    @IBOutlet weak var topSpeedLBL: UILabel!
    @IBOutlet weak var aveKilometersLBL: UILabel!
    @IBOutlet weak var stopTimeLBL: UILabel!
    @IBOutlet weak var distanceLBL: UILabel!
    @IBOutlet weak var durationLBL: UILabel!
    @IBOutlet weak var startTimeLBL: UILabel!
    @IBOutlet weak var endTimeLBL: UILabel!
    @IBOutlet weak var hightSpeedDistLBL: UILabel!
    @IBOutlet weak var stopTimeBottomLBL: UILabel!
    
    var tripId : Int!
    
    let selfController = SummaryTripDetailC()
    override func viewDidLoad() {
        super.viewDidLoad()
        selfController.getTripDetails(context: self)
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

    
    func indicatorInfo(for pagerTabStripController: PagerTabStripViewController) -> IndicatorInfo {
        return IndicatorInfo(title: "خلاصه")
    }
    
    func setDataToWidgets(model: TripDetail) {
        self.pointLBL.text = "\(model.score)"
        self.aveSpeedLBL.text = "میانگین سرعت\n\n\(round(model.averageSpeed * 100) / 100)\n\nkm/h"
        self.topSpeedLBL.text = "حداکثر سرعت\n\n\(model.maxSpeed)\n\nkm/h"
        self.aveKilometersLBL.text = "\(round(model.distance * 100) / 100)"
        let (sH , sM , _) = secondsToHoursMinutesSeconds(seconds: Int(model.duration - model.movementTime))
        let (h,m ,_) = secondsToHoursMinutesSeconds(seconds: Int(model.duration))
        self.stopTimeLBL.text = "\(sH):\(sM)"
        self.distanceLBL.text = "\(round(model.distance * 100) / 100)"
        self.durationLBL.text = "\(h):\(m)"
        self.startTimeLBL.text = "\(convertDate(dateString: model.tripStartDate))"
        self.endTimeLBL.text = "\(convertDate(dateString: model.tripEndDate))"
        self.startTimeLBL.text = "\(convertDate(dateString: model.tripStartDate))"
        self.hightSpeedDistLBL.text = "\(model.maxIdleTime)"
        self.stopTimeBottomLBL.text = "\(model.duration - model.movementTime)"
    }
}

class SummaryTripDetailC {
    var tripDetail : TripDetail!
    
    func getTripDetails(context : SummaryTripDetailVC) {
        ShowLoading(superView: context.view, position: .center, size: 40) { (loading) in
            RemoteService.getTripDetails(context.tripId, completion: { (response) in
                loading.stopAnimating()
                guard response.error == nil else {
                    print(response.error.debugDescription)
                    return
                }
                print(String(data: response.data! , encoding: .utf8) ?? "" )
                if response.response!.statusCode <= 199 || response.response!.statusCode > 300 {
                    print(response.response!.statusCode)
                } else {
                    do {
                        self.tripDetail = try
                            JSONDecoder().decode(TripDetail.self,from:response.data!)
                        context.setDataToWidgets(model: self.tripDetail)
                    } catch let err {
                        print(err.localizedDescription)
                    }
                }
            })
        }
    }
}

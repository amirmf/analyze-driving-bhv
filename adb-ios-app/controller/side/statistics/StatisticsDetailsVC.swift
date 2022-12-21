//
//  StatisticsDetailsVC.swift
//  driver-behavior-ios-app
//
//  Created by Amir Mansouri Fard (amf.fard@gmail.com) on 8/3/18.
//  Copyright © 2018 Amir Mansouri Fard (amf.fard@gmail.com). All rights reserved.
//

import UIKit
import drCharts
import XLPagerTabStrip

class StatisticsDetailsVC: UIViewController , IndicatorInfoProvider {
    @IBOutlet weak var pointLBL: UILabel!
    @IBOutlet weak var maxSpeedLBL: UILabel!
    @IBOutlet weak var aveSpeedLBL: UILabel!
    @IBOutlet weak var highSpeedLBL: UILabel!
    @IBOutlet weak var highSpeedTimeLBL: UILabel!
    @IBOutlet weak var dayNightChart: HorizontalStackBarChart!
    @IBOutlet weak var driverRidingChart: BarChart!
    @IBOutlet weak var aveKilometers: UILabel!
    @IBOutlet weak var stopTimeLBL: UILabel!
    @IBOutlet weak var distanceLBL: UILabel!
    @IBOutlet weak var timeLBL: UILabel!
    
    var tabTitle = ""
    var fromRange = ""
    var toRange = ""
    
    
    let yArray : NSMutableArray = [10 , 15, 2, 20 ,23, 18, 11, 9, 5, 4]
    let xArray : NSMutableArray = [0 , 2 , 4 , 8 , 10 , 12, 14 , 16, 18, 20]
    
    let selfController = StatisticsDetailsC()
    override func viewDidLoad() {
        super.viewDidLoad()

    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        selfController.getStatistics(context: self)
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    
    func indicatorInfo(for pagerTabStripController: PagerTabStripViewController) -> IndicatorInfo {
        return IndicatorInfo(title: tabTitle)
    }
    
    func setDataToWidgets(model : Statistic) {
        self.pointLBL.text = "\(model.score)"
        self.aveSpeedLBL.text = "میانگین سرعت\n\(round(model.averageSpeed * 100) / 100)\nkm/h"
        self.maxSpeedLBL.text = "حداکثر سرعت\n\(model.maxSpeed)\nkm/h"
        self.highSpeedTimeLBL.text = "درصد زمان سرعت غیر مجاز\n\(Int(model.highOverSpeedingTime))٪"
        self.highSpeedLBL.text = "سرعت بیشتر از حد مجاز\n\(Int(model.highOverSpeedCount))٪"
        self.aveKilometers.text = "\(round(model.distance * 100) / 100)"
        let (sH , sM , _) = secondsToHoursMinutesSeconds(seconds: Int(model.idleTime))
        let (h,m ,_) = secondsToHoursMinutesSeconds(seconds: Int(model.duration))
        self.stopTimeLBL.text = "\(sH):\(sM)"
        self.distanceLBL.text = "\(round(model.distance * 100) / 100)"
        self.timeLBL.text = "\(h):\(m)"
        self.dayNightChart.showLegend = true
        self.dayNightChart.legendViewType = LegendTypeHorizontal
        self.dayNightChart.showValueOnBarSlice = false
        self.dayNightChart.showCustomMarkerView = true
        self.dayNightChart.textFontSize = 13
        self.dayNightChart.textFont = UIFont(name: "IRANSans(FaNum)", size: self.dayNightChart.textFontSize)
        self.dayNightChart.delegate = self
        self.dayNightChart.dataSource = self
        self.dayNightChart.drawStackChart()
        
        self.driverRidingChart.showLegend = true
        self.driverRidingChart.legendViewType = LegendTypeHorizontal
        self.driverRidingChart.drawGridY = true
        self.driverRidingChart.drawGridX = false
        self.driverRidingChart.gridLineColor = .blue
        self.driverRidingChart.gridLineWidth = 0.3
        self.driverRidingChart.textFontSize = 13
        self.driverRidingChart.textColor = .darkGray
        self.driverRidingChart.textFont = UIFont(name: "IRANSans(FaNum)", size: self.dayNightChart.textFontSize)
        self.driverRidingChart.showCustomMarkerView = true
//        self.driverRidingChart.delegate = self
//        self.driverRidingChart.dataSource = self
        self.driverRidingChart.drawBarGraph()
    }
}

extension StatisticsDetailsVC : BarChartDelegate , BarChartDataSource {
    func didTapOnBarChart(withValue value: String!) {
        
    }
    
    func numberOfBarsToBePlotted() -> Int {
        return xArray.count
    }
    
    func colorForTheBar(withBarNumber barNumber: Int) -> UIColor! {
        return .blue
    }
    
    func xDataForBarChart() -> NSMutableArray! {
        return xArray
    }
    
    func widthForTheBar(withBarNumber barNumber: Int) -> CGFloat {
        return 30
    }
    
    func yDataForBar(withBarNumber barNumber: Int) -> NSMutableArray! {
        return yArray
    }
    
    func nameForTheBar(withBarNumber barNumber: Int) -> String! {
        return ""
    }
    
    func customViewForBarChartTouch(withValue value: NSNumber!) -> UIView! {
        let view = UIView()
        view.backgroundColor = .white
        view.layer.cornerRadius = 2
        view.layer.borderWidth = 0.5
        view.layer.borderColor = UIColor.darkGray.cgColor
        return view
    }
}

extension StatisticsDetailsVC : HorizontalStackBarChartDelegate , HorizontalStackBarChartDataSource {
    func colorForValueInStackChart(with index: Int) -> UIColor! {
        if index == 0 {
            return .blue
        } else {
            return .green
        }
    }
    
    func titleForValueInStackChart(with index: Int) -> String! {
        if index == 0 {
            return "شب \(Int(selfController.result.nightPercentage))٪"
        } else {
            return "روز \(Int(selfController.result.dayPercentage))٪"
        }
    }
    
    func numberOfValuesForStackChart() -> Int {
        return 2
    }
    
    func valueInStackChart(with index: Int) -> NSNumber! {
        if index == 0 {
            return NSNumber(value: selfController.result.nightPercentage)
        } else {
            return NSNumber(value: selfController.result.dayPercentage)
        }
    }
    
    func customViewForStackChartTouch(withValue value: NSNumber!) -> UIView! {
        let view = UIView()
        view.backgroundColor = .white
        view.layer.cornerRadius = 2
        view.layer.borderWidth = 0.5
        view.layer.borderColor = UIColor.darkGray.cgColor
        return view
    }
    
    func didTapOnHorizontalStackBarChart(withValue value: String!) {
        
    }
}

class StatisticsDetailsC {
    var result : Statistic!
    func getStatistics ( context : StatisticsDetailsVC ) {
        ShowLoading(superView: context.view, position: .center, size: 40) { (loading) in
            RemoteService.getStatistic(context.fromRange, context.toRange, completion: { (response) in
                loading.stopAnimating()
                guard response.error == nil else {
                    print(response.error.debugDescription)
                    return
                }
                
                do {
                    self.result = try JSONDecoder().decode(Statistic.self,from:response.data!)
                    context.setDataToWidgets(model: self.result)
                } catch let err {
                    print(err.localizedDescription)
                }
            })
        }
    }
}


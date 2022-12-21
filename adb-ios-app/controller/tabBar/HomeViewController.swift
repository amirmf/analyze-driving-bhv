//
//  HomeViewController.swift
//  driver-behavior-ios-app
//
//  Created by Amir Mansouri Fard (amf.fard@gmail.com) on 6/26/18.
//  Copyright © 2018 Amir Mansouri Fard (amf.fard@gmail.com). All rights reserved.
//

import UIKit
import drCharts

class HomeViewController: UIViewController, DefaultBarViewController {

    // MARK : Properties
    
    @IBOutlet weak var countDownRightLBL: UILabel!
    @IBOutlet weak var countDownLBL: UILabel!
    @IBOutlet weak var chart: HorizontalStackBarChart!
    @IBOutlet weak var btnStartDriving: UIButton!
    @IBOutlet weak var aveSpeedLBL: UILabel!
    @IBOutlet weak var tripCountLBL: UILabel!
    @IBOutlet weak var clockLBL: UILabel!
    @IBOutlet weak var imgSplash: UIImageView!
    @IBOutlet weak var welcomeLBL: UILabel!
    @IBOutlet weak var collection: UICollectionView!
    @IBOutlet weak var table: UITableView!
    @IBOutlet weak var imgNews: UIImageView!
    @IBOutlet weak var newsDescLBL: UILabel!
    
    let selfController = HomeC()
    
    var avgCounter :Int64 = 0
    var scoreCounter : Int64 = 0
    
    var timer : Timer!
    var scoreCounterFlag = false
    var avgCounterFlag = false
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.chart.showLegend = true
        self.chart.legendViewType = LegendTypeHorizontal
        self.chart.showValueOnBarSlice = true
        self.chart.showCustomMarkerView = true
        self.chart.textFontSize = 13
        self.chart.textFont = UIFont(name: "IRANSans(FaNum)", size: self.chart.textFontSize)
        
        self.table.estimatedRowHeight = 40
        self.table.rowHeight = UITableViewAutomaticDimension
        
        selfController.getHomeData(context: self)
        
//        self.btnStartDriving.layer.borderWidth = self.btnStartDriving.frame.size.height/10;
        self.btnStartDriving.layer.borderColor = K.color_gray_light?.cgColor;
        
        let topConstraint = NSLayoutConstraint(item:self.btnStartDriving,
                                               attribute:NSLayoutAttribute.top,
                                               relatedBy: NSLayoutRelation.equal ,
                                               toItem:self.imgSplash,
                                               attribute:NSLayoutAttribute.bottom,
                                               multiplier:1,
                                               constant:-self.btnStartDriving.frame.size.height/2);
        
        NSLayoutConstraint.activate([topConstraint]);
        
        //self.imgNews.sizeToImage();

        // Do any additional setup after loading the view.
    }

    override func viewDidAppear(_ animated:Bool) {
        super.viewDidAppear(animated);
        self.navigationController?.navigationBar.topItem?.title = "";
        self.navigationController?.navigationBar.alpha = 1.0;
        // RemoteService.listChallenges(callback: ListChallengeCallBack(self));
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

    @IBAction func drivingSelector(_ sender: RadiusButton) {
        let vc = self.storyboard?.instantiateViewController(withIdentifier: "DrivingViewController")
        self.navigationController?.pushViewController(vc!, animated: true)
    }
    
    
    func scoreCounterObserver() {
        UIView.animate(withDuration: 0.2, animations: {
            self.countDownLBL.text = "\(self.scoreCounter)"
            self.scoreCounter = self.scoreCounterFlag ? self.scoreCounter - 1 : self.scoreCounter + 1
        }) { (flag) in
            DispatchQueue.main.asyncAfter(deadline: .now() + 0.1, execute: {
                if self.scoreCounter == self.selfController.result.score + 7 {
                    self.scoreCounterFlag = true
                }
                
                if self.scoreCounter > self.selfController.result.score - 1 {
                    self.scoreCounterObserver()
                }
            })
        }
    }
    
    func avrageCounterObserver() {
        UIView.animate(withDuration: 0.2, animations: {
            self.countDownRightLBL.text = "\(self.avgCounter)"
            self.avgCounter = self.avgCounterFlag ? self.avgCounter - 1 : self.avgCounter + 1
        }) { (flag) in
            DispatchQueue.main.asyncAfter(deadline: .now() + 0.1
                , execute: {
                if self.avgCounter == self.selfController.result.averageSpeed + 7 {
                    self.avgCounterFlag = true
                }
                
                if self.avgCounter > self.selfController.result.averageSpeed - 1{
                    self.avrageCounterObserver()
                }
            })
        }
    }
}

extension HomeViewController : HorizontalStackBarChartDataSource , HorizontalStackBarChartDelegate {
    func colorForValueInStackChart(with index: Int) -> UIColor! {
        if index == 0 {
            return .blue
        } else {
            return .green
        }
    }
    
    func titleForValueInStackChart(with index: Int) -> String! {
        if index == 0 {
            return "هفته \(Int(selfController.result.weekPercentage))٪"
        } else {
            return "آخر هفته \(Int(selfController.result.weekendPercentage))٪"
        }
    }
    
    func numberOfValuesForStackChart() -> Int {
        return 2
    }
    
    func valueInStackChart(with index: Int) -> NSNumber! {
        if index == 0 {
            return NSNumber(value: Int(selfController.result.weekPercentage))
        } else {
            return NSNumber(value: Int(selfController.result.weekendPercentage))
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

extension HomeViewController : UICollectionViewDelegate , UICollectionViewDataSource , UICollectionViewDelegateFlowLayout {
    func numberOfSections(in collectionView: UICollectionView) -> Int {
        return 1
    }
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        if selfController.result == nil || selfController.result.rewards == nil {
            return 0
        }
        
        return selfController.result.rewards!.count
    }
    
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "Cell", for: indexPath) as! RewardHomeCell
        cell.configureCell(model: selfController.result.rewards![indexPath.row])
        return cell
    }
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
        return CGSize(width: self.view.width/3.3, height: 170)
    }
    
    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        collectionView.deselectItem(at: indexPath, animated: true)
        
        let vc = self.storyboard?.instantiateViewController(withIdentifier: "EmptyNavController")
        self.navigationController?.pushViewController(vc!, animated: true)
    }
}

extension HomeViewController : UITableViewDelegate , UITableViewDataSource {
    func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if selfController.result == nil || selfController.result.challengeItems == nil {
            return 0
        }
        
        return selfController.result.challengeItems!.count
    }
    
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "Cell", for: indexPath) as! ChallengeCell
        let model = selfController.result.challengeItems![indexPath.row]
        cell.imgChal.kf.setImage(with: URL(string: model.icon))
        cell.lblPpl.text = "\(model.participantCount) نفر"
        cell.lblStatus.text = model.dayLeft == 0 ? "پایان یافته" : "\(model.dayLeft) روز دیگر"
        cell.lblTitle.text = model.name
        return cell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        tableView.deselectRow(at: indexPath, animated: true)
        let detailView = self.storyboard?.instantiateViewController(withIdentifier: "challengeDetail") as! ChallengeDetailViewController;
        detailView.challenge = selfController.result.challengeItems![indexPath.row]
        self.navigationController?.pushViewController(detailView, animated: true)
    }
}

class HomeC {
    var result : Home!
    func getHomeData(context : HomeViewController) {
        ShowLoading(superView: context.view, position: .center, size: 40) { (loading) in
            RemoteService.getHome(completion: { (response) in
                loading.stopAnimating()
                guard response.error == nil else {
                    print(response.error.debugDescription)
                    return
                }
                
                do {
                    print(String(data: response.data!, encoding : .utf8) ?? "")
                    self.result = try
                        JSONDecoder().decode(Home.self,from:response.data!)
                    
                    if self.result.news != nil {
                        context.imgNews.kf.setImage(with: URL(string: self.result.news!.image), placeholder: nil, options: nil, progressBlock: nil, completionHandler: { (image, error, cache, url) in
                            if image == nil {
                                context.imgNews.image = #imageLiteral(resourceName: "news")
                            } else {
                                context.imgNews.image = image
                            }
                        })
                        
                        context.welcomeLBL.text = self.result.news!.title
                    }
                    context.collection.reloadData()
                    context.table.reloadData()
                    context.clockLBL.text = "\(self.result.sumTimeDuration)h"
                    context.aveSpeedLBL.text = "\(self.result.averageSpeed)km/h"
                    context.tripCountLBL.text = "\(self.result.tripCount)"
                    context.chart.delegate = context
                    context.chart.dataSource = context
                    context.chart.showValueOnBarSlice = false
                    context.chart.drawStackChart()
                    DispatchQueue.main.asyncAfter(deadline: .now() + 1, execute: {
                        context.scoreCounter = self.result.score
                        context.avgCounter = self.result.averageSpeed
                        context.scoreCounterObserver()
                        context.avrageCounterObserver()
                    })
                } catch let err {
                    print(err.localizedDescription)
                }
            })
        }
    }
}

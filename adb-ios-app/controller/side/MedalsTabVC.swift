//
//  MedalsTabVC.swift
//  driver-behavior-ios-app
//
//  Created by Amir Mansouri Fard (amf.fard@gmail.com) on 8/1/18.
//  Copyright © 2018 Amir Mansouri Fard (amf.fard@gmail.com). All rights reserved.
//

import UIKit
import XLPagerTabStrip

class MedalsTabVC: UIViewController, IndicatorInfoProvider {

    @IBOutlet weak var collection : UICollectionView!
    var nothgin : UILabel!
    let selfController = MedalsTabC()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        selfController.getMedals(context: self)
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func indicatorInfo(for pagerTabStripController: PagerTabStripViewController) -> IndicatorInfo {
        return IndicatorInfo(stringLiteral: "مدال ها")
    }
}

extension MedalsTabVC : UICollectionViewDelegate , UICollectionViewDataSource, UICollectionViewDelegateFlowLayout {
    func numberOfSections(in collectionView: UICollectionView) -> Int {
        return 1
    }
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        if selfController.result == nil {
            return 0
        }
        
        return selfController.result.rewards.count
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "Cell", for: indexPath) as! MedalCell
        cell.configureCell(model: selfController.result.rewards[indexPath.row])
        cell.titleLBL.minimumScaleFactor = 0.5
        cell.titleLBL.adjustsFontSizeToFitWidth = true
        return cell
    }
    
    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        collectionView.deselectItem(at: indexPath, animated: true)
        let alert = UIAlertController(title: "", message: selfController.result.rewards[indexPath.row].description, preferredStyle: UIAlertControllerStyle.alert)
        alert.addAction(UIAlertAction(title: "باشه", style: .default, handler: { (action) in
            alert.dismiss(animated: true, completion: nil)
        }))
        
        self.present(alert, animated: true, completion: nil)
    }
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
        return CGSize(width: collectionView.width / 3.3, height: 180)
    }
}

class MedalsTabC {
    var result : Medals!
    func getMedals(context : MedalsTabVC) {
        ShowLoading(superView: context.view, position: .center, size: 40) { (loading) in
            RemoteService.listMedals(completion: { (response) in
                loading.stopAnimating()
                guard response.error == nil else {
                    print(response.error.debugDescription)
                    return
                }
                
                do {
                    self.result = try
                        JSONDecoder().decode(Medals.self,from:response.data!)
                    
                    if self.result.rewards.isEmpty {
                        context.nothgin = showNothingLabel(context: context, text: "هنوز سفری ثبت نکرده اید")
                        context.view.addSubview(context.nothgin)
                    } else {
                        if context.nothgin != nil {
                            context.nothgin.removeFromSuperview()
                            context.nothgin = nil
                        }
                    }
                    
                    context.collection.reloadData()
                } catch let err {
                    print(err.localizedDescription)
                }
            })
        }
    }
}

//
//  RewardTabCell.swift
//  driver-behavior-ios-app
//
//  Created by Amir Mansouri Fard (amf.fard@gmail.com) on 8/1/18.
//  Copyright © 2018 Amir Mansouri Fard (amf.fard@gmail.com). All rights reserved.
//

import UIKit

class RewardTabCell: UITableViewCell {

    @IBOutlet weak var thumbIMG: UIImageView!
    @IBOutlet weak var titleLBL: UILabel!
    @IBOutlet weak var textLBL: UILabel!
    @IBOutlet weak var collection: UICollectionView!
    
    var model : Reward!
    
    func configureCell(model : Reward) {
        self.model = model
        self.thumbIMG.kf.setImage(with: URL(string: model.rewardImage))
        self.titleLBL.text = model.name
        self.textLBL.text = model.description
        self.collection.delegate = self
        self.collection.dataSource = self
        self.collection.reloadData()
    }
    
    @IBAction func getRewards(sender : UIButton) {
        ShowLoading(superView: RewardsTabVC.singleton.view, position: .center, size: 40) { (loading) in
            RemoteService.enterReward(model.id) { (response) in
                loading.stopAnimating()
                if response.response != nil && response.response!.statusCode == 200 {
                    let alert = UIAlertController(title: "", message: "کد تخفیف جایزه به صندوق پیام کارنو ارسال می شود.", preferredStyle: .alert)
                    alert.addAction(UIAlertAction(title: "باشه", style: .default, handler: { (action) in
                        alert.dismiss(animated: true, completion: nil)
                    }))
                    
                    RewardsTabVC.singleton.present(alert, animated: true, completion: nil)
                    RewardsTabVC.singleton.selfController.getRewards(context: RewardsTabVC.singleton)
                } else {
                    let alert = UIAlertController(title: "خطا", message: "شما امکان دریافت این جایزه را ندارید", preferredStyle: .alert)
                    alert.addAction(UIAlertAction(title: "باشه", style: .default, handler: { (action) in
                        alert.dismiss(animated: true, completion: nil)
                    }))
                    
                    RewardsTabVC.singleton.present(alert, animated: true, completion: nil)
                }
            }
        }
    }
}


extension RewardTabCell : UICollectionViewDelegate , UICollectionViewDataSource , UICollectionViewDelegateFlowLayout {
    func numberOfSections(in collectionView: UICollectionView) -> Int {
        return 1
    }
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return self.model.requiredBadges.count
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "Cell", for: indexPath) as! BadgeCell
        cell.thumbIMG.kf.setImage(with: URL(string: model.requiredBadges[indexPath.row].icon))
        return cell
    }
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
        return CGSize(width: collectionView.width / 3.3, height: collectionView.width / 3.3)
    }
}

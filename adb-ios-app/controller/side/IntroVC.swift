//
//  IntroVC.swift
//  driver-behavior-ios-app
//
//  Created by Amir Mansouri Fard (amf.fard@gmail.com) on 7/31/18.
//  Copyright © 2018 Amir Mansouri Fard (amf.fard@gmail.com). All rights reserved.
//

import UIKit

class IntroVC: UIViewController {

    @IBOutlet weak var collection: UICollectionView!
    @IBOutlet weak var pageCounter: UIPageControl!
    @IBOutlet weak var nextBtn: UIButton!
    override func viewDidLoad() {
        super.viewDidLoad()
        pageCounter.numberOfPages = 5
        pageCounter.currentPage = 0
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    @IBAction func skipSelector(_ sender: UIButton) {
        let vc = self.storyboard?.instantiateViewController(withIdentifier: "mainView")
        (UIApplication.shared.delegate as! AppDelegate).window?.rootViewController = vc!
        (UIApplication.shared.delegate as! AppDelegate).window?.makeKeyAndVisible()
    }
    @IBAction func nextSelector(_ sender: UIButton) {
        let vc = self.storyboard?.instantiateViewController(withIdentifier: "mainView")
        (UIApplication.shared.delegate as! AppDelegate).window?.rootViewController = vc!
        (UIApplication.shared.delegate as! AppDelegate).window?.makeKeyAndVisible()
    }
}

extension IntroVC : UICollectionViewDelegate , UICollectionViewDataSource , UICollectionViewDelegateFlowLayout {
    
    func numberOfSections(in collectionView: UICollectionView) -> Int {
        return 1
    }
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return 5
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "Cell", for: indexPath) as! HelpCell
        switch indexPath.row {
        case 0:
            cell.thumbIMG.image = #imageLiteral(resourceName: "ic_home_tut")
            cell.textLBL.text = "در این بخش می توانید با استفاده از شروع رانندگی سفرهای خود را ثبت کنید و از وضعیت مدالهای خود و آخرین چالش ها مطلع شوید"
            cell.titleLBL.text = "صفحه اصلی"
            break
        case 1:
            cell.thumbIMG.image = #imageLiteral(resourceName: "ic_driving_tut")
            cell.textLBL.text = "با انتخاب گزینه شروع در هنگام رانندگی می توانید رانندگی خود را ارزیابی کنید. پس از اتمام سفر خود با انتخاب گزینه توفق، سفر خود را ثبت کنید"
            cell.titleLBL.text = "رانندگی"
            break
        case 2:
            cell.thumbIMG.image = #imageLiteral(resourceName: "ic_map_tut")
            cell.textLBL.text = "در این قسمت، شما می توانید لیست سفر های خود را مشاهده و به جزئیات رفتار رانندگی خود دست پیدا کنید"
            cell.titleLBL.text = "سفر های من"
            break
        case 3:
            cell.thumbIMG.image = #imageLiteral(resourceName: "ic_medal_tut")
            cell.textLBL.text = "در این بخش متناسب با رفتار راندگی خود مدال های گوناگونی دریافت خواهید کرد که به مبادله آنها می توانید از جوایز گوناگونی بهره مند شوید"
            cell.titleLBL.text = "جوایز"
            break
        case 4:
            cell.thumbIMG.image = #imageLiteral(resourceName: "ic_group_tut")
            cell.textLBL.text = "با استفاده از این قسمت خودتان را به چالش بکشید و با رانندگان دیگر مقایسه کنید"
            cell.titleLBL.text = "چالش"
            break
        default :
            break
        }
        return cell
    }
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
        return CGSize(width: collectionView.width, height: collectionView.height)
    }
    
    func scrollViewDidEndDecelerating(_ scrollView: UIScrollView) {
        let x = scrollView.contentOffset.x
        let w = scrollView.bounds.size.width
        let currentPage = Int(ceil(x/w))
        pageCounter.currentPage = currentPage
    }
}

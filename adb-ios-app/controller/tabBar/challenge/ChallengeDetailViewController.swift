//
//  ChallengeItemViewController.swift
//  driver-behavior-ios-app
//
//  Created by Amir Mansouri Fard (amf.fard@gmail.com) on 7/22/18.
//  Copyright © 2018 Amir Mansouri Fard (amf.fard@gmail.com). All rights reserved.
//

import UIKit

class ChallengeDetailViewController: UIViewController {
    // MARK : Properties
    
    var challengeDetail : ChallengeDetail!

    @IBOutlet weak var chalPpl: UILabel!
    @IBOutlet weak var chalStatus: UILabel!
    @IBOutlet weak var imgChal: UIImageView!
    @IBOutlet weak var challengeBtnHeight: NSLayoutConstraint!
    
    @IBOutlet weak var chalDesc: UILabel!
    @IBOutlet weak var chalSDate: UILabel!
    @IBOutlet weak var chalTitle: UILabel!
    @IBOutlet weak var prizeIMG: UIImageView!
    @IBOutlet weak var prizeDescLBL: UILabel!
    @IBOutlet weak var table : UITableView!
    @IBOutlet weak var allMembersBtn : RadiusButton!
    @IBOutlet weak var winnersBtn : RadiusButton!
    @IBOutlet weak var tableHeight: NSLayoutConstraint!
    @IBOutlet weak var scroll : UIScrollView!
    
    var challenge : Challenge!
    var loading: UIActivityIndicatorView!
    
    var isAllSelector = true
    
    required init?(coder aDecoder: NSCoder) {
        super.init(coder:aDecoder);
    }

    override func viewDidLoad() {
        super.viewDidLoad()
        loadData(challenge)
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    override func viewDidAppear(_ animated:Bool) {
        super.viewDidAppear(animated);
        self.navigationController?.navigationBar.topItem?.title = ""
        self.navigationController?.navigationBar.alpha = 1;
    }
    
    func loadData(_ challenge : Challenge ){
        if let ch = self.challenge {
            ShowLoading(superView: self.view, position: PositionsOfLoading.center, size: 40) { (loading) in
                self.loading = loading
            }
            RemoteService.getChallengeDetail(ch.id, GetChallengeDetail(self));
        }
    }
    
    class GetChallengeDetail : APICallBack{
        
        var parent : ChallengeDetailViewController;
        
        init(_ parent : ChallengeDetailViewController){
            self.parent = parent;
        }
        
        func onSuccess(_ result : Any){
            self.parent.loading.stopAnimating()
            self.parent.challengeDetail = result as! ChallengeDetail
            let rec = self.parent.challengeDetail!

            let url = URL(string:rec.image)
            self.parent.imgChal.kf.setImage(with: url)
            
            self.parent.chalTitle.text = rec.name
            self.parent.chalDesc.text = rec.description
            self.parent.chalDesc.textColor = .gray
            self.parent.chalSDate.text = "تاریخ شروع : \(convertDate(dateString: rec.startDate))"
            self.parent.chalStatus.text = rec.active ? "فعال" : "پایان یافته"
            self.parent.chalPpl.text = "\(rec.participantCount) نفر"
            self.parent.prizeIMG.kf.setImage(with: URL(string: rec.prizeImage))
            self.parent.prizeDescLBL.text = "جایزه این چالش" + rec.prizeDescription
            self.parent.prizeDescLBL.textColor = .gray
            self.parent.table.reloadData()
            
            let tableHeight = rec.scoreChallengeMembers.count * 80
            self.parent.scroll.contentSize.height -= self.parent.tableHeight.constant
            self.parent.scroll.contentSize.height += CGFloat(tableHeight)
            self.parent.tableHeight.constant = CGFloat(tableHeight)
            self.parent.challengeBtnHeight.constant = rec.active ? 50 : 0
        }
        
        func onError( _ error : Any){
            self.parent.loading.stopAnimating()
        }

    }

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */
    
    
    @IBAction func tabSelector(sender : RadiusButton) {
        if sender == winnersBtn {
            self.isAllSelector = false
            sender.backgroundColor = UIColor(red: 235/255, green: 235/255, blue: 241/255, alpha: 1.0)
            self.allMembersBtn.backgroundColor = .white
            let tableHeight = challengeDetail.allChallengeMembers.count * 80
            self.scroll.contentSize.height -= self.tableHeight.constant
            self.scroll.contentSize.height += CGFloat(tableHeight)
            self.tableHeight.constant = CGFloat(tableHeight)
        } else {
            self.isAllSelector = true
            sender.backgroundColor = UIColor(red: 235/255, green: 235/255, blue: 241/255, alpha: 1.0)
            self.winnersBtn.backgroundColor = .white
            
            let tableHeight = challengeDetail.scoreChallengeMembers.count * 80
            self.scroll.contentSize.height -= self.tableHeight.constant
            self.scroll.contentSize.height += CGFloat(tableHeight)
            self.tableHeight.constant = CGFloat(tableHeight)
        }
        
        table.reloadData()
    }

    @IBAction func startChallengeSelector(_ sender: RadiusButton) {
        ShowLoading(superView: self.view, position: .center, size: 40) { (loading) in
            RemoteService.joinChannelge(challenge.id, completion: { (response) in
                loading.stopAnimating()
                if response.response != nil && response.response!.statusCode == 200 {
                    let alert = UIAlertController(title: "شرکت در چالش", message: "شما با موفقیت به چالش مورد نظر پیوستید", preferredStyle: .alert)
                    alert.addAction(UIAlertAction(title: "باشه", style: .default, handler: { (action) in
                        alert.dismiss(animated: true, completion: nil)
                    }))
                    
                    self.present(alert, animated: true, completion: nil)
                } else {
                    let alert = UIAlertController(title: "شرکت در چالش", message: "خطایی رخ داده است. لطفا دوباره تلاش کنید", preferredStyle: UIAlertControllerStyle.alert)
                    alert.addAction(UIAlertAction(title: "باشه", style: .default, handler: { (action) in
                        self.loadData(self.challenge)
                        alert.dismiss(animated: true, completion: nil)
                    }))
                    
                    self.present(alert, animated: true, completion: nil)
                }
            })
        }
    }
}


extension ChallengeDetailViewController : UITableViewDelegate , UITableViewDataSource {
    func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if self.challengeDetail != nil {
            return isAllSelector ? self.challengeDetail.scoreChallengeMembers.count : challengeDetail.allChallengeMembers.count
        }
        
        return 0
    }
    
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "Cell", for: indexPath) as! ChallengeMemberCell
        cell.configureCell(model: isAllSelector ? self.challengeDetail.scoreChallengeMembers[indexPath.row] :
        self.challengeDetail.allChallengeMembers[indexPath.row])
        cell.selectionStyle = .none
        cell.pointLBL.isHidden = !isAllSelector
        return cell
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return 80
    }
}

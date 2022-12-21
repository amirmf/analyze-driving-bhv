//
//  ChallengeViewController.swift
//  driver-behavior-ios-app
//
//  Created by Amir Mansouri Fard (amf.fard@gmail.com) on 6/28/18.
//  Copyright © 2018 Amir Mansouri Fard (amf.fard@gmail.com). All rights reserved.
//
import Foundation
import UIKit
import Kingfisher

class ChallengeViewController: UITableViewController, DefaultBarViewController {
    
    // MARK : Properties
    var records : [Challenge] = [];
    var loading : UIActivityIndicatorView!
    var nothinLabel : UILabel!
    override func viewDidLoad() {
        super.viewDidLoad()
        ShowLoading(superView: self.view, position: .center, size: 40) { (loading) in
            self.loading = loading
            RemoteService.listChallenges(callback: ListChallengeCallBack(self));
        }
        
        self.tableView.separatorStyle = .none
        self.tableView.estimatedRowHeight = 100
        // self.tableView.allowsSelection = false
        self.tableView.rowHeight = UITableViewAutomaticDimension
        
    }
    override func viewDidAppear(_ animated:Bool) {
        super.viewDidAppear(animated);
        self.navigationController?.navigationBar.topItem?.title = "چالش ها";
        self.navigationController?.navigationBar.alpha = 1;
    }
    
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    // MARK: - Table view data source
    
    override func numberOfSections(in tableView: UITableView) -> Int {
        // #warning Incomplete implementation, return the number of sections
        return 1
    }
    
    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        // #warning Incomplete implementation, return the number of rows
        return self.records.count;
    }
    
    
    class ListChallengeCallBack : APICallBack{
        
        var parent : ChallengeViewController;
        
        init(_ parent : ChallengeViewController){
            self.parent = parent;
        }
        
        func onSuccess(_ result : Any){
            parent.loading.stopAnimating()
            let res : Challenges = result as! Challenges ;
            self.parent.records = res.challengeItems;
            if self.parent.records.isEmpty {
                self.parent.nothinLabel = showNothingLabel(context: self.parent, text: "موردی یافت نشد")
                self.parent.view.addSubview(self.parent.nothinLabel)
            } else {
                if self.parent.nothinLabel != nil {
                    self.parent.nothinLabel.removeFromSuperview()
                    self.parent.nothinLabel = nil
                }
            }
            self.parent.tableView.delegate = self.parent;
            self.parent.tableView.reloadData();
            
        }
        
        func onError( _ error : Any){
            parent.loading.stopAnimating()
        }
        
    }
    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        let cell = tableView.dequeueReusableCell(withIdentifier: "challengeCellTemplate") as! ChallengeCell
        cell.selectedBackgroundView?.backgroundColor = UIColor.white

        let rec : Challenge = self.records[indexPath.item];
        
        cell.lblTitle.text = rec.name;
        cell.lblPpl.text =  "\(rec.participantCount)" + " " + "نفر";
        cell.lblStatus.text = rec.dayLeft <= 0 ? "پایان یافته" : "\(rec.dayLeft) روز";
        let url = URL(string:rec.icon)
        cell.imgChal.kf.setImage(with: url)
        
        //cell.addBorderBottom(size: 1.0, color: K.color_gray_dark!);
        // cell.backgroundColor = UIColor.red
        
        
        //        if let base64Data = Data( base64Encoded: rec.icon){
        //            cell.imgChal.image = UIImage(data:base64Data);
        //        }
        
        //        cell.chalImage = UIImage(data:rec.icon.data(using: String.Encoding.RawValue));
        //        cell.chalImage = UIImage(data: Data)
        return cell
    }
    
    override func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        tableView.deselectRow(at: indexPath, animated: true)
        let detailView = self.storyboard?.instantiateViewController(withIdentifier: "challengeDetail") as! ChallengeDetailViewController;
        detailView.challenge = self.records[indexPath.row]
        DefaultTabBarController.singleton?.navigationController?.pushViewController(detailView, animated: true)
    }
    
    /*
     // Override to support conditional editing of the table view.
     override func tableView(_ tableView: UITableView, canEditRowAt indexPath: IndexPath) -> Bool {
     // Return false if you do not want the specified item to be editable.
     return true
     }
     */
    
    /*
     // Override to support editing the table view.
     override func tableView(_ tableView: UITableView, commit editingStyle: UITableViewCellEditingStyle, forRowAt indexPath: IndexPath) {
     if editingStyle == .delete {
     // Delete the row from the data source
     tableView.deleteRows(at: [indexPath], with: .fade)
     } else if editingStyle == .insert {
     // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view
     }
     }
     */
    
    /*
     // Override to support rearranging the table view.
     override func tableView(_ tableView: UITableView, moveRowAt fromIndexPath: IndexPath, to: IndexPath) {
     
     }
     */
    
    /*
     // Override to support conditional rearranging of the table view.
     override func tableView(_ tableView: UITableView, canMoveRowAt indexPath: IndexPath) -> Bool {
     // Return false if you do not want the item to be re-orderable.
     return true
     }
     */
    
    /*
     // MARK: - Navigation
     
     // In a storyboard-based application, you will often want to do a little preparation before navigation
     override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
     // Get the new view controller using segue.destinationViewController.
     // Pass the selected object to the new view controller.
     }
     */
    
}


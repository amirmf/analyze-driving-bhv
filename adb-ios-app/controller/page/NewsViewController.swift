//
//  NewsViewController.swift
//  driver-behavior-ios-app
//
//  Created by Amir Mansouri Fard (amf.fard@gmail.com) on 6/29/18.
//  Copyright © 2018 Amir Mansouri Fard (amf.fard@gmail.com). All rights reserved.
//

import UIKit
import Kingfisher

class NewsViewController: UITableViewController, DefaultBarViewController {
    
    @IBOutlet weak var newsDetail: UILabel!
    @IBOutlet weak var newsDate: UILabel!
    @IBOutlet weak var newsTitle: UILabel!
    @IBOutlet weak var newsImg: UIImageView!
    var records : [News] = []
    var nothgin : UILabel!
    let selfController = NewsC()
    
    var paginationNumber = 0
    
    override func viewDidLoad() {
        super.viewDidLoad();
        
        selfController.getNews(context: self)
        
        self.tableView.separatorStyle = .none
        self.tableView.estimatedRowHeight = 100
        // self.tableView.allowsSelection = false
        self.tableView.rowHeight = UITableViewAutomaticDimension
        
        self.tableView.delegate = self
        self.tableView.dataSource = self
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    override func viewDidAppear(_ animated:Bool) {
        super.viewDidAppear(animated);
        self.navigationController?.navigationBar.topItem?.title = "اخبار"
        self.navigationController?.navigationBar.alpha = 1;
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
    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        let cell = tableView.dequeueReusableCell(withIdentifier: "newsCellTemplate") as! NewsTableViewCell
        cell.selectedBackgroundView?.backgroundColor = UIColor.white
        
        let rec : News = self.records[indexPath.item];
        
        cell.newsTitle.text = rec.title;
        cell.newsDetail.text = rec.detailed;
        cell.newsDate.text = rec.startDate;
//                let url = URL.init(fileURLWithPath:String(rec.image.split(separator:"?")[0]),isDirectory : false)
//        do{
//            if let url = {
//                let imageData = try Data(contentsOf: url);
//                let data = imageData
//                cell.newsImg.image = UIImage(data:data);
//            }
//
//        }catch {
//            print(error.localizedDescription);
//        }
        cell.newsImg.kf.setImage(with: URL.init(string:rec.image.components(separatedBy: "?")[0]), placeholder: nil, options: nil, progressBlock: nil) { (image, err, cache, url) in
            if err == nil {
                cell.newsImg.image = image
            } else {
                print(err.debugDescription)
            }
        }
        return cell
    }
    
    override func scrollViewDidEndDragging(_ scrollView: UIScrollView, willDecelerate decelerate: Bool) {
        // UITableView only moves in one direction, y axis
        let currentOffset = scrollView.contentOffset.y
        let maximumOffset = scrollView.contentSize.height - scrollView.frame.size.height
        
        // Change 10.0 to adjust the distance from bottom
        if maximumOffset - currentOffset <= 10.0 {
            paginationNumber += 1
            selfController.getNews(context: self)
        }
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



class NewsC {
    func getNews(context: NewsViewController) {
        ShowLoading(superView: context.view, position: .center, size: 40) { (loading) in
            RemoteService.listNews(pageNumber: context.paginationNumber) { (response) in
                loading.stopAnimating()
                guard response.error == nil else {
                    print(response.error.debugDescription)
                    return
                }
                
                do {
                    let result = try JSONDecoder().decode(Newses.self,from:response.data!)
                    
                    if context.records.isEmpty {
                        context.records = result.newses
                    } else {
                        context.records.append(contentsOf: result.newses)
                    }
                    
                    if context.records.isEmpty {
                        context.nothgin = showNothingLabel(context: context, text: "خبری دریافت نشد")
                        context.view.addSubview(context.nothgin)
                    } else {
                        if context.nothgin != nil {
                            context.nothgin.removeFromSuperview()
                            context.nothgin = nil
                        }
                    }
                    
                    context.tableView.reloadData()
                } catch let err {
                    print(err.localizedDescription)
                }
            }
        }
    }
}

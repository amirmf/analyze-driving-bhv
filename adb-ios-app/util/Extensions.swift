//
//  Extensions.swift
//  driver-behavior-ios-app
//
//  Created by Amir Mansouri Fard (amf.fard@gmail.com) on 6/23/18.
//  Copyright © 2018 Amir Mansouri Fard (amf.fard@gmail.com). All rights reserved.
//

import Foundation
import UIKit
extension UIView{
    
    func addGradientBackground(firstColor: UIColor, secondColor: UIColor){
        clipsToBounds = true
        let gradientLayer = CAGradientLayer()
        gradientLayer.colors = [firstColor.cgColor, secondColor.cgColor]
        gradientLayer.frame = self.bounds
        gradientLayer.startPoint = CGPoint(x: 0, y: 0)
        gradientLayer.endPoint = CGPoint(x: 1, y: 0)
        print(gradientLayer.frame)
        self.layer.insertSublayer(gradientLayer, at: 0)
    }
    
}

extension UITableViewController{
    func fitHeaderSize() {
        if let headerView = tableView.tableHeaderView {
            
            headerView.setNeedsLayout()
            headerView.layoutIfNeeded()
            
            let height = headerView.systemLayoutSizeFitting(UILayoutFittingCompressedSize).height
            var frame = headerView.frame
            frame.size.height = height
            headerView.frame = frame
            
            tableView.tableHeaderView = headerView
        }else if let headerView = tableView.dequeueReusableCell(withIdentifier:"headerTemplate") {
            
            headerView.setNeedsLayout()
            headerView.layoutIfNeeded()

            let height = headerView.systemLayoutSizeFitting(UILayoutFittingCompressedSize).height
            headerView.frame.size.height = 128.0 //height
            
        }
    }

}

extension Notification.Name{
    static let registrationDone = Notification.Name("registrationDone");
    static let loginDone = Notification.Name("loginDone");
}

extension UIImageView{
    
    func sizeToImage(){

        let widthRatio = self.bounds.size.width/(self.image?.size.width)!
        let heightRatio = self.bounds.size.height/(self.image?.size.height)!
        
        let scale = min(widthRatio,heightRatio);

        let frameWidth = scale * (self.image?.size.width)!
        let frameHeight = scale * (self.image?.size.height)!

        self.frame = CGRect( x: self.frame.minX,
                             y: self.frame.minY,
                             width:frameWidth,
                             height:frameHeight);
        self.bounds = CGRect( x: self.frame.minX,
                             y: self.frame.minY,
                             width:frameWidth,
                             height:frameHeight);

        //UIGraphicsBeginImageContextWithOptions(self.frame.size,false,0);
        
        

    }
    
    func sizeToImage_3(){
        
        let oldCenter = self.center;
        
        self.frame = CGRect( x: self.frame.minX,
                             y: self.frame.minY,
                             width:(self.image?.size.width)!,
                             height:(self.image?.size.height)!);
        
    }
    func sizeToImage_2(){
        let xc = self.center.x;
        let yc = self.center.y;
        
        self.frame = CGRect( x: 0,
                             y:0,
                             width:(self.image?.size.width)!,
                             height:(self.image?.size.height)!)
        self.center = CGPoint(x:xc,y:yc);
    }
}

extension UIImage{
    func cropImage( image:UIImage , cropRect:CGRect) -> UIImage
    {
        UIGraphicsBeginImageContextWithOptions(cropRect.size, false, 0);
        let context = UIGraphicsGetCurrentContext();
        
        context?.translateBy(x: 0.0, y: image.size.height);
        context?.scaleBy(x: 1.0, y: -1.0);
        context?.draw(image.cgImage!, in: CGRect(x:0, y:0, width:image.size.width, height:image.size.height), byTiling: false);
        context?.clip(to: [cropRect]);
        
        let croppedImage = UIGraphicsGetImageFromCurrentImageContext();
        UIGraphicsEndImageContext();
        
        return croppedImage!;
    }
}

extension UIView {
    func addBorderTop(size: CGFloat, color: UIColor) {
        addBorderUtility(x: 0, y: 0, width: frame.width, height: size, color: color)
    }
    func addBorderBottom(size: CGFloat, color: UIColor) {
        addBorderUtility(x: 0, y: frame.height - size, width: frame.width, height: size, color: color)
    }
    func addBorderLeft(size: CGFloat, color: UIColor) {
        addBorderUtility(x: 0, y: 0, width: size, height: frame.height, color: color)
    }
    func addBorderRight(size: CGFloat, color: UIColor) {
        addBorderUtility(x: frame.width - size, y: 0, width: size, height: frame.height, color: color)
    }
    private func addBorderUtility(x: CGFloat, y: CGFloat, width: CGFloat, height: CGFloat, color: UIColor) {
        let border = CALayer()
        border.backgroundColor = color.cgColor
        border.frame = CGRect(x: x, y: y, width: width, height: height)
        layer.addSublayer(border)
    }
}

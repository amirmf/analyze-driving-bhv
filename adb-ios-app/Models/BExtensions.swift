//
//  Extensions.swift
//

import Foundation
import UIKit


/// BinMan1 : An Extension for appending string to nsmutable data for uploading image to server
extension NSMutableData {
    
    func appendString(string: String) {
        let data = string.data(using: .utf8, allowLossyConversion: true)
        append(data!)
    }
}

/// An Extension For get width , height, x and y of views
extension UIView {
    var width : CGFloat {
        get {
            return self.frame.width
        }
    }
    
    var height : CGFloat {
        get {
            return self.frame.height
        }
    }
    
    var xPosition : CGFloat {
        get {
            return self.frame.origin.x
        }
    }
    
    var yPosition : CGFloat {
        get {
            return self.frame.origin.y
        }
    }
}

extension UIImage {
    
    public func maskWithColor(color: UIColor) -> UIImage {
        
        UIGraphicsBeginImageContextWithOptions(self.size, false, self.scale)
        let context = UIGraphicsGetCurrentContext()!
        
        let rect = CGRect(origin: CGPoint.zero, size: size)
        
        color.setFill()
        self.draw(in: rect)
        
        context.setBlendMode(.sourceIn)
        context.fill(rect)
        
        let resultImage = UIGraphicsGetImageFromCurrentImageContext()!
        UIGraphicsEndImageContext()
        return resultImage
    }
    
}

extension String {
    /// BinMan1 : A func for decode url encoded string to simple string
    var decodingUrlEncodedString : String {
        get {
            let string = self.removingPercentEncoding ?? ""
            return string.replacingOccurrences(of: "+", with: " ")
        }
    }
    
    var encodingToUrlEncodedString : String {
        get {
            let string = self.addingPercentEncoding(withAllowedCharacters: .urlHostAllowed) ?? ""
            return string
        }
    }
    
    var decodingUrlEncodedStringWithoutSpace : String {
        get {
            let string = self.removingPercentEncoding ?? ""
            return string
        }
    }
    
    func height(withConstrainedWidth width: CGFloat, font: UIFont) -> CGFloat {
        let constraintRect = CGSize(width: width, height: .greatestFiniteMagnitude)
        let boundingBox = self.boundingRect(with: constraintRect, options: .usesLineFragmentOrigin, attributes: [.font : font], context: nil)
        
        return ceil(boundingBox.height)
    }
    
    func width(withConstrainedHeight height: CGFloat, font: UIFont) -> CGFloat {
        let constraintRect = CGSize(width: .greatestFiniteMagnitude, height: height)
        let boundingBox = self.boundingRect(with: constraintRect, options: .usesLineFragmentOrigin, attributes: [.font : font], context: nil)
        
        return ceil(boundingBox.width)
    }
}

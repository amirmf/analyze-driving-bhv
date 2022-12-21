//
//  File.swift
//  driver-behavior-ios-app
//
//  Created by Amir Mansouri Fard (amf.fard@gmail.com) on 7/19/18.
//  Copyright © 2018 Amir Mansouri Fard (amf.fard@gmail.com). All rights reserved.
//

import Foundation
protocol APICallBack {
    func onSuccess(_ result:Any);
    func onError(_ error : Any);
}

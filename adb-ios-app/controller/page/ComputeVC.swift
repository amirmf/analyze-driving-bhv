//
//  ComputeVC.swift
//  driver-behavior-ios-app
//
//  Created by Amir Mansouri Fard (amf.fard@gmail.com) on 7/26/18.
//  Copyright © 2018 Amir Mansouri Fard (amf.fard@gmail.com). All rights reserved.
//

import UIKit
import NVActivityIndicatorView
import CoreLocation
import CoreMotion

struct ConstantMotion {
    static var x = 0.0
    static var y = 0.0
    static var z = 0.0
    static var rotation = 0
}

class ComputeVC: UIViewController {

    static var singleton : ComputeVC?
    
    var lastLocation : CLLocation!
    var sumDistance = Float()
    var isLocationSet = false
    
    var tripID : Int!
    var timer : Timer!
    var mTimer : Timer!
    var tripTimer : Timer!
    let selfController = ComputeC()
    let locationManager = CLLocationManager()
    let motion = CMMotionManager()
    
    
    @IBOutlet weak var stopBtn: RadiusButton!
    @IBOutlet weak var loading: NVActivityIndicatorView!
    @IBOutlet weak var kilometerLBL : UILabel!
    @IBOutlet weak var speedLBL : UILabel!
    
    var order : Int64 = 0
    
    override func viewDidLoad() {
        super.viewDidLoad()

        ComputeVC.singleton = self
        
        loading.startAnimating()
        
        startAccelerometers()
        
        // Ask for Authorisation from the User.
        self.locationManager.requestAlwaysAuthorization()
        // For use in foreground
        self.locationManager.requestWhenInUseAuthorization()
        if CLLocationManager.locationServicesEnabled() {
            locationManager.delegate = self
            locationManager.desiredAccuracy = kCLLocationAccuracyNearestTenMeters
            locationManager.startUpdatingLocation()
        }
        
        timer = Timer.scheduledTimer(timeInterval: 3, target: self, selector: #selector(self.timerObserver), userInfo: nil, repeats: true)
        timer.fire()
        
        tripTimer = Timer.scheduledTimer(timeInterval: 15, target: self, selector: #selector(self.sendTripTrack), userInfo: nil, repeats: true)
        tripTimer.fire()
    }

    override func viewDidLayoutSubviews() {
        super.viewDidLayoutSubviews()
        stopBtn.layer.cornerRadius = stopBtn.width/2
    }
    
    override func viewDidDisappear(_ animated: Bool) {
        super.viewDidDisappear(animated)
        if timer != nil {
            timer.invalidate()
            timer = nil
        }
        
        if tripTimer != nil {
            tripTimer.invalidate()
            tripTimer = nil
        }
        
        if mTimer != nil {
            mTimer.invalidate()
            mTimer = nil
        }
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    
    func startAccelerometers() {
        // Make sure the accelerometer hardware is available.
        if self.motion.isAccelerometerAvailable {
            self.motion.accelerometerUpdateInterval = 1.0 / 60.0  // 60 Hz
            self.motion.startAccelerometerUpdates()
            self.motion.startDeviceMotionUpdates()
            
            // Configure a timer to fetch the data.
            self.mTimer = Timer(fire: Date(), interval: (1.0/60.0),
                               repeats: true, block: { (timer) in
                                // Get the accelerometer data.
                                if let data = self.motion.accelerometerData {
                                    
                                    let x = data.acceleration.x
                                    let y = data.acceleration.y
                                    let z = data.acceleration.z
                                    
                                    ConstantMotion.x = x
                                    ConstantMotion.y = y
                                    ConstantMotion.z = z
                                    
                                }
                                
                                if let deviceMotion = self.motion.deviceMotion {
                                    let attitude = deviceMotion.attitude
                                    ConstantMotion.rotation = Int(attitude.pitch * 180 / .pi)
                                }
            })
            
            // Add the timer to the current run loop.
            RunLoop.current.add(self.mTimer!, forMode: .defaultRunLoopMode)
        }
    }
    
    @objc func timerObserver() {
        self.isLocationSet = false
    }
    
    @objc func sendTripTrack() {
        let models = DBManager.getTripTracks()
        if models != nil && !models!.isEmpty {
            let trackInfo = TripTrackInfo(tripInfos: models!)
            selfController.sendTripTrack(model: trackInfo)
        }
    }

    @IBAction func stopSelector(_ sender: RadiusButton) {
        if loading.isAnimating {
            if sumDistance > 0.0 {
                let alert = UIAlertController(title: "پایان سفر", message: "آیا از پایان این سفر مطمئن هستید؟", preferredStyle: .alert)
                alert.addAction(UIAlertAction(title: "خیر", style: .default, handler: { (action) in
                    alert.dismiss(animated: true, completion: nil)
                }))
                
                alert.addAction(UIAlertAction(title: "بله", style: .default, handler: { (action) in
                    alert.dismiss(animated: true, completion: nil)
                    let alert2 = UIAlertController(title: "", message: "لطفا نامی برای سفر خود انتخاب کنید", preferredStyle: UIAlertControllerStyle.alert)
                    
                    var textField : UITextField!
                    alert2.addTextField(configurationHandler: { (tf) in
                        textField = tf
                    })
                    
                    alert2.addAction(UIAlertAction(title: "ارسال", style: .default, handler: { (action) in
                        RemoteService.endTrip(req: EndTripRequest(name: textField.text ?? "",tripId: Int64(self.tripID)), completion: { (response) in
                            if response.response != nil && response.response!.statusCode == 200 {
                                self.timer.invalidate()
                                self.timer = nil
                                self.locationManager.stopUpdatingLocation()
                                let alert3 = UIAlertController(title: "اتمام سفر", message: "سفر شما با موفقیت پایان یافت", preferredStyle: .alert)
                                alert3.addAction(UIAlertAction(title: "باشه", style: .default, handler: { (action) in
                                    alert3.dismiss(animated: true, completion: nil)
                                }))
                                self.stopBtn.setTitle("بازگشت", for: .normal)
                                self.loading.stopAnimating()
                                alert2.dismiss(animated: true, completion: nil)
                                self.present(alert3, animated: true, completion: nil)
                            } else {
                                let alert3 = UIAlertController(title: "اتمام سفر", message: "سفر شما با موفقیت انجام نشد", preferredStyle: .alert)
                                alert3.addAction(UIAlertAction(title: "باشه", style: .default, handler: { (action) in
                                    alert3.dismiss(animated: true, completion: nil)
                                }))
                                self.loading.stopAnimating()
                                alert2.dismiss(animated: true, completion: nil)
                                self.present(alert3, animated: true, completion: nil)
                            }
                        })
                    }))
                    
                    alert2.addAction(UIAlertAction(title: "لغو", style: .default, handler: { (action) in
                        alert2.dismiss(animated: true, completion: nil)
                    }))
                    
                    self.present(alert2, animated: true, completion: nil)
                }))
                
                self.present(alert, animated: true, completion: nil)
            } else {
                let alert = UIAlertController(title: "پایان سفر", message: "سفر های کمتر از پانصد متر ثبت نمی شود، آیا از پایان این سفر مطمئن هستید؟", preferredStyle: .alert)
                alert.addAction(UIAlertAction(title: "ادامه", style: .default, handler: { (action) in
                    alert.dismiss(animated: true, completion: nil)
                }))
                
                alert.addAction(UIAlertAction(title: "پایان سفر", style: .default, handler: { (action) in
                    self.stopBtn.setTitle("بازگشت", for: .normal)
                    self.loading.stopAnimating()
                    self.timer.invalidate()
                    self.timer = nil
                    self.locationManager.stopUpdatingLocation()
                    if DBManager.deleteAll() {
                        print("delete all data from db")
                    }
                }))
                
                self.present(alert, animated: true, completion: nil)
            }
        } else {
            self.dismiss(animated: true, completion: nil)
        }
    }
    
    func getDiffTime(first : Date , second : Date) -> Float {
        let diff = second.timeIntervalSince1970 - first.timeIntervalSince1970
        
        let hours = Float(diff / 3600)
        return hours
    }
    
    func computeDistance(loc1 : CLLocation , loc2 : CLLocation) -> CLLocationDistance {
        let dist = loc1.distance(from: loc2)
        return dist
    }
}

extension ComputeVC : CLLocationManagerDelegate {
    func locationManager(_ manager: CLLocationManager, didUpdateLocations locations: [CLLocation]) {
        if !isLocationSet {
            if let location = manager.location {
                var track = TripTrack()
                if self.lastLocation != nil {
                    let dist = computeDistance(loc1: location, loc2: lastLocation)
                    let roundDist = round(Float(Float(dist) / 1000) * 100) / 100
                    sumDistance = sumDistance + roundDist
                    self.kilometerLBL.text = "\(sumDistance) km"
                    let kmH = Double(Double(location.speed) * 18 / 5)
                    var kmHR = round(kmH * 100) / 100
                    kmHR = kmHR < 0.0 ? 0.0 : kmHR
                    self.speedLBL.text = "\(kmHR) km/h"
                    lastLocation = location
                    
                    track.realSpeed = kmHR
                } else {
                    self.lastLocation = location
                    self.sumDistance = 0.0
                    
                    track.realSpeed = 0.0
                }
                
                let dateFormatter = DateFormatter()
                dateFormatter.dateFormat = "yyyy-MM-dd hh:mm:ss"
                track.postDate = dateFormatter.string(from: Date())
                track.deviceId = getDeviceCode()
                track.latitude = Double(location.coordinate.latitude)
                track.longitude = Double(location.coordinate.longitude)
                track.order = self.order
                track.x = ConstantMotion.x
                track.y = ConstantMotion.y
                track.z = ConstantMotion.z
                track.direction = ConstantMotion.rotation
                
                if DBManager.setTripTrackToDB(tr: track) {
                    print("record saved")
                } else {
                    print("record not saved")
                }
                
                isLocationSet = true
                self.order += 1
            }
        }
    }
}

class ComputeC {
    func sendTripTrack (model : TripTrackInfo) {
        RemoteService.sendTripTrack(model) { (response) in
            if response.response?.statusCode != nil && response.response!.statusCode == 200 {
                if DBManager.deleteAll() {
                    print("all trips deleted from db")
                } else {
                    print("some error occured in deleting trips from db")
                }
            } else {
                print(String(data: response.data! , encoding: .utf8) ?? "")
            }
        }
    }
}

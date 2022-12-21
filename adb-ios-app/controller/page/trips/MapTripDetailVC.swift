//
//  MapTripDetailVC.swift
//  driver-behavior-ios-app
//
//  Created by Amir Mansouri Fard (amf.fard@gmail.com) on 7/28/18.
//  Copyright © 2018 Amir Mansouri Fard (amf.fard@gmail.com). All rights reserved.
//

import UIKit
import XLPagerTabStrip
import GoogleMaps

class MapTripDetailVC: UIViewController , IndicatorInfoProvider {

    var tripId : Int!
    
    @IBOutlet weak var mapView : GMSMapView!
    
    let selfController = MapTripDetailC()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        selfController.getGeoLocations(context: self)
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

    func indicatorInfo(for pagerTabStripController: PagerTabStripViewController) -> IndicatorInfo {
        return IndicatorInfo(title: "نقشه")
    }
    
    func makeMapContext (geoLocations : TripGeoLocations) {
        if !geoLocations.geoLocations.isEmpty {
            let firstMarker = GMSMarker(position: CLLocationCoordinate2D(latitude: CLLocationDegrees(geoLocations.geoLocations[0].latitude), longitude: geoLocations.geoLocations[0].longitude))
            let secondMarker = GMSMarker(position: CLLocationCoordinate2D(latitude: CLLocationDegrees(geoLocations.geoLocations.last!.latitude), longitude: geoLocations.geoLocations.last!.longitude))
            firstMarker.map = mapView
            secondMarker.map = mapView
            mapView.animate(to: GMSCameraPosition.camera(withTarget: firstMarker.position, zoom: 15))
            
            
            let path = GMSMutablePath()
            for location in geoLocations.geoLocations {
                path.add(CLLocationCoordinate2D(latitude: CLLocationDegrees(location.latitude), longitude: CLLocationDegrees(location.longitude)))
            }
            
            let line = GMSPolyline(path: path)
            line.strokeWidth = 5
            line.strokeColor = .purple
            line.map = mapView
        }
    }
}

class MapTripDetailC {
    var result : TripGeoLocations!
    func getGeoLocations(context : MapTripDetailVC) {
        ShowLoading(superView: context.view, position: PositionsOfLoading.center, size: 40) { (loading) in
            RemoteService.getTripGeoLocation(context.tripId, completion: { (response) in
                loading.stopAnimating()
                guard response.error == nil else {
                    print(response.error.debugDescription)
                    return
                }
                
                if response.response!.statusCode <= 199 || response.response!.statusCode > 300 {
                    print(response.response!.statusCode)
                } else {
                    do {
                        self.result = try
                            JSONDecoder().decode(TripGeoLocations.self,from:response.data!)
                        context.makeMapContext(geoLocations: self.result)
                    } catch let err {
                        print(err.localizedDescription)
                    }
                }
            })
        }
    }
}

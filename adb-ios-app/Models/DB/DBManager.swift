//
//  DBManager.swift
//  driver-behavior-ios-app
//
//  Created by Amir Mansouri Fard (amf.fard@gmail.com) on 7/30/18.
//  Copyright © 2018 Amir Mansouri Fard (amf.fard@gmail.com). All rights reserved.
//

import UIKit
import CoreData

class DBManager  {
    static func setTripTrackToDB(tr : TripTrack) -> Bool {
        let context = (UIApplication.shared.delegate as! AppDelegate).persistentContainer.viewContext
        let entity = NSEntityDescription.entity(forEntityName: "TrackTripEntity", in: context)
        let trip = NSManagedObject(entity: entity!, insertInto: context)
        
        
        trip.setValue(tr.postDate, forKey: "date")
        trip.setValue(tr.latitude, forKey: "latitude")
        trip.setValue(tr.longitude, forKey: "longitude")
        trip.setValue(tr.realSpeed, forKey: "realSpeed")
        trip.setValue(tr.direction, forKey: "direction")
        trip.setValue(tr.deviceId, forKey: "deviceId")
        trip.setValue(tr.order, forKey: "order")
        trip.setValue(tr.x, forKey: "x")
        trip.setValue(tr.y, forKey: "y")
        trip.setValue(tr.z, forKey: "z")
        
        do {
            try context.save()
            return true
        } catch let err {
            print(err.localizedDescription)
            return false
        }
    }
    
    
    static func getTripTracks() -> Array<TripTrack>? {
        let context = (UIApplication.shared.delegate as! AppDelegate).persistentContainer.viewContext
        let request = NSFetchRequest<NSFetchRequestResult>(entityName: "TrackTripEntity")
        request.returnsObjectsAsFaults = false
        
        do {
            var array = Array<TripTrack>()
            let fetch = try context.fetch(request)
            for data in fetch as! [NSManagedObject] {
                var track = TripTrack()
                track.order = data.value(forKey: "order") as? Int64 ?? 0
                track.postDate = data.value(forKey: "date") as? String ?? ""
                track.deviceId = data.value(forKey: "deviceId") as? String ?? ""
                track.direction = data.value(forKey: "direction") as? Int ?? 0
                track.latitude = data.value(forKey: "latitude") as? Double ?? 0.0
                track.longitude = data.value(forKey: "longitude") as? Double ?? 0.0
                track.realSpeed = data.value(forKey: "realSpeed") as? Double ?? 0.0
                track.sumAcceleration = 0.0
                track.x = data.value(forKey: "x") as? Double ?? 0.0
                track.y = data.value(forKey: "y") as? Double ?? 0.0
                track.z = data.value(forKey: "z") as? Double ?? 0.0
                array.append(track)
            }
            
            return array
        } catch let err {
            print(err.localizedDescription)
            return nil
        }
    }
    
    
    static func deleteAll() -> Bool {
        //getting context from your Core Data Manager Class
        let delegate = UIApplication.shared.delegate as! AppDelegate
        let context = delegate.persistentContainer.viewContext
        
        let deleteFetch = NSFetchRequest<NSFetchRequestResult>(entityName: "TrackTripEntity")
        let deleteRequest = NSBatchDeleteRequest(fetchRequest: deleteFetch)
        
        do {
            try context.execute(deleteRequest)
            try context.save()
            
            return true
        } catch {
            print ("There was an error")
            return false
        }
    }
}

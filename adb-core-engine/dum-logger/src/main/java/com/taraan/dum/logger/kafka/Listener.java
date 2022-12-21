package com.taraan.dum.logger.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taraan.dum.audit.mongodb.TripTrackerInfo;
import com.taraan.dum.logger.mongodb.TripTrackerInfoRepository;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;

import java.io.IOException;

public class Listener {
	private static final ObjectMapper objectMapper = new ObjectMapper();

	public Listener(TripTrackerInfoRepository tripTrackerInfoRepository) {
		this.tripTrackerInfoRepository = tripTrackerInfoRepository;
	}

	private TripTrackerInfoRepository tripTrackerInfoRepository;

	@KafkaListener(topics = "audit")
	public void listenPartition0(ConsumerRecord<String, String> record) throws IOException {
		System.out.println("Received: " + record.value());
		TripTrackerInfo tripTrackerInfo = objectMapper.readValue(record.value(), TripTrackerInfo.class);
		tripTrackerInfoRepository.save(tripTrackerInfo);
	}
	@KafkaListener(topics = "audits")
	public void listenPartition2(ConsumerRecord<String, String> record) throws IOException {
		System.out.println("Received: " + record.value());
		TripTrackerInfo[] tripTrackerInfo = objectMapper.readValue(record.value(), TripTrackerInfo[].class);
		tripTrackerInfoRepository.saveAll(tripTrackerInfo);
	}
}

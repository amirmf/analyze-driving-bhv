package com.taraan.dum.service.socket;

import com.google.protobuf.InvalidProtocolBufferException;
import com.taraan.dum.audit.mongodb.TripTrackerInfo;
import com.taraan.dum.common.DateUtils;
import com.taraan.dum.dto.protobuf.TripTrackerInfoProtocol;
import com.taraan.dum.dto.tracker.TripTrackerInfoDto;
import com.taraan.dum.logic.TripTrackerInfoRepository;
import com.taraan.dum.service.assembler.TripTrackerInfoAssembler;
import com.taraan.dum.service.rest.RestTripTrackerInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;


@Component
public class OdpService implements Runnable {
    private ServerSocketChannel serverSocketChannel;
    @Value("${socket.port}")
    private int port = 10020;
    @Autowired
    private TripTrackerInfoRepository tripTrackerInfoRepository;

    public OdpService() throws IOException {
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(port));
        final Thread thread = new Thread(this);
        thread.start();
    }

    public void run() {
        while (true) {
            try {
                SocketChannel client = serverSocketChannel.accept();
                ByteBuffer buf = ByteBuffer.allocate(1024);
                int numBytesRead = client.read(buf);
                if (numBytesRead == -1) {
                    client.close();
                    continue;
                }
                buf.flip();
                TripTrackerInfoProtocol.TripTrackerInfoDtos tripTrackerInfoDtos = null;
                try {
                    tripTrackerInfoDtos = TripTrackerInfoProtocol.TripTrackerInfoDtos.parseFrom(buf);
                } catch (InvalidProtocolBufferException e) {
                    sendMessage(client, "Failed");
                    continue;
                }
                if (tripTrackerInfoDtos != null) {
                    for (TripTrackerInfoProtocol.OdpTripTrackerInfoDto odpTripTrackerInfoDto : tripTrackerInfoDtos.getOdpTripTrackerInfosList()) {
                        try {
                            tripTrackerInfoRepository.save(getTripTrackerInfo(odpTripTrackerInfoDto));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                sendMessage(client, "OK");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void sendMessage(SocketChannel client, String message) throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byteBuffer.put(message.getBytes());
        byteBuffer.flip();
        client.write(byteBuffer);

        client.close();
    }


    private TripTrackerInfo getTripTrackerInfo(TripTrackerInfoProtocol.OdpTripTrackerInfoDto tripTrackerInfoDto) {
        TripTrackerInfo trackerInfo = new TripTrackerInfo();
        trackerInfo.setDeviceId(tripTrackerInfoDto.getDeviceId());
        trackerInfo.setOrder(tripTrackerInfoDto.getOrder());
        trackerInfo.setDeviceId(tripTrackerInfoDto.getDeviceId());
        trackerInfo.setLongitude(tripTrackerInfoDto.getLongitude());
        trackerInfo.setLatitude(tripTrackerInfoDto.getLatitude());
        trackerInfo.setX(tripTrackerInfoDto.getX());
        trackerInfo.setY(tripTrackerInfoDto.getY());
        trackerInfo.setZ(tripTrackerInfoDto.getZ());
        trackerInfo.setRealSpeed(tripTrackerInfoDto.getRealSpeed());
        final Date date = new Date();
        trackerInfo.setPostDateTimeStamp(date.getTime());
        trackerInfo.setPostDate(date);
        final Date userDate = DateUtils.getDate(tripTrackerInfoDto.getPostDate());
        trackerInfo.setUserPostDate(userDate);
        trackerInfo.setUserPostDateTimeStamp(userDate.getTime());
        return trackerInfo;
    }

}

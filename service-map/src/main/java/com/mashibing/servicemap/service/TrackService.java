package com.mashibing.servicemap.service;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.TrackResponse;
import com.mashibing.servicemap.feign.ServiceTrackClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrackService {

    @Autowired
    ServiceTrackClient serviceTrackClient;

    public ResponseResult<TrackResponse> add(String tid) {
        return serviceTrackClient.add(tid);
    }

}

package com.mashibing.servicemap;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.DirectionResponse;
import org.springframework.stereotype.Service;

@Service
public class DirectionService {

    public ResponseResult driving(String depLongitude, String depLatitude, String destLongitude, String destLatitude) {

        DirectionResponse directionResponse = new DirectionResponse();
        directionResponse.setDistance(123);
        directionResponse.setDuration(11);

        return ResponseResult.success(directionResponse);
    }
}

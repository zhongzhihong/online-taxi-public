package com.mashibing.serviceorder.feign;

import com.mashibing.internalcommon.dto.Car;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.OrderDriverResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("service-driver-user")
public interface ServiceDriverUserClient {

    @GetMapping("/cityDriver/hasAvailableDriver")
    ResponseResult<Boolean> hasAvailableDriver(@RequestParam String cityCode);

    // 根据车辆ID，查询可以派单的司机的信息
    @GetMapping("/getAvailableDriver/{carId}")
    ResponseResult<OrderDriverResponse> getAvailableDriver(@PathVariable("carId") Long carId);

    @GetMapping("/car")
    ResponseResult<Car> getCarInfoById(@RequestParam Long carId);
}

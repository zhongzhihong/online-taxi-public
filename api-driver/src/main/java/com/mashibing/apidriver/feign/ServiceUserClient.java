package com.mashibing.apidriver.feign;

import com.mashibing.internalcommon.dto.Car;
import com.mashibing.internalcommon.dto.DriverUser;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.DriverUserExistsResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;


@FeignClient("service-driver-user")
public interface ServiceUserClient {

    @PutMapping("/user")
    ResponseResult updateUser(@RequestBody DriverUser driverUser);

    @GetMapping("/check-driver/{driverPhone}")
    ResponseResult<DriverUserExistsResponse> getDriverUserByPhone(@PathVariable String driverPhone);

    @GetMapping("/car")
    ResponseResult<Car> getCarInfoById(@RequestParam Long carId);

}

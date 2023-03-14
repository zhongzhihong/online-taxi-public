package com.mashibing.serviceprice.controller;


import com.mashibing.internalcommon.dto.PriceRule;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.serviceprice.service.PriceRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 钟志宏
 * @since 2023-03-13
 */
@RestController
@RequestMapping("/price-rule")
public class PriceRuleController {

    @Autowired
    PriceRuleService priceRuleService;

    @PostMapping("/add")
    public ResponseResult add(@RequestBody PriceRule priceRule) {
        return priceRuleService.add(priceRule);
    }

    @PostMapping("/edit")
    public ResponseResult edit(@RequestBody PriceRule priceRule) {
        return priceRuleService.edit(priceRule);
    }

    @GetMapping("/getLatestPrice")
    public ResponseResult getLatestPrice(String fareType) {
        return priceRuleService.getLatestPrice(fareType);
    }

    @GetMapping("/isLatestPrice")
    public ResponseResult<Boolean> isLatestPrice(@RequestParam String fareType, @RequestParam Integer fareVersion) {
        return priceRuleService.isLatestPrice(fareType, fareVersion);
    }

}

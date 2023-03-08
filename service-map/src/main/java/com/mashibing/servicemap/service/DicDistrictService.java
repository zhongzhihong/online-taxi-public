package com.mashibing.servicemap.service;

import com.mashibing.internalcommon.constant.AMapConfigConstants;
import com.mashibing.internalcommon.constant.CommonStatusEnum;
import com.mashibing.internalcommon.dto.DicDistrict;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.servicemap.feign.MapDicDistrictClient;
import com.mashibing.servicemap.mapper.DicDistrictMapper;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DicDistrictService {

    @Autowired
    MapDicDistrictClient mapDicDistrictClient;

    @Autowired
    DicDistrictMapper dicDistrictMapper;

    public ResponseResult initDicDistrict(String keywords) {

        // 请求地图
        String dicDistrictResult = mapDicDistrictClient.dicDistrict(keywords);
        System.out.println(dicDistrictResult);

        // 解析结果
        JSONObject dicDistrictJsonObject = JSONObject.fromObject(dicDistrictResult);
        int status = dicDistrictJsonObject.getInt(AMapConfigConstants.STATUS);
        if (status != 1) {
            return ResponseResult.fail(CommonStatusEnum.MAP_DISTRICT_ERROR.getCode(), CommonStatusEnum.MAP_DISTRICT_ERROR.getValue());
        }
        JSONArray countryJsonArray = dicDistrictJsonObject.getJSONArray(AMapConfigConstants.DISTRICTS);
        for (int country = 0; country < countryJsonArray.size(); country++) {
            JSONObject countryJsonObject = countryJsonArray.getJSONObject(country);
            String countryAddressCode = countryJsonObject.getString(AMapConfigConstants.ADCODE);
            String countryAddressName = countryJsonObject.getString(AMapConfigConstants.NAME);
            String countryParentAddressCode = "0";
            String countryLevel = countryJsonObject.getString(AMapConfigConstants.LEVEL);

            insertDicDistrict(countryAddressCode, countryAddressName, countryLevel, countryParentAddressCode);

            JSONArray provinceJsonArray = countryJsonObject.getJSONArray(AMapConfigConstants.DISTRICTS);
            for (int p = 0; p < provinceJsonArray.size(); p++) {
                JSONObject provinceJsonObject = provinceJsonArray.getJSONObject(p);
                String provinceAddressCode = provinceJsonObject.getString(AMapConfigConstants.ADCODE);
                String provinceAddressName = provinceJsonObject.getString(AMapConfigConstants.NAME);
                String provinceParentAddressCode = countryAddressCode;
                String provinceLevel = provinceJsonObject.getString(AMapConfigConstants.LEVEL);

                insertDicDistrict(provinceAddressCode, provinceAddressName, provinceLevel, provinceParentAddressCode);

                JSONArray cityArray = provinceJsonObject.getJSONArray(AMapConfigConstants.DISTRICTS);
                for (int city = 0; city < cityArray.size(); city++) {
                    JSONObject cityJsonObject = cityArray.getJSONObject(city);
                    String cityAddressCode = cityJsonObject.getString(AMapConfigConstants.ADCODE);
                    String cityAddressName = cityJsonObject.getString(AMapConfigConstants.NAME);
                    String cityParentAddressCode = provinceAddressCode;
                    String cityLevel = cityJsonObject.getString(AMapConfigConstants.LEVEL);

                    insertDicDistrict(cityAddressCode, cityAddressName, cityLevel, cityParentAddressCode);

                    JSONArray districtArray = cityJsonObject.getJSONArray(AMapConfigConstants.DISTRICTS);
                    for (int d = 0; d < districtArray.size(); d++) {
                        JSONObject districtJsonObject = districtArray.getJSONObject(d);
                        String districtAddressCode = districtJsonObject.getString(AMapConfigConstants.ADCODE);
                        String districtAddressName = districtJsonObject.getString(AMapConfigConstants.NAME);
                        String districtParentAddressCode = cityAddressCode;
                        String districtLevel = districtJsonObject.getString(AMapConfigConstants.LEVEL);

                        if (districtLevel.equals(AMapConfigConstants.STREET)) {
                            continue;
                        }

                        insertDicDistrict(districtAddressCode, districtAddressName, districtLevel, districtParentAddressCode);
                    }
                }
            }

        }
        return ResponseResult.success("");
    }


    // 生成insertDicDistrict方法，后续遍历层级不同的数据插入数据库时直接调用方法即可，可以减少代码的重复量
    public void insertDicDistrict(String addressCode, String addressName, String level, String parentAddressCode) {
        DicDistrict dicDistrict = new DicDistrict();
        dicDistrict.setAddressCode(addressCode);
        dicDistrict.setAddressName(addressName);
        int levelInt = generateLevel(level);
        dicDistrict.setLevel(levelInt);
        dicDistrict.setParentAddressCode(parentAddressCode);

        // 插入数据库
        dicDistrictMapper.insert(dicDistrict);
    }

    // 生成level级别，用于后续判断
    public int generateLevel(String level) {
        int levelInt = 0;
        if (level.trim().equals("country")) {
            levelInt = 0;
        } else if (level.trim().equals("province")) {
            levelInt = 1;
        } else if (level.trim().equals("city")) {
            levelInt = 2;
        } else if (level.trim().equals("district")) {
            levelInt = 3;
        }
        return levelInt;
    }
}

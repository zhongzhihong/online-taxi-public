package com.mashibing.servicemap.service;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.TerminalResponse;
import com.mashibing.servicemap.feign.TerminalClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TerminalService {

    @Autowired
    TerminalClient terminalClient;

    public ResponseResult<TerminalResponse> add(String name, String desc) {
        return terminalClient.add(name, desc);
    }

    public ResponseResult<List<TerminalResponse>> aroundSearch(String center, Integer radius) {
        return terminalClient.aroundsearch(center, radius);
    }

}

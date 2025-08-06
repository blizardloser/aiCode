package com.lyh.aicodemother.controller;

import com.lyh.aicodemother.common.BaseResponse;
import com.lyh.aicodemother.common.ResultUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Comparator;

@RestController
@RequestMapping("/health")
public class HealthController {

    @GetMapping("/")
    public BaseResponse<String> healthCheck() {
        Integer arr[] ={1,2,3,4};
        Arrays.sort(arr,new Comparator(){
            @Override
            public int compare(Object o1, Object o2) {
                return 0;
            }
        });
        return ResultUtils.success("ok");
    }
//    @GetMapping("/")
//    public String healthCheck(){
//        return "ok";
//    }
}

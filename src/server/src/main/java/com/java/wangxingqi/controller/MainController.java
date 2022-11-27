package com.java.wangxingqi.controller;

import com.java.wangxingqi.utils.JsonResponse;
import com.java.wangxingqi.utils.ResponseUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
public class MainController {
    @GetMapping("hello")
    public String helloWorld()
    {
        return "Hello World!";
    }

    @GetMapping("searchInstance")
    public JsonResponse searchInstance() {
        return new JsonResponse(ResponseUtil.OK.getStatus(), ResponseUtil.OK.getMessage());
    }
}
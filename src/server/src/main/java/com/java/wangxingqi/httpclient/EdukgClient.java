package com.java.wangxingqi.httpclient;

import com.java.wangxingqi.configs.FeignConfigurer;
import com.java.wangxingqi.utils.EdukgResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(name = "edukg-client", url = "http://open.edukg.cn/opedukg/api/", configuration = FeignConfigurer.class)
public interface EdukgClient {
    @RequestMapping (value = "typeAuth/user/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    EdukgResponse<String> getId(Map<String, ?> parameters);

}
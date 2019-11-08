package com.printer.core.utils;

import com.printer.core.common.dto.ResultDTO;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class RequestUtil {
    
    // 这里以Json String作为请求参数的类型，实际也可以直接用对象作为参数（使用泛型）
    public static ResultDTO sendRequest(String url, String args) {
        RestTemplate client = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        // 使用json格式发送post请求
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<String>(args, headers);

        // 发送post请求： 最后一个参数是 reponseType, 响应体的数据类型
        ResponseEntity<String> response = client.exchange(url, HttpMethod.POST, requestEntity, String.class);

        return new ResultDTO(true, response.getStatusCodeValue(), response.getBody());
    }
}


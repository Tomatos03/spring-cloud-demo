package org.cloud.exception;

import com.alibaba.csp.sentinel.adapter.spring.webmvc_v6x.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.cloud.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 提供
 *
 * @author : Tomatos
 * @date : 2025/9/26
 */
@Slf4j(topic = "orderExceptionHandler")
@Component
public class OrderExceptionHandler implements BlockExceptionHandler {
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       String s,
                       BlockException e) throws Exception {
        response.setStatus(429); // 429表示被限流
        response.setContentType("application/json;charset=utf-8");

        Result<?> error = Result.error("访问失败, 被Sentinel限流, 请稍后重试");

        String responseJson = objectMapper.writeValueAsString(error);
        log.info("json obj: {}", responseJson);
        response.getOutputStream().write(responseJson.getBytes());
    }
}

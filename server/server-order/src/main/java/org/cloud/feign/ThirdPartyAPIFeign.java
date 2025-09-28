package org.cloud.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/9/26
 */
// 在调用第三方API时，value只是Feign客户端的唯一标识, 不参与服务发现和地址解析.
// 而在Nacos服务中心场景下，value既是唯一标识，也是服务发现的服务名。
@FeignClient(url = "http://third-party-api", value = "third-party")
public interface ThirdPartyAPIFeign {

    @PostMapping("/api/{id}")
    String getInfo(@PathVariable("id") Long id);
}

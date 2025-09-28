package com.atguigu.storage.controller;


import com.atguigu.storage.service.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j(topic = "storage")
@RestController
public class StorageRestController {

    @Autowired
    StorageService  storageService;

    @GetMapping("/deduct")
    public String deduct(@RequestParam("commodityCode") String commodityCode,
                         @RequestParam("count") Integer count) {
        log.info("call...");
        storageService.deduct(commodityCode, count);
        return "storage deduct success";
    }
}

package com.gary.consumer.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gary.consumer.service.impl.HelloRemoteHystrix;

@FeignClient(name = "spring-cloud-producer", fallback = HelloRemoteHystrix.class)
public interface HelloRemote {
	@GetMapping(value = "/hello")
	public String helloFeign(@RequestParam("name") String name);
}

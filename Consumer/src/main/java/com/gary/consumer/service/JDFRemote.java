package com.gary.consumer.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gary.consumer.service.impl.JDFRemoteHystrix;

@FeignClient(name = "JDFSERVICE", fallback = JDFRemoteHystrix.class)
public interface JDFRemote {
	@GetMapping(value = "/processors/createExportPdfJdf")
	public String jdfFeign(@RequestParam(value = "jobId") int jobId,
			@RequestParam(value = "nodeId") int nodeId);
}

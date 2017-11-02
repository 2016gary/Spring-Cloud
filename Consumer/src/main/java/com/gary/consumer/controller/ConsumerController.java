package com.gary.consumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.gary.consumer.service.HelloRemote;
import com.gary.consumer.service.JDFRemote;

@RestController
public class ConsumerController {
	@Autowired
	HelloRemote HelloRemote;

	@Autowired
	JDFRemote jdfRemote;

	@GetMapping("/hello/{name}")
	public String index(@PathVariable("name") String name) {
		return HelloRemote.helloFeign(name);
	}

	@GetMapping("/createJDF/{jobId}/{nodeId}")
	public String createJDF(@PathVariable("jobId") int jobId,
			@PathVariable("nodeId") int nodeId) {
		return jdfRemote.jdfFeign(jobId, nodeId);
	}
}

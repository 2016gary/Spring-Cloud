package com.gary.consumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.gary.consumer.service.HelloRemote;

@RestController
public class ConsumerController {
	@Autowired
	HelloRemote HelloRemote;

	@GetMapping("/hello/{name}")
	public String index(@PathVariable("name") String name) {
		return HelloRemote.helloFeign(name);
	}
}

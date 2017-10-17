package com.gary.consumer.service.impl;

import org.springframework.stereotype.Component;

import com.gary.consumer.service.HelloRemote;

@Component
public class HelloRemoteHystrix implements HelloRemote {

	@Override
	public String helloFeign(String name) {
		return "hello" + name + ", this messge send failed ";
	}

}

package com.gary.consumer.service.impl;

import org.springframework.stereotype.Component;

import com.gary.consumer.service.JDFRemote;

@Component
public class JDFRemoteHystrix implements JDFRemote {

	@Override
	public String jdfFeign(int jobId, int nodeId) {
		return "生成JDF文件失败";
	}

}

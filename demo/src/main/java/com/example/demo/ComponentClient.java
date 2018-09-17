package com.example.demo;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ComponentClient {

	@Autowired
	private com.example.demo.v1.ComponentBean componentBeanV1;

	@Autowired
	private com.example.demo.v2.ComponentBean componentBeanV2;

	@PostConstruct
	void postConstruct() {
		componentBeanV1.method();
		componentBeanV2.method();
	}

}

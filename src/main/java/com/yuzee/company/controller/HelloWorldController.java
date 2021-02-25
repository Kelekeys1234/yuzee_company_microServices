package com.yuzee.company.controller;

import org.springframework.web.bind.annotation.RestController;

import com.yuzee.company.endpoint.HelloWorldEndpoint;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class HelloWorldController implements HelloWorldEndpoint {
	
	@Override
	public String helloWorld() {
		return "Hello World";
	}

}

package com.lixl.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.lixl.demo.test.TestMultiDataSource;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AfterApplicationStarted implements ApplicationRunner {

	@Autowired
    TestMultiDataSource testMultiDataSource;
    
	@Override
	public void run(ApplicationArguments args) throws Exception {
        log.info("start run");
        
        testMultiDataSource.doTest();
        
        log.info("finish run");
	}

}

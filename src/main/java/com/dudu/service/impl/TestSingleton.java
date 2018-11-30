package com.dudu.service.impl;

import com.dudu.service.LearnService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TestSingleton {
    @Resource
    private LearnService learnService;

    public LearnService getLearnService(){
        return learnService;
    }
}

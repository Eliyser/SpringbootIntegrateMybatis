package com.dudu.service;

import com.dudu.domain.LearnResource;

import java.util.List;
import java.util.Map;

public interface LearnService {
    public int add(LearnResource learnResource);
    public int update(LearnResource learnResource);
    public int deleteByIds(String[] ids);
    public LearnResource queryLearnResourceById(Long id);
    /**
     * @Author haien
     * @Description 动态查询
     * @Date 2018/11/3
     * @Param [params]
     * @return java.util.List<com.dudu.domain.LearnResource>
     **/
    public List<LearnResource> queryLearnResourceList(Map<String,Object> params);
}
